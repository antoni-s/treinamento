package br.com.zgsolucoes.dominio;

import br.com.zgsolucoes.entidades.Produto;
import br.com.zgsolucoes.persistencia.ProdutoDAO;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Caixa {

    private Map<Produto, Integer> itens;
    private ProdutoDAO produtoDAO;
    private Desconto desconto;

    public Caixa() {

        this.produtoDAO = new ProdutoDAO();
        this.itens = new HashMap<Produto, Integer>();
        this.desconto = new Desconto();
    }

    public void adicionarProduto(int codigoProduto) {

        Produto produto = produtoDAO.obterProduto(codigoProduto);

        if (!itens.containsKey(produto)) {
            itens.put(produto,1);
        } else {
            int quantidade = itens.get(produto) + 1;
            itens.put(produto, quantidade);
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

        BigDecimal valorSemDesconto = new BigDecimal(0);
        Produto produtoAtual;

        for (Map.Entry<Produto, Integer> produto : itens.entrySet()) {
            produtoAtual = produto.getKey();
            valorSemDesconto = valorSemDesconto.add(produtoAtual.getValor().multiply(new BigDecimal(produto.getValue())));
        }

        BigDecimal valorComDesconto = valorSemDesconto.subtract(getTotalDiscount());

        return valorComDesconto;
    }

    public BigDecimal getTotalDiscount() {

        BigDecimal valorDesconto = new BigDecimal(0);
        Produto produtoAtual;

        for (Map.Entry<Produto, Integer> produto : itens.entrySet()) {
            produtoAtual = produto.getKey();

            if (produtoAtual.getPromocao().getId() != -1) {
                valorDesconto = valorDesconto.add(desconto.ativarPromocao(produtoAtual, produto.getValue()));
            }
        }

        return valorDesconto;
    }
}
