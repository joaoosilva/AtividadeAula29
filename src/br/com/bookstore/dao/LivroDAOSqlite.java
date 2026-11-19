package br.com.bookstore.dao;

import br.com.bookstore.factory.ConexaoFactory;
import br.com.bookstore.model.Livro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LivroDAOSqlite implements LivroDAO {
    
public LivroDAOSqlite() {
        criarTabelaSeNaoExistir();
 }
    
 private void criarTabelaSeNaoExistir() {
        String sql = "CREATE TABLE IF NOT EXISTS livros (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "titulo TEXT NOT NULL, " +
                     "autor TEXT NOT NULL, " +
                     "preco REAL NOT NULL);";
        try (Connection conn = ConexaoFactory.obterConexao();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
           throw new RuntimeException("Não foi possível preparar a tabela 'livros': " + e.getMessage(), e);
        }
 }

 private Livro mapearLinha(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String titulo = rs.getString("titulo");
        String autor = rs.getString("autor");
        double preco = rs.getDouble("preco");
        return new Livro(id, titulo, autor, preco);
 }

    @Override
  public void salvar(Livro obra) {
        String sql = "INSERT INTO livros (titulo, autor, preco) VALUES (?, ?, ?);";
        try (Connection conn = ConexaoFactory.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
          stmt.setString(1, obra.getTitulo());
            stmt.setString(2, obra.getAutor());
        stmt.setDouble(3, obra.getPreco());
            stmt.executeUpdate();
             try (ResultSet chaves = stmt.getGeneratedKeys()) {
                if (chaves.next()) {
                    obra.setId(chaves.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível incluir a obra: " + e.getMessage(), e);
        }
 }

    @Override
  public Optional<Livro> buscarPorId(Long id) {
        String sql = "SELECT id, titulo, autor, preco FROM livros WHERE id = ?;";
        try (Connection conn = ConexaoFactory.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearLinha(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Falha na consulta pelo código: " + e.getMessage(), e);
        }
 }

@Override
 public List<Livro> listarTodos() {
   String sql = "SELECT id, titulo, autor, preco FROM livros ORDER BY titulo;";
     List<Livro> acervo = new ArrayList<>();
        try (Connection conn = ConexaoFactory.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                acervo.add(mapearLinha(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao carregar o acervo: " + e.getMessage(), e);
        }
 return acervo;
 }
}