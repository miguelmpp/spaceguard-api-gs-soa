package br.com.fiap.spaceguard.alerta.dto;

import br.com.fiap.spaceguard.alerta.model.Alerta;
import br.com.fiap.spaceguard.alerta.model.NivelAlerta;
import br.com.fiap.spaceguard.alerta.model.StatusAlerta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosListagemAlerta(
        Long id,
        String nomeSatelite,
        String nomeSensor,
        NivelAlerta nivel,
        StatusAlerta status,
        BigDecimal valorRegistrado,
        LocalDateTime criadoEm
) {

    public DadosListagemAlerta(Alerta alerta) {
        this(
                alerta.getId(),
                alerta.getSatelite().getNome(),
                alerta.getSensor().getNome(),
                alerta.getNivel(),
                alerta.getStatus(),
                alerta.getValorRegistrado(),
                alerta.getCriadoEm()
        );
    }
}