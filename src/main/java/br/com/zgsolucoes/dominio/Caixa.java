package br.com.zgsolucoes.dominio;

import br.com.zgsolucoes.entidades.Produto;
import br.com.zgsolucoes.persistencia.ProdutoDAO;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Caixa {

    private Map<Produto, Integer> itens;
    private ProdutoDAO produtoDAO;

    public Caixa() {

        this.produtoDAO = new ProdutoDAO();
        this.itens = new HashMap<Produto, Integer>();
    }

    public void adicionarProduto(int codigoProduto) {

        Produto produtoNovo = produtoDAO.obterProduto(codigoProduto);

        if (itens.equals(null)) {
            itens.put(produtoNovo,1);
        } else {
            Produto produtoVerificar;
            boolean existe = false;
            for (Map.Entry<Produto, Integer> produto : itens.entrySet()) {
                produtoVerificar = produto.getKey();
                if (produtoNovo.getId() == produtoVerificar.getId()) {
                    int quantidade = itens.get(produtoVerificar) + 1;
                    itens.put(produtoVerificar, quantidade);
                    existe = true;
                }
            }
            if (!existe) {
                itens.put(produtoNovo,1);
            }
        }
    }

    public void removerProduto(int codigoProduto) {

        Produto produto = produtoDAO.obterProduto(codigoProduto);
        int quantidade = itens.get(produto) - 1;

        if (quantidade == 0) {
            itens.remove(produto);
        } else {
            itens.put(produto, quantidade);
        }
    }

    public BigDecimal getTotalPrice() {

        BigDecimal valorSemDesconto = new BigDecimal("0.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        Produto produtoAtual;

        for (Map.Entry<Produto, Integer> produto : itens.entrySet()) {
            produtoAtual = produto.getKey();
            valorSemDesconto = valorSemDesconto.add(produtoAtual.getValor().multiply(new BigDecimal(produto.getValue())));
        }

        BigDecimal valorComDesconto = valorSemDesconto.subtract(getTotalDiscount());

        return valorComDesconto.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getTotalDiscount() {

        BigDecimal valorDesconto = new BigDecimal(0);
        Produto produtoAtual;

        for (Map.Entry<Produto, Integer> produto : itens.entrySet()) {
            produtoAtual = produto.getKey();

            if (produtoAtual.getPromocao().getId() > 0) {
                valorDesconto = valorDesconto.add(produtoAtual.getPromocao().ativarPromocao(
                        produtoAtual, produto.getValue()));
            }
        }

        return valorDesconto.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
