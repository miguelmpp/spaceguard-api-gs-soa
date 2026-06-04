package br.com.fiap.spaceguard.shared.vo;

import java.math.BigDecimal;

public record FaixaOperacional(
        BigDecimal limiteMinimo,
        BigDecimal limiteMaximo,
        String unidadeMedida
) {

    public boolean estaAbaixoDoMinimo(BigDecimal valor) {
        return valor.compareTo(limiteMinimo) < 0;
    }

    public boolean estaAcimaDoMaximo(BigDecimal valor) {
        return valor.compareTo(limiteMaximo) > 0;
    }

    public boolean estaDentroDaFaixa(BigDecimal valor) {
        return !estaAbaixoDoMinimo(valor) && !estaAcimaDoMaximo(valor);
    }

    public BigDecimal intervalo() {
        return limiteMaximo.subtract(limiteMinimo).abs();
    }

    public String descricao() {
        return limiteMinimo + " a " + limiteMaximo + " " + unidadeMedida;
    }
}