package br.com.fiap.spaceguard.alerta.repository;

import br.com.fiap.spaceguard.alerta.model.Alerta;
import br.com.fiap.spaceguard.alerta.model.StatusAlerta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    Page<Alerta> findAllByStatus(StatusAlerta status, Pageable pageable);
}