package br.com.fiap.spaceguard.sensor.avaliacao;

import br.com.fiap.spaceguard.sensor.model.TipoSensor;
import org.springframework.stereotype.Component;

@Component
public class AvaliadorComunicacao extends AvaliadorSensorBase {

    @Override
    public boolean deveAvaliar(TipoSensor tipoSensor) {
        return tipoSensor == TipoSensor.COMUNICACAO;
    }

    @Override
    protected String getNomeTecnico() {
        return "Avaliador de comunicação e telemetria";
    }

    @Override
    protected String getRecomendacao() {
        return "verificar antenas, força do sinal, telemetria e janela de comunicação com a estação base.";
    }
}