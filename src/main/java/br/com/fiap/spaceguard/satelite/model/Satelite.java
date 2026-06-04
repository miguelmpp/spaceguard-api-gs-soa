package br.com.fiap.spaceguard.satelite.model;

import br.com.fiap.spaceguard.satelite.dto.DadosAtualizacaoSatelite;
import br.com.fiap.spaceguard.satelite.dto.DadosCadastroSatelite;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Entity
@Table(name = "satelites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Satelite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String operador;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusSatelite status;

    @Column(nullable = false)
    private String orbita;

    @Column(name = "data_lancamento", nullable = false)
    private LocalDate dataLancamento;

    @Column(nullable = false)
    private Boolean ativo = true;

    public Satelite(DadosCadastroSatelite dados) {
        this.nome = dados.nome();
        this.codigo = dados.codigo();
        this.operador = dados.operador();
        this.status = dados.status();
        this.orbita = dados.orbita();
        this.dataLancamento = dados.dataLancamento();
        this.ativo = true;
    }

    public void atualizarInformacoes(DadosAtualizacaoSatelite dados) {
        if (StringUtils.hasText(dados.nome())) {
            this.nome = dados.nome();
        }

        if (StringUtils.hasText(dados.operador())) {
            this.operador = dados.operador();
        }

        if (dados.status() != null) {
            this.status = dados.status();
        }

        if (StringUtils.hasText(dados.orbita())) {
            this.orbita = dados.orbita();
        }

        if (dados.dataLancamento() != null) {
            this.dataLancamento = dados.dataLancamento();
        }
    }

    public void desativar() {
        this.ativo = false;
        this.status = StatusSatelite.INATIVO;
    }

    public boolean estaOperacional() {
        return Boolean.TRUE.equals(this.ativo) && this.status == StatusSatelite.ATIVO;
    }
}