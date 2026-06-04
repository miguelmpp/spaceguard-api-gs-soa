package br.com.fiap.spaceguard.alerta.service;

import br.com.fiap.spaceguard.alerta.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AlertaService {

    Page<DadosListagemAlerta> listar(Pageable pageable);

    Page<DadosListagemAlerta> listarAbertos(Pageable pageable);

    DadosDetalhamentoAlerta detalhar(Long id);

    DadosDetalhamentoAlerta resolver(Long id);
}