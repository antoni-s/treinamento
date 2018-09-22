package br.com.zgsolucoes;

import br.com.zgsolucoes.dominio.Caixa;
import br.com.zgsolucoes.dominio.util.Extrator;
import br.com.zgsolucoes.dominio.util.LerArquivo;
import br.com.zgsolucoes.dominio.util.csv.ExtratorCSV;
import br.com.zgsolucoes.dominio.util.regex.ExtratorRegex;
import br.com.zgsolucoes.entidades.Produto;
import br.com.zgsolucoes.entidades.Promocao;
import br.com.zgsolucoes.persistencia.ProdutoDAO;
import br.com.zgsolucoes.persistencia.PromocaoDAO;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class CaixaTest {

    private static Caixa caixa;

    @Test
    public void popularBD() throws IOException {

        Principal principal = new Principal();
        principal.iniciarBD();

        LerArquivo lerArquivo = new LerArquivo();
        Extrator extrator;
        PromocaoDAO promocaoDAO = new PromocaoDAO();
        Promocao objPromocao = new Promocao();
        ProdutoDAO produtoDAO = new ProdutoDAO();

        List<String> promocoes, produtos, objeto = new ArrayList<>();
        promocoes = lerArquivo.lerArquivo("/home/antonio/Documents/promoções.csv");
        extrator = new ExtratorCSV();

        for (int i = 1; i < promocoes.size(); i++) {
            extrator.setTexto(promocoes.get(i));
            objeto = ((ExtratorCSV) extrator).getResultado(",");
            objPromocao.setId(parseInt(objeto.get(0)));
            objPromocao.setDescricao(objeto.get(1));
            objPromocao.setObservacao(objeto.get(2));
            objPromocao.setQtdeAtivacao(objeto.get(3).equals("") ? 0 : Integer.parseInt(objeto.get(3)));
            objPromocao.setPrecoFinal(objeto.get(4).equals("") ? 0 : Integer.parseInt(objeto.get(4)));
            if (objeto.get(4).equals("")) {
                objPromocao.setQtdePaga(parseInt(objeto.get(5)));
            } else {
                objPromocao.setQtdePaga(0);
            }
            promocaoDAO.inserirPromocao(objPromocao);
        }

        //produtos = lerArquivo.lerArquivo("/home/antonio/Documents/Arquivo_dados_checkout.txt");
        produtos = lerArquivo.lerArquivo("/home/antonio/Documents/testProd.txt");
        extrator = new ExtratorRegex();
        Produto produto = new Produto();
        String retorno;

        for (int i = 0; i < produtos.size(); i++) {
            extrator.setTexto(produtos.get(i));

            retorno = extrator.getResultado("(?<=id: )\\d+(<=|)").toString();
            produto.setId(Integer.parseInt(retorno.subSequence(1, retorno.length()-1).toString()));
            retorno = extrator.getResultado("(?<=descricao: )\\w+(<=|)").toString();
            produto.setDescricao(retorno.subSequence(1, retorno.length()-1).toString());
            retorno = extrator.getResultado("(?<=valor: )\\d+.?\\d+(<=|)").toString();
            produto.setValor(new BigDecimal(Float.parseFloat(retorno.subSequence(1, retorno.length()-1).toString())));
            retorno = extrator.getResultado("(?<=promocao: ).?\\d+(<=|)").toString();
            produto.setPromocao(promocaoDAO.obterPromocao(Integer.parseInt(retorno.subSequence(1, retorno.length()-1).toString())));
            produtoDAO.inserirProdutos(produto);
        }

    }
    
    @Test
    public void casoTeste1() {

        caixa = new Caixa();
        caixa.adicionarProduto(1);
        Assert.assertEquals(new BigDecimal("50.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("0.00"), caixa.getTotalDiscount());
        caixa.adicionarProduto(1);
        Assert.assertEquals(new BigDecimal("100.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("0.00"), caixa.getTotalDiscount());
        caixa.adicionarProduto(1);
        Assert.assertEquals(new BigDecimal("130.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("20.00"), caixa.getTotalDiscount());
        caixa.adicionarProduto(1);
        Assert.assertEquals(new BigDecimal("180.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("20.00"), caixa.getTotalDiscount());
        caixa.adicionarProduto(1);
        Assert.assertEquals(new BigDecimal("230.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("20.00"), caixa.getTotalDiscount());
        caixa.adicionarProduto(1);
        Assert.assertEquals(new BigDecimal("260.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("40.00"), caixa.getTotalDiscount());
        caixa.removerProduto(1);
        Assert.assertEquals(new BigDecimal("230.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("20.00"), caixa.getTotalDiscount());
    }

    @Test
    public void casoTeste2() {

        caixa = new Caixa();
        caixa.adicionarProduto(4);
        Assert.assertEquals(new BigDecimal("15.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("0.00"), caixa.getTotalDiscount());
        caixa.adicionarProduto(1);
        Assert.assertEquals(new BigDecimal("65.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("0.00"), caixa.getTotalDiscount());
        caixa.adicionarProduto(2);
        Assert.assertEquals(new BigDecimal("95.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("0.00"), caixa.getTotalDiscount());
        caixa.adicionarProduto(1);
        Assert.assertEquals(new BigDecimal("145.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("0.00"), caixa.getTotalDiscount());
        caixa.adicionarProduto(2);
        Assert.assertEquals(new BigDecimal("160.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("15.00"), caixa.getTotalDiscount());
        caixa.adicionarProduto(1);
        Assert.assertEquals(new BigDecimal("190.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("35.00"), caixa.getTotalDiscount());
        caixa.removerProduto(1);
        Assert.assertEquals(new BigDecimal("160.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("15.00"), caixa.getTotalDiscount());
        caixa.removerProduto(2);
        Assert.assertEquals(new BigDecimal("145.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("0.00"), caixa.getTotalDiscount());
    }

    @Test
    public void casoTeste3() {

        caixa = new Caixa();
        caixa.adicionarProduto(3);
        Assert.assertEquals(new BigDecimal("20.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("0.00"), caixa.getTotalDiscount());
        caixa.adicionarProduto(3);
        Assert.assertEquals(new BigDecimal("40.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("0.00"), caixa.getTotalDiscount());
        caixa.adicionarProduto(3);
        Assert.assertEquals(new BigDecimal("40.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("20.00"), caixa.getTotalDiscount());
        caixa.adicionarProduto(3);
        Assert.assertEquals(new BigDecimal("60.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("20.00"), caixa.getTotalDiscount());
        caixa.removerProduto(3);
        Assert.assertEquals(new BigDecimal("40.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("20.00"), caixa.getTotalDiscount());
        caixa.removerProduto(3);
        Assert.assertEquals(new BigDecimal("40.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("0.00"), caixa.getTotalDiscount());
    }

    @Test
    public void casoTeste4() {

        Caixa caixa = new Caixa();
        caixa.adicionarProduto(3);
        Assert.assertEquals(new BigDecimal("20.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("0.00"), caixa.getTotalDiscount());
        caixa.adicionarProduto(2);
        Assert.assertEquals(new BigDecimal("50.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("0.00"), caixa.getTotalDiscount());
        caixa.adicionarProduto(2);
        Assert.assertEquals(new BigDecimal("65.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("15.00"), caixa.getTotalDiscount());
        caixa.removerProduto(2);
        Assert.assertEquals(new BigDecimal("50.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("0.00"), caixa.getTotalDiscount());
    }
}
