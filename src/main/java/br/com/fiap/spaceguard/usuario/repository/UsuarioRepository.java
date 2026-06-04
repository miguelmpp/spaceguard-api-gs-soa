package br.com.fiap.spaceguard.usuario.repository;

import br.com.fiap.spaceguard.usuario.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByLogin(String login);

    Boolean existsByLogin(String login);

    Page<Usuario> findAllByAtivoTrue(Pageable pageable);
}