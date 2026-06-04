package br.com.fiap.spaceguard.usuario.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String senha;

    @Enumerated(EnumType.STRING)
    private PerfilUsuario perfil;

    private Boolean ativo = true;

    public Usuario(String login, String senha, PerfilUsuario perfil) {
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
        this.ativo = true;
    }

    public void atualizarPerfil(PerfilUsuario perfil) {
        this.perfil = perfil;
    }

    public void alterarSenha(String novaSenhaCriptografada) {
        this.senha = novaSenhaCriptografada;
    }

    public void desativar() {
        this.ativo = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.perfil == PerfilUsuario.ADMIN) {
            return List.of(
                    new SimpleGrantedAuthority(PerfilUsuario.ADMIN.getAuthority()),
                    new SimpleGrantedAuthority(PerfilUsuario.OPERADOR.getAuthority()),
                    new SimpleGrantedAuthority(PerfilUsuario.ANALISTA.getAuthority())
            );
        }

        return List.of(new SimpleGrantedAuthority(this.perfil.getAuthority()));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return Boolean.TRUE.equals(this.ativo);
    }

    @Override
    public boolean isAccountNonLocked() {
        return Boolean.TRUE.equals(this.ativo);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Boolean.TRUE.equals(this.ativo);
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(this.ativo);
    }
}