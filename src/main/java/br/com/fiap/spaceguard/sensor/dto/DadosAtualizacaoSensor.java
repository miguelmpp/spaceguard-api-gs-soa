package br.com.fiap.spaceguard.sensor.dto;

import br.com.fiap.spaceguard.sensor.model.StatusSensor;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DadosAtualizacaoSensor(
        @NotNull(message = "O id do sensor é obrigatório.")
        Long id,

        String nome,

        String unidadeMedida,

        StatusSensor status,

        BigDecimal limiteMinimo,

        BigDecimal limiteMaximo
) {
}