package br.com.zgsolucoes.entidades;

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
}
