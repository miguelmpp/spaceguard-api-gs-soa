package br.com.fiap.spaceguard.usuario.model;

public enum PerfilUsuario {

    ADMIN,
    OPERADOR,
    ANALISTA;

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}