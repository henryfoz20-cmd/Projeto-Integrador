package sistemapedidos;

import sistemapedidos.config.FlywayConfig;
import sistemapedidos.entity.Categoria;
import sistemapedidos.entity.Cliente;
import sistemapedidos.entity.Pedido;
import sistemapedidos.entity.Produto;
import sistemapedidos.repository.CategoriaRepository;
import sistemapedidos.service.ClienteService;
import sistemapedidos.service.PedidoService;
import sistemapedidos.service.ProdutoService;

public class Main {
    public static void main(String[] args) {

        // Executa as migrations do Flyway antes de qualquer operação
        FlywayConfig.migrate();

        try {
            // Instancia os services responsáveis pelas regras de negócio
            ClienteService clienteService = new ClienteService();
            ProdutoService produtoService = new ProdutoService();
            PedidoService pedidoService = new PedidoService();
            CategoriaRepository categoriaRepository = new CategoriaRepository();

            // 1. Criar e salvar categoria (necessária para vincular ao produto)
            Categoria categoria = new Categoria("Eletrodoméstico");
            categoriaRepository.salvar(categoria);
            System.out.println("Categoria salva com sucesso!");

            // 2. Criar e salvar cliente
            Cliente cliente = new Cliente("joao", "joaozin@email.com");
            clienteService.salvar(cliente);
            System.out.println("Cliente salvo com sucesso!");

            // 3. Criar e salvar produto vinculado à categoria
            Produto produto = new Produto("microondas", 100.0, 10, categoria);
            produtoService.salvarProduto(produto);
            System.out.println("Produto salvo com sucesso!");

            // 4. Criar e salvar pedido vinculado ao cliente
            Pedido pedido = new Pedido(100.0, cliente);
            pedidoService.salvarPedido(pedido);
            System.out.println("Pedido salvo com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro no sistema: " + e.getMessage());
        }
    }
}