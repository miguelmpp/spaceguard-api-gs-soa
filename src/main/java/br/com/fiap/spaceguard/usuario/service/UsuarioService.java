package br.com.fiap.spaceguard.usuario.service;

import br.com.fiap.spaceguard.usuario.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface UsuarioService {

    DadosDetalhamentoUsuario cadastrar(DadosCadastroUsuario dados);

    Page<DadosListagemUsuario> listar(Pageable pageable);

    DadosDetalhamentoUsuario detalhar(Long id);

    DadosDetalhamentoUsuario atualizarPerfil(DadosAtualizacaoPerfilUsuario dados);

    void excluir(Long id);

    DadosDetalhamentoUsuario alterarSenha(Authentication authentication, DadosAlteracaoSenha dados);
}