package br.com.fiap.spaceguard.leitura.repository;

import br.com.fiap.spaceguard.leitura.model.LeituraSensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeituraSensorRepository extends JpaRepository<LeituraSensor, Long> {

    Page<LeituraSensor> findAllBySensorId(Long sensorId, Pageable pageable);
}