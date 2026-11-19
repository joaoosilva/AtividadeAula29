package br.com.bookstore.main;

import br.com.bookstore.dao.LivroDAO;
import br.com.bookstore.dao.LivroDAOSqlite;
import br.com.bookstore.model.Livro;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("====================================================");
        System.out.println("              DEMONSTRAÇÃO DA LIVRARIA");
        System.out.println("====================================================");
        LivroDAO estoque = new LivroDAOSqlite(); // Usa a implementação SQLite do DAO
        System.out.println("\n--- Inclusão de títulos ---");
        Livro primeiro = new Livro("Clean Code", "Robert C. Martin", 89.90);
         Livro segundo = new Livro("Design Patterns", "Erich Gamma et al.", 125.50);
       Livro terceiro = new Livro("Refactoring", "Martin Fowler", 98.00);
        estoque.salvar(primeiro);
          estoque.salvar(segundo);
      estoque.salvar(terceiro);
        System.out.println("Título incluído: " + primeiro);
         System.out.println("Título incluído: " + segundo);
        System.out.println("Título incluído: " + terceiro);
        System.out.println("\n--- Consulta pelo ID 2 ---");
        estoque.buscarPorId(2L).ifPresentOrElse(
                item -> System.out.println("Registro encontrado: " + item),
                  () -> System.out.println("Livro não localizado!"));
        System.out.println("\n--- Consulta pelo ID 999 ---");
        estoque.buscarPorId(999L).ifPresentOrElse(
           item -> System.out.println("Registro encontrado: " + item),
                () -> System.out.println("Não há registro cadastrado com o ID 999."));
        System.out.println("\n--- Acervo disponível ---");
        List<Livro> acervo = estoque.listarTodos();
        System.out.printf("%-6s | %-25s | %-22s | %-10s%n", "CÓD.", "OBRA", "AUTOR", "VALOR");
        System.out.println("----------------------------------------------------------------------");
        for (Livro titulo : acervo) {
            System.out.printf("#%-5d | %-25s | %-22s | R$ %8.2f%n", titulo.getId(), titulo.getTitulo(), titulo.getAutor(),
             titulo.getPreco());
        }
        System.out.println("====================================================");
    }
}