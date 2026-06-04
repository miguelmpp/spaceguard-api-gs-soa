package br.com.fiap.spaceguard.sensor.avaliacao;

import br.com.fiap.spaceguard.alerta.model.NivelAlerta;
import br.com.fiap.spaceguard.leitura.model.LeituraSensor;
import br.com.fiap.spaceguard.sensor.model.Sensor;
import br.com.fiap.spaceguard.shared.vo.FaixaOperacional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Optional;

public abstract class AvaliadorSensorBase implements AvaliadorLeituraSensor {

    @Override
    public Optional<ResultadoAvaliacaoAlerta> avaliar(Sensor sensor, LeituraSensor leitura) {
        var valor = leitura.getValor();
        var faixa = new FaixaOperacional(
                sensor.getLimiteMinimo(),
                sensor.getLimiteMaximo(),
                sensor.getUnidadeMedida()
        );

        if (faixa.estaDentroDaFaixa(valor)) {
            return Optional.empty();
        }

        var condicao = faixa.estaAbaixoDoMinimo(valor)
                ? "abaixo do limite mínimo"
                : "acima do limite máximo";

        var nivel = calcularNivel(faixa, valor);
        var mensagem = montarMensagem(sensor, faixa, valor, condicao);

        return Optional.of(new ResultadoAvaliacaoAlerta(nivel, mensagem));
    }

    protected NivelAlerta calcularNivel(FaixaOperacional faixa, BigDecimal valor) {
        var intervalo = faixa.intervalo();

        if (intervalo.compareTo(BigDecimal.ZERO) == 0) {
            return NivelAlerta.CRITICO;
        }

        BigDecimal desvio;

        if (faixa.estaAbaixoDoMinimo(valor)) {
            desvio = faixa.limiteMinimo().subtract(valor).abs();
        } else {
            desvio = valor.subtract(faixa.limiteMaximo()).abs();
        }

        var percentualDesvio = desvio
                .divide(intervalo, MathContext.DECIMAL64)
                .multiply(BigDecimal.valueOf(100));

        if (percentualDesvio.compareTo(BigDecimal.valueOf(50)) >= 0) {
            return NivelAlerta.CRITICO;
        }

        if (percentualDesvio.compareTo(BigDecimal.valueOf(25)) >= 0) {
            return NivelAlerta.ALTO;
        }

        if (percentualDesvio.compareTo(BigDecimal.valueOf(10)) >= 0) {
            return NivelAlerta.MEDIO;
        }

        return NivelAlerta.BAIXO;
    }

    protected String montarMensagem(
            Sensor sensor,
            FaixaOperacional faixa,
            BigDecimal valor,
            String condicao
    ) {
        return String.format(
                "%s detectou valor %s para o sensor %s. Valor registrado: %s %s. Faixa esperada: %s. Recomendação: %s",
                getNomeTecnico(),
                condicao,
                sensor.getNome(),
                valor,
                faixa.unidadeMedida(),
                faixa.descricao(),
                getRecomendacao()
        );
    }

    protected abstract String getNomeTecnico();

    protected abstract String getRecomendacao();
}