package br.com.zgsolucoes;
import br.com.zgsolucoes.persistencia.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Principal {

    private final String CREATE_TABLE_PRODUTO = "CREATE TABLE IF NOT EXISTS PRODUTOS(ID INTEGER NOT NULL, " +
            "DESCRICAO VARCHAR(20) NOT NULL, PRECO FLOAT NOT NULL, FKPROMACAO INTEGER NOT NULL, PRIMARY KEY (ID));";
    private final String CREATE_TABLE_PROMOCAO = "CREATE TABLE IF NOT EXISTS PROMOCAO( ID INTEGER NOT NULL, " +
            "DESCRICAO VARCHAR(20) NOT NULL, OBS VARCHAR(50), QTD_ATIVACAO INT NOT NULL, PRECO_FINAL INT, " +
            "QTD_PAGA INT, PRIMARY KEY (ID));";

    public void iniciarBD() {

        try(Connection conexao = Conexao.getConexao();
            PreparedStatement criarProduto = conexao.prepareStatement(CREATE_TABLE_PRODUTO);
            PreparedStatement criarPromocao = conexao.prepareStatement(CREATE_TABLE_PROMOCAO);){
            criarPromocao.execute();
            criarProduto.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
