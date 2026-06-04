package br.com.fiap.spaceguard.sensor.service;

import br.com.fiap.spaceguard.sensor.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SensorService {

    DadosDetalhamentoSensor cadastrar(DadosCadastroSensor dados);

    Page<DadosListagemSensor> listar(Pageable pageable);

    Page<DadosListagemSensor> listarPorSatelite(Long idSatelite, Pageable pageable);

    DadosDetalhamentoSensor detalhar(Long id);

    DadosDetalhamentoSensor atualizar(DadosAtualizacaoSensor dados);

    void excluir(Long id);
}