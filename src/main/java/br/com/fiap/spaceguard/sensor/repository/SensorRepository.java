package br.com.fiap.spaceguard.sensor.repository;

import br.com.fiap.spaceguard.sensor.model.Sensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, Long> {

    Page<Sensor> findAllByAtivoTrue(Pageable pageable);

    Page<Sensor> findAllBySateliteIdAndAtivoTrue(Long sateliteId, Pageable pageable);

    Optional<Sensor> findByIdAndAtivoTrue(Long id);

    Boolean existsByNomeAndSateliteIdAndAtivoTrue(String nome, Long sateliteId);
}