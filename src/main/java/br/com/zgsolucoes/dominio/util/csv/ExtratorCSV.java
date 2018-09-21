package br.com.zgsolucoes.dominio.util.csv;

import br.com.zgsolucoes.dominio.util.Extrator;

import java.util.ArrayList;
import java.util.List;

public class ExtratorCSV implements Extrator<List<String>> {

    private String texto;

    @Override
    public List<String> getResultado(String regra) {

        String[] resultadoExtracao;
        resultadoExtracao = texto.split(regra);
        List<String> retorno = new ArrayList<>();

        for (String campo : resultadoExtracao) {
            retorno.add(campo);
        }

        return retorno;
    }

    @Override
    public void setTexto(String texto) {

        this.texto = texto;
    }
}
