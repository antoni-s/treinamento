package br.com.zgsolucoes.persistencia;

import br.com.zgsolucoes.entidades.Produto;
import br.com.zgsolucoes.entidades.Promocao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ProdutoDAO {

    private final String SQL_INSERE_PRODUTOS = "INSERT INTO PRODUTOS(ID, DESCRICAO, PRECO, FKPROMACAO) " +
            "VALUES ( ?, ?, ?, ?);";
    private final String SQL_SELECIONA_PRODUTO = "SELECT * FROM PRODUTOS WHERE ID = ?";

    public void inserirProdutos(Produto produto) {

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement pst = conexao.prepareStatement(SQL_INSERE_PRODUTOS);) {

            pst.setInt(1, produto.getId());
            pst.setString(2, produto.getDescricao());
            pst.setBigDecimal(3, produto.getValor());
            pst.setInt(4, produto.getPromocao().getId());
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Produto obterProduto(int codigoProduto) {

        Produto produto = new Produto();
        PromocaoDAO promocaoDAO = new PromocaoDAO();

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement pst = conexao.prepareStatement(SQL_SELECIONA_PRODUTO);) {

            pst.setInt(1, codigoProduto);
            ResultSet resultado = pst.executeQuery();

            if (resultado.next()) {
                produto.setId(resultado.getInt("ID"));
                produto.setDescricao(resultado.getString("DESCRICAO"));
                produto.setValor(resultado.getBigDecimal("PRECO"));

                if (resultado.getInt("FKPROMACAO") < 1) {
                    Promocao promocao = new Promocao();
                    promocao.setId(resultado.getInt("FKPROMACAO"));
                    produto.setPromocao(promocao);
                } else {
                    produto.setPromocao(promocaoDAO.obterPromocao(resultado.getInt("FKPROMACAO")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produto;
    }
}
