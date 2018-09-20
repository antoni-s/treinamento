package br.com.zgsolucoes.dominio;

import br.com.zgsolucoes.entidades.Produto;
import br.com.zgsolucoes.persistencia.ProdutoDAO;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Caixa {

    private Map<Produto, BigDecimal> itens;
    private ProdutoDAO produtoDAO;
    private Desconto desconto;

    public Caixa() {

        this.produtoDAO = new ProdutoDAO();
        this.itens = new HashMap<Produto, BigDecimal>();
    }

    public void adicionarProduto(int codigoProduto) {

        Produto produto = produtoDAO.obterProduto(codigoProduto);

        if (!itens.containsKey(produto)) {
            itens.put(produto, new BigDecimal(1));
        } else {
            BigDecimal quantidade = itens.get(produto).add(new BigDecimal(1));
            itens.put(produto, quantidade);
        }
    }

    public void removerProduto(int codigoProduto) {

        Produto produto = produtoDAO.obterProduto(codigoProduto);
        BigDecimal quantidade = itens.get(produto).subtract(new BigDecimal(1));

        if (quantidade.equals(new BigDecimal(0))) {
            itens.remove(produto);
        } else {
            itens.put(produto, quantidade);
        }
    }

    public BigDecimal getTotalPrice() {

        BigDecimal valorSemDesconto = new BigDecimal(0);
        Produto produtoAtual;

        for (Map.Entry<Produto, BigDecimal> produto : itens.entrySet()) {
            produtoAtual = produto.getKey();
            valorSemDesconto = valorSemDesconto.add(produtoAtual.getValor().multiply(produto.getValue()));
        }

        BigDecimal valorComDesconto = valorSemDesconto.subtract(getTotalDiscount());

        return valorComDesconto;
    }

    public BigDecimal getTotalDiscount() {

        BigDecimal valorDesconto = new BigDecimal(0);
        Produto produtoAtual;

        for (Map.Entry<Produto, BigDecimal> produto : itens.entrySet()) {
            produtoAtual = produto.getKey();

            if (produtoAtual.getPromocao().getId() != -1) {
                valorDesconto = valorDesconto.add(desconto.ativarPromocao(produtoAtual, produto.getValue()));
            }
        }

        return valorDesconto;
    }
}
