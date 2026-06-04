package br.com.fiap.spaceguard.satelite.controller;

import br.com.fiap.spaceguard.satelite.dto.*;
import br.com.fiap.spaceguard.satelite.service.SateliteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/satelites")
@SecurityRequirement(name = "bearer-key")
public class SateliteController {

    private final SateliteService sateliteService;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoSatelite> cadastrar(
            @RequestBody @Valid DadosCadastroSatelite dados,
            UriComponentsBuilder uriBuilder
    ) {
        var satelite = sateliteService.cadastrar(dados);

        var uri = uriBuilder
                .path("/satelites/{id}")
                .buildAndExpand(satelite.id())
                .toUri();

        return ResponseEntity.created(uri).body(satelite);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemSatelite>> listar(
            @ParameterObject
            @PageableDefault(size = 10, sort = "nome")
            Pageable pageable
    ) {
        var satelites = sateliteService.listar(pageable);

        return ResponseEntity.ok(satelites);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoSatelite> detalhar(@PathVariable Long id) {
        var satelite = sateliteService.detalhar(id);

        return ResponseEntity.ok(satelite);
    }

    @PutMapping
    public ResponseEntity<DadosDetalhamentoSatelite> atualizar(
            @RequestBody @Valid DadosAtualizacaoSatelite dados
    ) {
        var satelite = sateliteService.atualizar(dados);

        return ResponseEntity.ok(satelite);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        sateliteService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}