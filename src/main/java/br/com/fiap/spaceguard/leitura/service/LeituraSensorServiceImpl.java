package br.com.fiap.spaceguard.leitura.service;

import br.com.fiap.spaceguard.alerta.dto.DadosDetalhamentoAlerta;
import br.com.fiap.spaceguard.alerta.model.Alerta;
import br.com.fiap.spaceguard.alerta.repository.AlertaRepository;
import br.com.fiap.spaceguard.leitura.dto.*;
import br.com.fiap.spaceguard.leitura.model.LeituraSensor;
import br.com.fiap.spaceguard.leitura.repository.LeituraSensorRepository;
import br.com.fiap.spaceguard.sensor.avaliacao.AvaliadorLeituraSensor;
import br.com.fiap.spaceguard.sensor.repository.SensorRepository;
import br.com.fiap.spaceguard.shared.exception.RecursoNaoEncontradoException;
import br.com.fiap.spaceguard.shared.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LeituraSensorServiceImpl implements LeituraSensorService {

    private final LeituraSensorRepository leituraSensorRepository;
    private final SensorRepository sensorRepository;
    private final AlertaRepository alertaRepository;
    private final List<AvaliadorLeituraSensor> avaliadores;

    @Override
    @Transactional
    public DadosResultadoRegistroLeitura registrar(DadosRegistroLeitura dados) {
        var sensor = sensorRepository.findByIdAndAtivoTrue(dados.idSensor())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Sensor não encontrado."));

        if (!sensor.estaOperacional()) {
            throw new RegraNegocioException("Não é possível registrar leitura para um sensor ou satélite inoperante.");
        }

        var leitura = new LeituraSensor(dados, sensor);
        leituraSensorRepository.save(leitura);

        var avaliador = avaliadores.stream()
                .filter(item -> item.deveAvaliar(sensor.getTipo()))
                .findFirst()
                .orElseThrow(() -> new RegraNegocioException("Não existe avaliador configurado para este tipo de sensor."));

        var resultadoAvaliacao = avaliador.avaliar(sensor, leitura);

        if (resultadoAvaliacao.isEmpty()) {
            return new DadosResultadoRegistroLeitura(
                    new DadosDetalhamentoLeitura(leitura),
                    false,
                    null
            );
        }

        var resultado = resultadoAvaliacao.get();

        var alerta = new Alerta(
                sensor,
                leitura,
                resultado.nivel(),
                resultado.mensagem()
        );

        alertaRepository.save(alerta);

        return new DadosResultadoRegistroLeitura(
                new DadosDetalhamentoLeitura(leitura),
                true,
                new DadosDetalhamentoAlerta(alerta)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DadosListagemLeitura> listar(Pageable pageable) {
        return leituraSensorRepository.findAll(pageable)
                .map(DadosListagemLeitura::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DadosListagemLeitura> listarPorSensor(Long idSensor, Pageable pageable) {
        var sensorExiste = sensorRepository.findByIdAndAtivoTrue(idSensor).isPresent();

        if (!sensorExiste) {
            throw new RecursoNaoEncontradoException("Sensor não encontrado.");
        }

        return leituraSensorRepository.findAllBySensorId(idSensor, pageable)
                .map(DadosListagemLeitura::new);
    }

    @Override
    @Transactional(readOnly = true)
    public DadosDetalhamentoLeitura detalhar(Long id) {
        var leitura = leituraSensorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Leitura não encontrada."));

        return new DadosDetalhamentoLeitura(leitura);
    }
}