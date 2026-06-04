package br.com.fiap.spaceguard.usuario.dto;

import br.com.fiap.spaceguard.usuario.model.PerfilUsuario;
import br.com.fiap.spaceguard.usuario.model.Usuario;

public record DadosDetalhamentoUsuario(
        Long id,
        String login,
        PerfilUsuario perfil,
        Boolean ativo
) {

    public DadosDetalhamentoUsuario(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getLogin(),
                usuario.getPerfil(),
                usuario.getAtivo()
        );
    }
}