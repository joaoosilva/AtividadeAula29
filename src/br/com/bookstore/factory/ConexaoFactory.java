package br.com.bookstore.factory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConexaoFactory {
    private static final String URL = "jdbc:sqlite:livraria.db";
 private ConexaoFactory() {
        // Construtor privado para impedir criação direta
    }
public static Connection obterConexao() throws SQLException {
      try {
        Class.forName("org.sqlite.JDBC");
       return DriverManager.getConnection(URL);
    } catch (ClassNotFoundException e) {
        throw new SQLException("O conector JDBC do SQLite não foi localizado!", e);
      }
 } }
