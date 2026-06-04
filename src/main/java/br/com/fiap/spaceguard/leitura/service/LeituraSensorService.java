package br.com.fiap.spaceguard.leitura.service;

import br.com.fiap.spaceguard.leitura.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LeituraSensorService {

    DadosResultadoRegistroLeitura registrar(DadosRegistroLeitura dados);

    Page<DadosListagemLeitura> listar(Pageable pageable);

    Page<DadosListagemLeitura> listarPorSensor(Long idSensor, Pageable pageable);

    DadosDetalhamentoLeitura detalhar(Long id);
}