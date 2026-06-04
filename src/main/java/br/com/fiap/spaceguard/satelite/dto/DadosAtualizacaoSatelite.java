package br.com.fiap.spaceguard.satelite.dto;

import br.com.fiap.spaceguard.satelite.model.StatusSatelite;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record DadosAtualizacaoSatelite(
        @NotNull(message = "O id do satélite é obrigatório.")
        Long id,

        String nome,

        String operador,

        StatusSatelite status,

        String orbita,

        @PastOrPresent(message = "A data de lançamento não pode estar no futuro.")
        LocalDate dataLancamento
) {
}