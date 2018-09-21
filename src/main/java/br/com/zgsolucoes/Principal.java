package br.com.zgsolucoes;

import br.com.zgsolucoes.entidades.Produto;
import br.com.zgsolucoes.entidades.Promocao;
import br.com.zgsolucoes.persistencia.ProdutoDAO;
import br.com.zgsolucoes.persistencia.PromocaoDAO;

import java.math.BigDecimal;

public class Principal {

    public static void main(String[] args) {

        ProdutoDAO produtoDAO = new ProdutoDAO();

        Promocao promocao = new Promocao();
        promocao.setId(2);
        promocao.setQtdePaga(3);
        promocao.setPrecoFinal(4);
        promocao.setQtdeAtivacao(4);
        promocao.setDescricao("tete");
        promocao.setObservacao("casa");

        Produto produto = new Produto();
        produto.setId(2);
        produto.setDescricao("test");
        produto.setPromocao(promocao);
        produto.setValor(BigDecimal.valueOf(1.33333));

        PromocaoDAO promocaoDAO = new PromocaoDAO();

        promocaoDAO.inserirPromocao(promocao);

        produtoDAO.inserirProdutos(produto);

        System.out.println(produtoDAO.obterProduto(1));
    }
}
