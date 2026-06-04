package br.com.fiap.spaceguard.auth.controller;

import br.com.fiap.spaceguard.auth.dto.DadosLogin;
import br.com.fiap.spaceguard.auth.dto.DadosTokenJwt;
import br.com.fiap.spaceguard.security.service.TokenService;
import br.com.fiap.spaceguard.usuario.model.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<DadosTokenJwt> efetuarLogin(@RequestBody @Valid DadosLogin dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());

        var authentication = authenticationManager.authenticate(authenticationToken);

        var usuario = (Usuario) authentication.getPrincipal();

        var tokenJwt = tokenService.gerarToken(usuario);

        return ResponseEntity.ok(new DadosTokenJwt(
                tokenJwt,
                "Bearer",
                usuario.getLogin(),
                usuario.getPerfil().name()
        ));
    }
}