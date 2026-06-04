package br.com.fiap.spaceguard.alerta.dto;

import br.com.fiap.spaceguard.alerta.model.Alerta;
import br.com.fiap.spaceguard.alerta.model.NivelAlerta;
import br.com.fiap.spaceguard.alerta.model.StatusAlerta;
import br.com.fiap.spaceguard.sensor.model.TipoSensor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosDetalhamentoAlerta(
        Long id,
        Long idSatelite,
        String nomeSatelite,
        Long idSensor,
        String nomeSensor,
        TipoSensor tipoSensor,
        Long idLeituraSensor,
        NivelAlerta nivel,
        StatusAlerta status,
        String mensagem,
        BigDecimal valorRegistrado,
        LocalDateTime criadoEm,
        LocalDateTime resolvidoEm
) {

    public DadosDetalhamentoAlerta(Alerta alerta) {
        this(
                alerta.getId(),
                alerta.getSatelite().getId(),
                alerta.getSatelite().getNome(),
                alerta.getSensor().getId(),
                alerta.getSensor().getNome(),
                alerta.getSensor().getTipo(),
                alerta.getLeituraSensor().getId(),
                alerta.getNivel(),
                alerta.getStatus(),
                alerta.getMensagem(),
                alerta.getValorRegistrado(),
                alerta.getCriadoEm(),
                alerta.getResolvidoEm()
        );
    }
}