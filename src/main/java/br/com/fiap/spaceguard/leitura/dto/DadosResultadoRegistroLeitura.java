package br.com.fiap.spaceguard.leitura.dto;

import br.com.fiap.spaceguard.alerta.dto.DadosDetalhamentoAlerta;

public record DadosResultadoRegistroLeitura(
        DadosDetalhamentoLeitura leitura,
        Boolean alertaGerado,
        DadosDetalhamentoAlerta alerta
) {
}