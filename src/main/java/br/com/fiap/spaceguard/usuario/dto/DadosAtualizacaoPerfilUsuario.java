package br.com.fiap.spaceguard.usuario.dto;

import br.com.fiap.spaceguard.usuario.model.PerfilUsuario;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoPerfilUsuario(
        @NotNull(message = "O id do usuário é obrigatório.")
        Long id,

        @NotNull(message = "O perfil é obrigatório.")
        PerfilUsuario perfil
) {
}