package br.com.fiap.spaceguard.usuario.dto;

import br.com.fiap.spaceguard.usuario.model.PerfilUsuario;
import br.com.fiap.spaceguard.usuario.model.Usuario;

public record DadosListagemUsuario(
        Long id,
        String login,
        PerfilUsuario perfil,
        Boolean ativo
) {

    public DadosListagemUsuario(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getLogin(),
                usuario.getPerfil(),
                usuario.getAtivo()
        );
    }
}