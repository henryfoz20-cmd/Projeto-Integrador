package sistemapedidos.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "espetinhos")
public class Espetinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sabor;
    private double preco;
    private int estoque;

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSabor() { return sabor; }
    public void setSabor(String sabor) { this.sabor = sabor; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
    public int getEstoque() { return estoque; }
    public void setEstoque(int estoque) { this.estoque = estoque; }
}