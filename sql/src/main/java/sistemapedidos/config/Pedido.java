package sistemapedidos.entity;

import jakarta.persistence.*;
import java.util.List;

// Entidade JPA mapeada para a tabela 'pedidos'
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total")
    private Double total;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToMany
    @JoinTable(
            name = "pedido_produto",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private List<Produto> produtos;

    // Construtor vazio exigido pelo JPA
    public Pedido() {}

    public Pedido(Double total, Cliente cliente) {
        this.total = total;
        this.cliente = cliente;
    }

    public Long getId() { return id; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public List<Produto> getProdutos() { return produtos; }
    public void setProdutos(List<Produto> produtos) { this.produtos = produtos; }
}