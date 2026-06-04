package br.com.fiap.spaceguard.alerta.controller;

import br.com.fiap.spaceguard.alerta.dto.*;
import br.com.fiap.spaceguard.alerta.service.AlertaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/alertas")
@SecurityRequirement(name = "bearer-key")
public class AlertaController {

    private final AlertaService alertaService;

    @GetMapping
    public ResponseEntity<Page<DadosListagemAlerta>> listar(
            @ParameterObject
            @PageableDefault(size = 10, sort = "criadoEm")
            Pageable pageable
    ) {
        var alertas = alertaService.listar(pageable);

        return ResponseEntity.ok(alertas);
    }

    @GetMapping("/abertos")
    public ResponseEntity<Page<DadosListagemAlerta>> listarAbertos(
            @ParameterObject
            @PageableDefault(size = 10, sort = "criadoEm")
            Pageable pageable
    ) {
        var alertas = alertaService.listarAbertos(pageable);

        return ResponseEntity.ok(alertas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoAlerta> detalhar(@PathVariable Long id) {
        var alerta = alertaService.detalhar(id);

        return ResponseEntity.ok(alerta);
    }

    @PutMapping("/{id}/resolver")
    public ResponseEntity<DadosDetalhamentoAlerta> resolver(@PathVariable Long id) {
        var alerta = alertaService.resolver(id);

        return ResponseEntity.ok(alerta);
    }
}