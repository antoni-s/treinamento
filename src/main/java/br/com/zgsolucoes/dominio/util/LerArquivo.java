package br.com.zgsolucoes.dominio.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LerArquivo {

    public List<String> lerArquivo (String nomeArquivo) throws IOException {

        List<String> dadosArquivos = new ArrayList<>();

        Charset utf8 = Charset.forName("UTF-8");
        Path arquivo = Paths.get(nomeArquivo);

        for (String linha : Files.readAllLines(arquivo, utf8)) {
            dadosArquivos.add(linha);
        }

        return dadosArquivos;
    }
}
