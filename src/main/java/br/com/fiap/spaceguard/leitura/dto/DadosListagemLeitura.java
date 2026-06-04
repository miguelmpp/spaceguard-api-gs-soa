package br.com.fiap.spaceguard.leitura.dto;

import br.com.fiap.spaceguard.leitura.model.LeituraSensor;
import br.com.fiap.spaceguard.sensor.model.TipoSensor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosListagemLeitura(
        Long id,
        String nomeSensor,
        TipoSensor tipoSensor,
        String nomeSatelite,
        BigDecimal valor,
        String unidadeMedida,
        LocalDateTime registradaEm
) {

    public DadosListagemLeitura(LeituraSensor leitura) {
        this(
                leitura.getId(),
                leitura.getSensor().getNome(),
                leitura.getSensor().getTipo(),
                leitura.getSensor().getSatelite().getNome(),
                leitura.getValor(),
                leitura.getSensor().getUnidadeMedida(),
                leitura.getRegistradaEm()
        );
    }
}