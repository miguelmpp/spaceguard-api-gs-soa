package br.com.fiap.spaceguard.alerta.service;

import br.com.fiap.spaceguard.alerta.dto.*;
import br.com.fiap.spaceguard.alerta.model.Alerta;
import br.com.fiap.spaceguard.alerta.model.StatusAlerta;
import br.com.fiap.spaceguard.alerta.repository.AlertaRepository;
import br.com.fiap.spaceguard.shared.exception.RecursoNaoEncontradoException;
import br.com.fiap.spaceguard.shared.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AlertaServiceImpl implements AlertaService {

    private final AlertaRepository alertaRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<DadosListagemAlerta> listar(Pageable pageable) {
        return alertaRepository.findAll(pageable)
                .map(DadosListagemAlerta::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DadosListagemAlerta> listarAbertos(Pageable pageable) {
        return alertaRepository.findAllByStatus(StatusAlerta.ABERTO, pageable)
                .map(DadosListagemAlerta::new);
    }

    @Override
    @Transactional(readOnly = true)
    public DadosDetalhamentoAlerta detalhar(Long id) {
        var alerta = buscarAlertaPorId(id);

        return new DadosDetalhamentoAlerta(alerta);
    }

    @Override
    @Transactional
    public DadosDetalhamentoAlerta resolver(Long id) {
        var alerta = buscarAlertaPorId(id);

        if (!alerta.estaAberto()) {
            throw new RegraNegocioException("Este alerta já está resolvido.");
        }

        alerta.resolver();

        return new DadosDetalhamentoAlerta(alerta);
    }

    private Alerta buscarAlertaPorId(Long id) {
        return alertaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Alerta não encontrado."));
    }
}