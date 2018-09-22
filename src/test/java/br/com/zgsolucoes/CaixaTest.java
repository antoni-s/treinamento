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
    public void casoTestePopularBD() throws IOException {

        Principal principal = new Principal();
        principal.iniciarBD();

        LerArquivo lerArquivo = new LerArquivo();
        Extrator extrator;
        PromocaoDAO promocaoDAO = new PromocaoDAO();
        Promocao objPromocao = new Promocao();
        ProdutoDAO produtoDAO = new ProdutoDAO();

        List<String> promocoes, produtos, objeto = new ArrayList<>();
        promocoes = lerArquivo.lerArquivo("dados-para-testes/promoções.csv");
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

        produtos = lerArquivo.lerArquivo("dados-para-testes/Arquivo_dados_checkout.txt");
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
        caixa.adicionarProduto(2);
        Assert.assertEquals(new BigDecimal("64.80"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("0.00"), caixa.getTotalDiscount());
        caixa.adicionarProduto(2);
        Assert.assertEquals(new BigDecimal("129.59"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("0.00"), caixa.getTotalDiscount());
        caixa.adicionarProduto(2);
        Assert.assertEquals(new BigDecimal("130.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("64.39"), caixa.getTotalDiscount());
        caixa.adicionarProduto(5);
        Assert.assertEquals(new BigDecimal("207.47"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("64.39"), caixa.getTotalDiscount());
        caixa.adicionarProduto(5);
        Assert.assertEquals(new BigDecimal("284.95"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("64.39"), caixa.getTotalDiscount());
        caixa.adicionarProduto(5);
        Assert.assertEquals(new BigDecimal("260.00"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("166.81"), caixa.getTotalDiscount());
        caixa.removerProduto(5);
        Assert.assertEquals(new BigDecimal("284.95"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("64.39"), caixa.getTotalDiscount());
    }

    @Test
    public void casoTeste2() {

        caixa = new Caixa();
        caixa.adicionarProduto(50);
        Assert.assertEquals(new BigDecimal("96.53"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("0.00"), caixa.getTotalDiscount());
        caixa.adicionarProduto(16);
        Assert.assertEquals(new BigDecimal("187.59"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("0.00"), caixa.getTotalDiscount());
        caixa.adicionarProduto(42);
        Assert.assertEquals(new BigDecimal("222.57"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("0.00"), caixa.getTotalDiscount());
        caixa.adicionarProduto(16);
        Assert.assertEquals(new BigDecimal("313.63"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("0.00"), caixa.getTotalDiscount());
        caixa.adicionarProduto(42);
        Assert.assertEquals(new BigDecimal("323.64"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("24.97"), caixa.getTotalDiscount());
        caixa.adicionarProduto(16);
        Assert.assertEquals(new BigDecimal("271.53"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("168.14"), caixa.getTotalDiscount());
        caixa.removerProduto(16);
        Assert.assertEquals(new BigDecimal("323.64"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("24.97"), caixa.getTotalDiscount());
        caixa.removerProduto(42);
        Assert.assertEquals(new BigDecimal("313.63"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("0.00"), caixa.getTotalDiscount());
    }

    @Test
    public void casoTeste3() {

        caixa = new Caixa();
        caixa.adicionarProduto(23);
        Assert.assertEquals(new BigDecimal("27.07"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("0.00"), caixa.getTotalDiscount());
        caixa.adicionarProduto(23);
        Assert.assertEquals(new BigDecimal("54.14"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("0.00"), caixa.getTotalDiscount());
        caixa.adicionarProduto(23);
        Assert.assertEquals(new BigDecimal("54.14"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("27.07"), caixa.getTotalDiscount());
        caixa.adicionarProduto(23);
        Assert.assertEquals(new BigDecimal("81.21"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("27.07"), caixa.getTotalDiscount());
        caixa.removerProduto(23);
        Assert.assertEquals(new BigDecimal("54.14"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("27.07"), caixa.getTotalDiscount());
        caixa.removerProduto(23);
        Assert.assertEquals(new BigDecimal("54.14"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("0.00"), caixa.getTotalDiscount());
    }

    @Test
    public void casoTeste4() {

        Caixa caixa = new Caixa();
        caixa.adicionarProduto(28);
        Assert.assertEquals(new BigDecimal("47.91"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("0.00"), caixa.getTotalDiscount());
        caixa.adicionarProduto(35);
        Assert.assertEquals(new BigDecimal("118.57"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("0.00"), caixa.getTotalDiscount());
        caixa.adicionarProduto(35);
        Assert.assertEquals(new BigDecimal("92.91"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("96.32"), caixa.getTotalDiscount());
        caixa.removerProduto(35);
        Assert.assertEquals(new BigDecimal("118.57"), caixa.getTotalPrice());
        Assert.assertEquals(new BigDecimal("0.00"), caixa.getTotalDiscount());
    }
}
