package br.com.fiap.spaceguard.satelite.service;

import br.com.fiap.spaceguard.satelite.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SateliteService {

    DadosDetalhamentoSatelite cadastrar(DadosCadastroSatelite dados);

    Page<DadosListagemSatelite> listar(Pageable pageable);

    DadosDetalhamentoSatelite detalhar(Long id);

    DadosDetalhamentoSatelite atualizar(DadosAtualizacaoSatelite dados);

    void excluir(Long id);
}