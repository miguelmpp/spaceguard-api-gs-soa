package br.com.fiap.spaceguard.leitura.controller;

import br.com.fiap.spaceguard.leitura.dto.*;
import br.com.fiap.spaceguard.leitura.service.LeituraSensorService;
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
@RequestMapping("/leituras")
@SecurityRequirement(name = "bearer-key")
public class LeituraSensorController {

    private final LeituraSensorService leituraSensorService;

    @PostMapping
    public ResponseEntity<DadosResultadoRegistroLeitura> registrar(
            @RequestBody @Valid DadosRegistroLeitura dados,
            UriComponentsBuilder uriBuilder
    ) {
        var resultado = leituraSensorService.registrar(dados);

        var uri = uriBuilder
                .path("/leituras/{id}")
                .buildAndExpand(resultado.leitura().id())
                .toUri();

        return ResponseEntity.created(uri).body(resultado);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemLeitura>> listar(
            @ParameterObject
            @PageableDefault(size = 10, sort = "registradaEm")
            Pageable pageable
    ) {
        var leituras = leituraSensorService.listar(pageable);

        return ResponseEntity.ok(leituras);
    }

    @GetMapping("/sensor/{idSensor}")
    public ResponseEntity<Page<DadosListagemLeitura>> listarPorSensor(
            @PathVariable Long idSensor,
            @ParameterObject
            @PageableDefault(size = 10, sort = "registradaEm")
            Pageable pageable
    ) {
        var leituras = leituraSensorService.listarPorSensor(idSensor, pageable);

        return ResponseEntity.ok(leituras);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoLeitura> detalhar(@PathVariable Long id) {
        var leitura = leituraSensorService.detalhar(id);

        return ResponseEntity.ok(leitura);
    }
}