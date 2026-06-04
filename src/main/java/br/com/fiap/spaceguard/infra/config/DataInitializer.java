package br.com.fiap.spaceguard.infra.config;

import br.com.fiap.spaceguard.usuario.model.PerfilUsuario;
import br.com.fiap.spaceguard.usuario.model.Usuario;
import br.com.fiap.spaceguard.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class DataInitializer implements ApplicationRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.login}")
    private String adminLogin;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (usuarioRepository.existsByLogin(adminLogin)) {
            return;
        }

        var admin = new Usuario(
                adminLogin,
                passwordEncoder.encode(adminPassword),
                PerfilUsuario.ADMIN
        );

        usuarioRepository.save(admin);
    }
}