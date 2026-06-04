package br.com.fiap.spaceguard.sensor.avaliacao;

import br.com.fiap.spaceguard.alerta.model.NivelAlerta;

public record ResultadoAvaliacaoAlerta(
        NivelAlerta nivel,
        String mensagem
) {
}