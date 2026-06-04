package br.com.fiap.spaceguard.sensor.controller;

import br.com.fiap.spaceguard.sensor.dto.*;
import br.com.fiap.spaceguard.sensor.service.SensorService;
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
@RequestMapping("/sensores")
@SecurityRequirement(name = "bearer-key")
public class SensorController {

    private final SensorService sensorService;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoSensor> cadastrar(
            @RequestBody @Valid DadosCadastroSensor dados,
            UriComponentsBuilder uriBuilder
    ) {
        var sensor = sensorService.cadastrar(dados);

        var uri = uriBuilder
                .path("/sensores/{id}")
                .buildAndExpand(sensor.id())
                .toUri();

        return ResponseEntity.created(uri).body(sensor);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemSensor>> listar(
            @ParameterObject
            @PageableDefault(size = 10, sort = "nome")
            Pageable pageable
    ) {
        var sensores = sensorService.listar(pageable);

        return ResponseEntity.ok(sensores);
    }

    @GetMapping("/satelite/{idSatelite}")
    public ResponseEntity<Page<DadosListagemSensor>> listarPorSatelite(
            @PathVariable Long idSatelite,
            @ParameterObject
            @PageableDefault(size = 10, sort = "nome")
            Pageable pageable
    ) {
        var sensores = sensorService.listarPorSatelite(idSatelite, pageable);

        return ResponseEntity.ok(sensores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoSensor> detalhar(@PathVariable Long id) {
        var sensor = sensorService.detalhar(id);

        return ResponseEntity.ok(sensor);
    }

    @PutMapping
    public ResponseEntity<DadosDetalhamentoSensor> atualizar(
            @RequestBody @Valid DadosAtualizacaoSensor dados
    ) {
        var sensor = sensorService.atualizar(dados);

        return ResponseEntity.ok(sensor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        sensorService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}