package br.com.zgsolucoes.persistencia;

import br.com.zgsolucoes.entidades.Promocao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PromocaoDAO {

    private final String SQL_INSERE_PROMOCAO = "INSERT INTO PROMOCAO(ID, DESCRICAO, OBS, QTD_ATIVACAO, " +
            "PRECO_FINAL, QTD_PAGA) VALUES ( ?, ?, ?, ?, ?, ?);";
    private final String SQL_SELECIONA_PROMOCAO = "SELECT * FROM PROMOCAO WHERE ID = ?";

    public void inserirPromocao(Promocao promocao) {

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement pst = conexao.prepareStatement(SQL_INSERE_PROMOCAO);) {

            pst.setInt(1, promocao.getId());
            pst.setString(2, promocao.getDescricao());
            pst.setString(3, promocao.getObservacao());
            pst.setInt(4, promocao.getQtdeAtivacao());
            pst.setInt(5, promocao.getPrecoFinal());
            pst.setInt(6, promocao.getQtdePaga());
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Promocao obterPromocao(int codigoPromocao) {

        Promocao promocao = new Promocao();

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement pst = conexao.prepareStatement(SQL_SELECIONA_PROMOCAO);) {

            pst.setInt(1, codigoPromocao);
            ResultSet resultado = pst.executeQuery();

            if (resultado.next()) {
                promocao.setId(resultado.getInt("ID"));
                promocao.setDescricao(resultado.getString("DESCRICAO"));
                promocao.setObservacao(resultado.getString("OBS"));
                promocao.setQtdeAtivacao(resultado.getInt("QTD_ATIVACAO"));
                promocao.setPrecoFinal(resultado.getInt("PRECO_FINAL"));
                promocao.setQtdePaga(resultado.getInt("QTD_PAGA"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return promocao;
    }
}
