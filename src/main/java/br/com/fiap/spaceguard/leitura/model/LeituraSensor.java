package br.com.fiap.spaceguard.leitura.model;

import br.com.fiap.spaceguard.leitura.dto.DadosRegistroLeitura;
import br.com.fiap.spaceguard.sensor.model.Sensor;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "leituras_sensores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class LeituraSensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;

    @Column(nullable = false, precision = 12, scale = 4)
    private BigDecimal valor;

    @Column(name = "registrada_em", nullable = false)
    private LocalDateTime registradaEm;

    public LeituraSensor(DadosRegistroLeitura dados, Sensor sensor) {
        this.sensor = sensor;
        this.valor = dados.valor();
        this.registradaEm = dados.registradaEm() != null
                ? dados.registradaEm()
                : LocalDateTime.now();
    }
}