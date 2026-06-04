package br.com.fiap.spaceguard.sensor.avaliacao;

import br.com.fiap.spaceguard.leitura.model.LeituraSensor;
import br.com.fiap.spaceguard.sensor.model.Sensor;
import br.com.fiap.spaceguard.sensor.model.TipoSensor;

import java.util.Optional;

public interface AvaliadorLeituraSensor {

    boolean deveAvaliar(TipoSensor tipoSensor);

    Optional<ResultadoAvaliacaoAlerta> avaliar(Sensor sensor, LeituraSensor leitura);
}