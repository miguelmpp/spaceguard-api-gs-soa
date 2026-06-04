package br.com.fiap.spaceguard.usuario.service;

import br.com.fiap.spaceguard.shared.exception.RecursoNaoEncontradoException;
import br.com.fiap.spaceguard.shared.exception.RegraNegocioException;
import br.com.fiap.spaceguard.usuario.dto.*;
import br.com.fiap.spaceguard.usuario.model.Usuario;
import br.com.fiap.spaceguard.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public DadosDetalhamentoUsuario cadastrar(DadosCadastroUsuario dados) {
        if (usuarioRepository.existsByLogin(dados.login())) {
            throw new RegraNegocioException("Já existe um usuário cadastrado com este login.");
        }

        var senhaCriptografada = passwordEncoder.encode(dados.senha());

        var usuario = new Usuario(
                dados.login(),
                senhaCriptografada,
                dados.perfil()
        );

        usuarioRepository.save(usuario);

        return new DadosDetalhamentoUsuario(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DadosListagemUsuario> listar(Pageable pageable) {
        return usuarioRepository.findAllByAtivoTrue(pageable)
                .map(DadosListagemUsuario::new);
    }

    @Override
    @Transactional(readOnly = true)
    public DadosDetalhamentoUsuario detalhar(Long id) {
        var usuario = buscarUsuarioAtivoPorId(id);

        return new DadosDetalhamentoUsuario(usuario);
    }

    @Override
    @Transactional
    public DadosDetalhamentoUsuario atualizarPerfil(DadosAtualizacaoPerfilUsuario dados) {
        var usuario = buscarUsuarioAtivoPorId(dados.id());

        usuario.atualizarPerfil(dados.perfil());

        return new DadosDetalhamentoUsuario(usuario);
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        var usuario = buscarUsuarioAtivoPorId(id);

        usuario.desativar();
    }

    @Override
    @Transactional
    public DadosDetalhamentoUsuario alterarSenha(Authentication authentication, DadosAlteracaoSenha dados) {
        var usuarioAutenticado = (Usuario) authentication.getPrincipal();

        var usuario = buscarUsuarioAtivoPorId(usuarioAutenticado.getId());

        if (!passwordEncoder.matches(dados.senhaAtual(), usuario.getSenha())) {
            throw new RegraNegocioException("A senha atual informada está incorreta.");
        }

        var novaSenhaCriptografada = passwordEncoder.encode(dados.novaSenha());

        usuario.alterarSenha(novaSenhaCriptografada);

        return new DadosDetalhamentoUsuario(usuario);
    }

    private Usuario buscarUsuarioAtivoPorId(Long id) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));

        if (!Boolean.TRUE.equals(usuario.getAtivo())) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado.");
        }

        return usuario;
    }
}