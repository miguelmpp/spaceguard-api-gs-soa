package br.com.fiap.spaceguard.security.config;

import br.com.fiap.spaceguard.security.filter.SecurityFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(configuracao ->
                        configuracao.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(autorizacao -> autorizacao
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/health-check").permitAll()
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        .requestMatchers(HttpMethod.POST, "/usuarios").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/usuarios", "/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/usuarios").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/usuarios/senha").authenticated()

                        .requestMatchers(HttpMethod.POST, "/satelites").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/satelites").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/satelites/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/satelites", "/satelites/**")
                        .hasAnyRole("ADMIN", "OPERADOR", "ANALISTA")

                        .requestMatchers(HttpMethod.POST, "/sensores").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/sensores").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/sensores/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/sensores", "/sensores/**")
                        .hasAnyRole("ADMIN", "OPERADOR", "ANALISTA")

                        .requestMatchers(HttpMethod.POST, "/leituras").hasAnyRole("ADMIN", "OPERADOR")
                        .requestMatchers(HttpMethod.GET, "/leituras", "/leituras/**")
                        .hasAnyRole("ADMIN", "OPERADOR", "ANALISTA")

                        .requestMatchers(HttpMethod.PUT, "/alertas/**").hasAnyRole("ADMIN", "OPERADOR")
                        .requestMatchers(HttpMethod.GET, "/alertas", "/alertas/**")
                        .hasAnyRole("ADMIN", "OPERADOR", "ANALISTA")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}