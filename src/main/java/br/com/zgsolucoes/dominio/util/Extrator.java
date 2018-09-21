package br.com.zgsolucoes.dominio.util;

public interface Extrator<ResultType> {

    ResultType getResultado(String regra);

    void setTexto(String texto);

}
