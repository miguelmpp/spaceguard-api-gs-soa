package br.com.fiap.spaceguard.satelite.dto;

import br.com.fiap.spaceguard.satelite.model.StatusSatelite;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record DadosCadastroSatelite(
        @NotBlank(message = "O nome do satélite é obrigatório.")
        String nome,

        @NotBlank(message = "O código do satélite é obrigatório.")
        String codigo,

        @NotBlank(message = "O operador responsável é obrigatório.")
        String operador,

        @NotNull(message = "O status do satélite é obrigatório.")
        StatusSatelite status,

        @NotBlank(message = "A órbita do satélite é obrigatória.")
        String orbita,

        @NotNull(message = "A data de lançamento é obrigatória.")
        @PastOrPresent(message = "A data de lançamento não pode estar no futuro.")
        LocalDate dataLancamento
) {
}