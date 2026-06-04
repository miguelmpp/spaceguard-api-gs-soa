package br.com.fiap.spaceguard.sensor.service;

import br.com.fiap.spaceguard.satelite.repository.SateliteRepository;
import br.com.fiap.spaceguard.sensor.dto.*;
import br.com.fiap.spaceguard.sensor.model.Sensor;
import br.com.fiap.spaceguard.sensor.repository.SensorRepository;
import br.com.fiap.spaceguard.shared.exception.RecursoNaoEncontradoException;
import br.com.fiap.spaceguard.shared.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;
    private final SateliteRepository sateliteRepository;

    @Override
    @Transactional
    public DadosDetalhamentoSensor cadastrar(DadosCadastroSensor dados) {
        validarLimites(dados.limiteMinimo(), dados.limiteMaximo());

        var satelite = sateliteRepository.findByIdAndAtivoTrue(dados.idSatelite())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Satélite não encontrado."));

        if (sensorRepository.existsByNomeAndSateliteIdAndAtivoTrue(dados.nome(), dados.idSatelite())) {
            throw new RegraNegocioException("Já existe um sensor ativo com este nome para o satélite informado.");
        }

        var sensor = new Sensor(dados, satelite);

        sensorRepository.save(sensor);

        return new DadosDetalhamentoSensor(sensor);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DadosListagemSensor> listar(Pageable pageable) {
        return sensorRepository.findAllByAtivoTrue(pageable)
                .map(DadosListagemSensor::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DadosListagemSensor> listarPorSatelite(Long idSatelite, Pageable pageable) {
        var sateliteExiste = sateliteRepository.findByIdAndAtivoTrue(idSatelite).isPresent();

        if (!sateliteExiste) {
            throw new RecursoNaoEncontradoException("Satélite não encontrado.");
        }

        return sensorRepository.findAllBySateliteIdAndAtivoTrue(idSatelite, pageable)
                .map(DadosListagemSensor::new);
    }

    @Override
    @Transactional(readOnly = true)
    public DadosDetalhamentoSensor detalhar(Long id) {
        var sensor = buscarSensorAtivoPorId(id);

        return new DadosDetalhamentoSensor(sensor);
    }

    @Override
    @Transactional
    public DadosDetalhamentoSensor atualizar(DadosAtualizacaoSensor dados) {
        var sensor = buscarSensorAtivoPorId(dados.id());

        var novoLimiteMinimo = dados.limiteMinimo() != null ? dados.limiteMinimo() : sensor.getLimiteMinimo();
        var novoLimiteMaximo = dados.limiteMaximo() != null ? dados.limiteMaximo() : sensor.getLimiteMaximo();

        validarLimites(novoLimiteMinimo, novoLimiteMaximo);

        sensor.atualizarInformacoes(dados);

        return new DadosDetalhamentoSensor(sensor);
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        var sensor = buscarSensorAtivoPorId(id);

        sensor.desativar();
    }

    private Sensor buscarSensorAtivoPorId(Long id) {
        return sensorRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Sensor não encontrado."));
    }

    private void validarLimites(java.math.BigDecimal limiteMinimo, java.math.BigDecimal limiteMaximo) {
        if (limiteMinimo.compareTo(limiteMaximo) >= 0) {
            throw new RegraNegocioException("O limite mínimo deve ser menor que o limite máximo.");
        }
    }
}