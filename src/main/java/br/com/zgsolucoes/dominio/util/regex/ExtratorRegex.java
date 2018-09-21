package br.com.zgsolucoes.dominio.util.regex;

import br.com.zgsolucoes.dominio.util.Extrator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtratorRegex implements Extrator<List<String>> {

    private String texto;
    @Override
    public List<String> getResultado(String regex) {

        List<String> resultadoExtracao = new ArrayList<>();

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(texto);

        while (matcher.find()) {

            String campo = matcher.group();
            resultadoExtracao.add(campo);
        }

        return resultadoExtracao;
    }

    @Override
    public void setTexto(String texto) {

        this.texto = texto;
    }
}
