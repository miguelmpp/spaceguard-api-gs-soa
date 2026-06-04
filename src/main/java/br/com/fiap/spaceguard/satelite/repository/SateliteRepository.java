package br.com.fiap.spaceguard.satelite.repository;

import br.com.fiap.spaceguard.satelite.model.Satelite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SateliteRepository extends JpaRepository<Satelite, Long> {

    Boolean existsByCodigo(String codigo);

    Page<Satelite> findAllByAtivoTrue(Pageable pageable);

    Optional<Satelite> findByIdAndAtivoTrue(Long id);
}