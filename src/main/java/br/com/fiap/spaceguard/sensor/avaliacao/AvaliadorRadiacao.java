package br.com.fiap.spaceguard.sensor.avaliacao;

import br.com.fiap.spaceguard.sensor.model.TipoSensor;
import org.springframework.stereotype.Component;

@Component
public class AvaliadorRadiacao extends AvaliadorSensorBase {

    @Override
    public boolean deveAvaliar(TipoSensor tipoSensor) {
        return tipoSensor == TipoSensor.RADIACAO;
    }

    @Override
    protected String getNomeTecnico() {
        return "Avaliador de radiação espacial";
    }

    @Override
    protected String getRecomendacao() {
        return "avaliar blindagem, exposição a tempestades solares e necessidade de modo seguro.";
    }
}