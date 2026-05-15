package sistemapedidos.entity;

import jakarta.persistence.*;

// Entidade JPA mapeada para a tabela 'produtos'
@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "preco")
    private double preco;

    // Coluna adicionada via migration V2
    @Column(name = "estoque")
    private int estoque;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public Produto() {
    }

    public Produto(String nome, double preco, int estoque, Categoria categoria) {
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
    public int getEstoque() { return this.estoque; }
    public void setEstoque(int estoque) { this.estoque = estoque; }
}