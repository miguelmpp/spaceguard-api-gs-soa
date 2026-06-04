package br.com.fiap.spaceguard.leitura.dto;

import br.com.fiap.spaceguard.leitura.model.LeituraSensor;
import br.com.fiap.spaceguard.sensor.model.TipoSensor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosDetalhamentoLeitura(
        Long id,
        Long idSensor,
        String nomeSensor,
        TipoSensor tipoSensor,
        Long idSatelite,
        String nomeSatelite,
        BigDecimal valor,
        String unidadeMedida,
        LocalDateTime registradaEm
) {

    public DadosDetalhamentoLeitura(LeituraSensor leitura) {
        this(
                leitura.getId(),
                leitura.getSensor().getId(),
                leitura.getSensor().getNome(),
                leitura.getSensor().getTipo(),
                leitura.getSensor().getSatelite().getId(),
                leitura.getSensor().getSatelite().getNome(),
                leitura.getValor(),
                leitura.getSensor().getUnidadeMedida(),
                leitura.getRegistradaEm()
        );
    }
}