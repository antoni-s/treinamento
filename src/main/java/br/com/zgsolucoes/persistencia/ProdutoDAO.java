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
    private final String CREATE_TABLE_PRODUTO = "CREATE TABLE IF NOT EXISTS PRODUTOS(ID INTEGER NOT NULL, " +
            "DESCRICAO VARCHAR(20) NOT NULL, PRECO FLOAT NOT NULL, FKPROMACAO INTEGER NOT NULL, PRIMARY KEY (ID));";

    public void inserirProdutos(Produto produto) {

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement criar = conexao.prepareStatement(CREATE_TABLE_PRODUTO);) {

            criar.execute();

            PreparedStatement pst = conexao.prepareStatement(SQL_INSERE_PRODUTOS);
            pst.setInt(1, produto.getId());
            pst.setString(2, produto.getDescricao());
            pst.setBigDecimal(3, produto.getValor());
            pst.setInt(4, produto.getPromocao().getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            //System.out.println(e);
            //throw new IllegalArgumentException("Erro ao gravar arquivo!");
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
        } catch (SQLException e) {
            throw new IllegalArgumentException("Erro ao gravar arquivo!");
        }

        return produto;
    }
}
