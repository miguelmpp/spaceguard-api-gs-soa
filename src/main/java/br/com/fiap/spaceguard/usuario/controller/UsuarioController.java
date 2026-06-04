package br.com.fiap.spaceguard.usuario.controller;

import br.com.fiap.spaceguard.usuario.dto.*;
import br.com.fiap.spaceguard.usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoUsuario> cadastrar(
            @RequestBody @Valid DadosCadastroUsuario dados,
            UriComponentsBuilder uriBuilder
    ) {
        var usuario = usuarioService.cadastrar(dados);

        var uri = uriBuilder
                .path("/usuarios/{id}")
                .buildAndExpand(usuario.id())
                .toUri();

        return ResponseEntity.created(uri).body(usuario);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemUsuario>> listar(
            @ParameterObject
            @PageableDefault(size = 10, sort = "login")
            Pageable pageable
    ) {
        var usuarios = usuarioService.listar(pageable);

        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoUsuario> detalhar(@PathVariable Long id) {
        var usuario = usuarioService.detalhar(id);

        return ResponseEntity.ok(usuario);
    }

    @PutMapping
    public ResponseEntity<DadosDetalhamentoUsuario> atualizarPerfil(
            @RequestBody @Valid DadosAtualizacaoPerfilUsuario dados
    ) {
        var usuario = usuarioService.atualizarPerfil(dados);

        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        usuarioService.excluir(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/senha")
    public ResponseEntity<DadosDetalhamentoUsuario> alterarSenha(
            Authentication authentication,
            @RequestBody @Valid DadosAlteracaoSenha dados
    ) {
        var usuario = usuarioService.alterarSenha(authentication, dados);

        return ResponseEntity.ok(usuario);
    }
}