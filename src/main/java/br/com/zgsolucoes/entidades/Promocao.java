package br.com.zgsolucoes.entidades;

import java.math.BigDecimal;

public class Promocao {

    private int id;
    private String descricao;
    private String observacao;
    private int qtdeAtivacao;
    private int precoFinal;
    private int qtdePaga;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getQtdeAtivacao() {
        return qtdeAtivacao;
    }

    public void setQtdeAtivacao(int qtdeAtivacao) {
        this.qtdeAtivacao = qtdeAtivacao;
    }

    public int getPrecoFinal() {
        return precoFinal;
    }

    public void setPrecoFinal(int precoFinal) {
        this.precoFinal = precoFinal;
    }

    public int getQtdePaga() {
        return qtdePaga;
    }

    public void setQtdePaga(int qtdePaga) {
        this.qtdePaga = qtdePaga;
    }

    public BigDecimal ativarPromocao(Produto produto, int quantidade) {

        BigDecimal desconto = new BigDecimal(0.00);

        if (this.precoFinal == 0) {
            desconto = ((new BigDecimal((this.qtdePaga - this.qtdeAtivacao)).multiply(produto.getValor()))
                    .multiply(new BigDecimal((quantidade / this.qtdeAtivacao))));
        } else {
            desconto = (((produto.getValor().multiply(new BigDecimal(this.qtdeAtivacao))).multiply(
                    new BigDecimal(quantidade * this.qtdeAtivacao))).subtract(new BigDecimal(this.precoFinal)));
        }

        return desconto;
    }
}
