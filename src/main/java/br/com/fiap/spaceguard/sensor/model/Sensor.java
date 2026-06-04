package br.com.fiap.spaceguard.sensor.model;

import br.com.fiap.spaceguard.satelite.model.Satelite;
import br.com.fiap.spaceguard.sensor.dto.DadosAtualizacaoSensor;
import br.com.fiap.spaceguard.sensor.dto.DadosCadastroSensor;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Entity
@Table(name = "sensores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSensor tipo;

    @Column(name = "unidade_medida", nullable = false)
    private String unidadeMedida;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusSensor status;

    @Column(name = "limite_minimo", nullable = false, precision = 12, scale = 4)
    private BigDecimal limiteMinimo;

    @Column(name = "limite_maximo", nullable = false, precision = 12, scale = 4)
    private BigDecimal limiteMaximo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "satelite_id", nullable = false)
    private Satelite satelite;

    @Column(nullable = false)
    private Boolean ativo = true;

    public Sensor(DadosCadastroSensor dados, Satelite satelite) {
        this.nome = dados.nome();
        this.tipo = dados.tipo();
        this.unidadeMedida = dados.unidadeMedida();
        this.status = dados.status();
        this.limiteMinimo = dados.limiteMinimo();
        this.limiteMaximo = dados.limiteMaximo();
        this.satelite = satelite;
        this.ativo = true;
    }

    public void atualizarInformacoes(DadosAtualizacaoSensor dados) {
        if (StringUtils.hasText(dados.nome())) {
            this.nome = dados.nome();
        }

        if (StringUtils.hasText(dados.unidadeMedida())) {
            this.unidadeMedida = dados.unidadeMedida();
        }

        if (dados.status() != null) {
            this.status = dados.status();
        }

        if (dados.limiteMinimo() != null) {
            this.limiteMinimo = dados.limiteMinimo();
        }

        if (dados.limiteMaximo() != null) {
            this.limiteMaximo = dados.limiteMaximo();
        }
    }

    public void desativar() {
        this.ativo = false;
        this.status = StatusSensor.INATIVO;
    }

    public boolean estaOperacional() {
        return Boolean.TRUE.equals(this.ativo)
                && this.status == StatusSensor.ATIVO
                && this.satelite.estaOperacional();
    }
}