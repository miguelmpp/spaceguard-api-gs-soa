package br.com.fiap.spaceguard.satelite.service;

import br.com.fiap.spaceguard.satelite.dto.*;
import br.com.fiap.spaceguard.satelite.model.Satelite;
import br.com.fiap.spaceguard.satelite.repository.SateliteRepository;
import br.com.fiap.spaceguard.shared.exception.RecursoNaoEncontradoException;
import br.com.fiap.spaceguard.shared.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SateliteServiceImpl implements SateliteService {

    private final SateliteRepository sateliteRepository;

    @Override
    @Transactional
    public DadosDetalhamentoSatelite cadastrar(DadosCadastroSatelite dados) {
        if (sateliteRepository.existsByCodigo(dados.codigo())) {
            throw new RegraNegocioException("Já existe um satélite cadastrado com este código.");
        }

        var satelite = new Satelite(dados);

        sateliteRepository.save(satelite);

        return new DadosDetalhamentoSatelite(satelite);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DadosListagemSatelite> listar(Pageable pageable) {
        return sateliteRepository.findAllByAtivoTrue(pageable)
                .map(DadosListagemSatelite::new);
    }

    @Override
    @Transactional(readOnly = true)
    public DadosDetalhamentoSatelite detalhar(Long id) {
        var satelite = buscarSateliteAtivoPorId(id);

        return new DadosDetalhamentoSatelite(satelite);
    }

    @Override
    @Transactional
    public DadosDetalhamentoSatelite atualizar(DadosAtualizacaoSatelite dados) {
        var satelite = buscarSateliteAtivoPorId(dados.id());

        satelite.atualizarInformacoes(dados);

        return new DadosDetalhamentoSatelite(satelite);
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        var satelite = buscarSateliteAtivoPorId(id);

        satelite.desativar();
    }

    private Satelite buscarSateliteAtivoPorId(Long id) {
        return sateliteRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Satélite não encontrado."));
    }
}