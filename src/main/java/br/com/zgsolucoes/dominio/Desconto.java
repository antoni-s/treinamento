package br.com.zgsolucoes.dominio;

import br.com.zgsolucoes.entidades.Produto;

import java.math.BigDecimal;

public class Desconto {

    public BigDecimal ativarPromocao (Produto produto, int quantidade) {

        BigDecimal totalDesconto = new BigDecimal(0);

        if (produto.getPromocao().getPrecoFinal() != 0) {

            quantidade = quantidade / produto.getPromocao().getQtdeAtivacao();
            totalDesconto =  produto.getValor().multiply( new BigDecimal(quantidade));
            totalDesconto = totalDesconto.subtract(new BigDecimal(quantidade * produto.getPromocao().getPrecoFinal()));
        } else {
            int qtdDesconto = produto.getPromocao().getQtdeAtivacao() - produto.getPromocao().getQtdePaga();
            int qtdAgrupado = quantidade / produto.getPromocao().getQtdeAtivacao();
            qtdDesconto = qtdDesconto * qtdAgrupado;
            totalDesconto = produto.getValor().multiply(new BigDecimal(qtdDesconto));
        }

        return totalDesconto;
    }

}
