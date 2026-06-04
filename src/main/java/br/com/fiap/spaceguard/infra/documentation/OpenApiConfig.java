package br.com.fiap.spaceguard.infra.documentation;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI spaceGuardOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("SpaceGuard API")
                        .version("1.0.0")
                        .description("API REST para monitoramento de satélites, sensores, leituras e alertas espaciais.")
                        .contact(new Contact()
                                .name("FIAP - Global Solution Space Connect")
                                .email("contato@fiap.com.br")
                        )
                )
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }
}