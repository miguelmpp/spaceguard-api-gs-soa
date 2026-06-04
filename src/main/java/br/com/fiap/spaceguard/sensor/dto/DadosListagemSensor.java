package br.com.fiap.spaceguard.sensor.dto;

import br.com.fiap.spaceguard.sensor.model.Sensor;
import br.com.fiap.spaceguard.sensor.model.StatusSensor;
import br.com.fiap.spaceguard.sensor.model.TipoSensor;

import java.math.BigDecimal;

public record DadosListagemSensor(
        Long id,
        String nome,
        TipoSensor tipo,
        String unidadeMedida,
        StatusSensor status,
        BigDecimal limiteMinimo,
        BigDecimal limiteMaximo,
        Long idSatelite,
        String nomeSatelite
) {

    public DadosListagemSensor(Sensor sensor) {
        this(
                sensor.getId(),
                sensor.getNome(),
                sensor.getTipo(),
                sensor.getUnidadeMedida(),
                sensor.getStatus(),
                sensor.getLimiteMinimo(),
                sensor.getLimiteMaximo(),
                sensor.getSatelite().getId(),
                sensor.getSatelite().getNome()
        );
    }
}