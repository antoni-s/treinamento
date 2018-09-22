package br.com.zgsolucoes;

import br.com.zgsolucoes.dominio.Caixa;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

public class CaixaTest {

    private static Caixa caixa;

    @Test
    public void casoTestePopularBD() throws IOException {

        Principal principal = new Principal();
        principal.iniciarBD();
        principal.popularBanco();

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
