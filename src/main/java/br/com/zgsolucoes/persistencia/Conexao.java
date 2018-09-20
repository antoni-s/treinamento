package br.com.zgsolucoes.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static String usuario = "postgres";
    private static String senha = "root";
    private static String PathBase = "localhost:5432/checkout";
    private static String URL = "jdbc:postgresql://" + PathBase;

    public static Connection getConexao() {

        try {

            return DriverManager.getConnection(URL, usuario, senha);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

}
