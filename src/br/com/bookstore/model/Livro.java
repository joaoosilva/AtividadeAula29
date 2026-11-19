package br.com.bookstore.model;

public class Livro {

  private Long codigo;
    private String nomeObra;
 private String escritor;
  private double valor;

    // Inicialização sem código
    public Livro(String nomeObra, String escritor, double valor) {
 this(null, nomeObra, escritor, valor);
    }

    public Long getId() {
        return codigo;
    }

    public void setId(Long id) {
      this.codigo = id;
    }

    public String getTitulo() {
      return nomeObra;
    }

    public String getAutor() {
 return escritor;
    }

    public double getPreco() {
     return valor;
    }

    // Inicialização com código
    public Livro(Long codigo, String nomeObra, String escritor, double valor) {
        if (nomeObra == null || nomeObra.trim().length() < 2) {
            throw new IllegalArgumentException("A obra informada é obrigatória e deve ter no mínimo 2 caracteres!");
        }
        if (escritor == null || escritor.trim().isEmpty()) {
            throw new IllegalArgumentException("O responsável pela obra é obrigatório!");
        }
        if (valor <= 0.0) {
            throw new IllegalArgumentException("O valor da obra deve ser estritamente positivo!");
        }
        this.codigo = codigo;
      this.nomeObra = nomeObra.trim();
 this.escritor = escritor.trim();
        this.valor = valor;
    }

    @Override
    public String toString() {
        return String.format("[#%d] %s (Autor: %s) - R$ %.2f", codigo, nomeObra, escritor, valor);
    }
}