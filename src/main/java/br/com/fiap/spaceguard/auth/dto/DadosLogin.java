package br.com.fiap.spaceguard.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosLogin(
        @NotBlank(message = "O login é obrigatório.")
        String login,

        @NotBlank(message = "A senha é obrigatória.")
        String senha
) {
}