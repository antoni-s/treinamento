package br.com.zgsolucoes;

import br.com.zgsolucoes.entidades.Produto;
import br.com.zgsolucoes.entidades.Promocao;
import br.com.zgsolucoes.persistencia.ProdutoDAO;

import java.math.BigDecimal;

public class Principal {

    public static void main(String[] args) {

        ProdutoDAO produtoDAO = new ProdutoDAO();

        Promocao promocao = new Promocao();
        promocao.setId(1);

        Produto produto = new Produto();
        produto.setId(1);
        produto.setDescricao("test");
        produto.setPromocao(promocao);
        produto.setValor(BigDecimal.valueOf(1.33333));

        produtoDAO.inserirProdutos(produto);

        System.out.println(produtoDAO.obterProduto(1));
    }
}
