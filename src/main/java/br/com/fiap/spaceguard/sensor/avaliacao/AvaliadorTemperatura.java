package br.com.fiap.spaceguard.sensor.avaliacao;

import br.com.fiap.spaceguard.sensor.model.TipoSensor;
import org.springframework.stereotype.Component;

@Component
public class AvaliadorTemperatura extends AvaliadorSensorBase {

    @Override
    public boolean deveAvaliar(TipoSensor tipoSensor) {
        return tipoSensor == TipoSensor.TEMPERATURA;
    }

    @Override
    protected String getNomeTecnico() {
        return "Avaliador de temperatura orbital";
    }

    @Override
    protected String getRecomendacao() {
        return "verificar controle térmico, exposição solar e integridade do módulo monitorado.";
    }
}