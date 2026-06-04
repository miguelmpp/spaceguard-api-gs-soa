package br.com.fiap.spaceguard.alerta.model;

import br.com.fiap.spaceguard.leitura.model.LeituraSensor;
import br.com.fiap.spaceguard.satelite.model.Satelite;
import br.com.fiap.spaceguard.sensor.model.Sensor;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "alertas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "satelite_id", nullable = false)
    private Satelite satelite;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leitura_sensor_id", nullable = false)
    private LeituraSensor leituraSensor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelAlerta nivel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAlerta status;

    @Column(nullable = false, length = 500)
    private String mensagem;

    @Column(name = "valor_registrado", nullable = false, precision = 12, scale = 4)
    private BigDecimal valorRegistrado;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    @Column(name = "resolvido_em")
    private LocalDateTime resolvidoEm;

    public Alerta(
            Sensor sensor,
            LeituraSensor leituraSensor,
            NivelAlerta nivel,
            String mensagem
    ) {
        this.sensor = sensor;
        this.satelite = sensor.getSatelite();
        this.leituraSensor = leituraSensor;
        this.nivel = nivel;
        this.status = StatusAlerta.ABERTO;
        this.mensagem = mensagem;
        this.valorRegistrado = leituraSensor.getValor();
        this.criadoEm = LocalDateTime.now();
    }

    public void resolver() {
        this.status = StatusAlerta.RESOLVIDO;
        this.resolvidoEm = LocalDateTime.now();
    }

    public boolean estaAberto() {
        return this.status == StatusAlerta.ABERTO;
    }
}