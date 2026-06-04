package br.com.fiap.spaceguard.sensor.avaliacao;

import br.com.fiap.spaceguard.sensor.model.TipoSensor;
import org.springframework.stereotype.Component;

@Component
public class AvaliadorEnergia extends AvaliadorSensorBase {

    @Override
    public boolean deveAvaliar(TipoSensor tipoSensor) {
        return tipoSensor == TipoSensor.ENERGIA;
    }

    @Override
    protected String getNomeTecnico() {
        return "Avaliador de energia embarcada";
    }

    @Override
    protected String getRecomendacao() {
        return "verificar baterias, painéis solares, consumo dos módulos e disponibilidade energética.";
    }
}