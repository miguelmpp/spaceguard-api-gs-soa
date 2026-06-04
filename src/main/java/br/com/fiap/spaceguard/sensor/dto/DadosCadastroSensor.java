package br.com.fiap.spaceguard.sensor.dto;

import br.com.fiap.spaceguard.sensor.model.StatusSensor;
import br.com.fiap.spaceguard.sensor.model.TipoSensor;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DadosCadastroSensor(
        @NotBlank(message = "O nome do sensor é obrigatório.")
        String nome,

        @NotNull(message = "O tipo do sensor é obrigatório.")
        TipoSensor tipo,

        @NotBlank(message = "A unidade de medida é obrigatória.")
        String unidadeMedida,

        @NotNull(message = "O status do sensor é obrigatório.")
        StatusSensor status,

        @NotNull(message = "O limite mínimo é obrigatório.")
        BigDecimal limiteMinimo,

        @NotNull(message = "O limite máximo é obrigatório.")
        BigDecimal limiteMaximo,

        @NotNull(message = "O id do satélite é obrigatório.")
        Long idSatelite
) {
}