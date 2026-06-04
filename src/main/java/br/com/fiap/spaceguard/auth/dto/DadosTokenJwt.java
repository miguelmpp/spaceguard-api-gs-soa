package br.com.fiap.spaceguard.auth.dto;

public record DadosTokenJwt(
        String token,
        String tipo,
        String login,
        String perfil
) {
}