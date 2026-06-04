package br.com.fiap.spaceguard.satelite.dto;

import br.com.fiap.spaceguard.satelite.model.Satelite;
import br.com.fiap.spaceguard.satelite.model.StatusSatelite;

import java.time.LocalDate;

public record DadosListagemSatelite(
        Long id,
        String nome,
        String codigo,
        String operador,
        StatusSatelite status,
        String orbita,
        LocalDate dataLancamento
) {

    public DadosListagemSatelite(Satelite satelite) {
        this(
                satelite.getId(),
                satelite.getNome(),
                satelite.getCodigo(),
                satelite.getOperador(),
                satelite.getStatus(),
                satelite.getOrbita(),
                satelite.getDataLancamento()
        );
    }
}