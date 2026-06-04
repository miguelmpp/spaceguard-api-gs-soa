package br.com.fiap.spaceguard.leitura.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosRegistroLeitura(
        @NotNull(message = "O id do sensor é obrigatório.")
        Long idSensor,

        @NotNull(message = "O valor da leitura é obrigatório.")
        BigDecimal valor,

        @PastOrPresent(message = "A data da leitura não pode estar no futuro.")
        LocalDateTime registradaEm
) {
}