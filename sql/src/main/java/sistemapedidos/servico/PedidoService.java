package sistemapedidos.servico;

import sistemapedidos.entidade.Cliente;
import sistemapedidos.entidade.Pedido;
import sistemapedidos.repositorio.ClienteRepository;
import sistemapedidos.repositorio.PedidoRepository;
import java.util.List;

public class PedidoService {

    // Repositório de pedidos
    private final PedidoRepository pedidoRepository;

    // Repositório de clientes — usado para validar o cliente do pedido
    private final ClienteRepository clienteRepository;

    public PedidoService() {
        this.pedidoRepository = new PedidoRepository();
        this.clienteRepository = new ClienteRepository();
    }

    // Valida e salva um novo pedido
    public void salvarPedido(Pedido pedido) {
        try {
            if (pedido == null) {
                throw new RuntimeException("Pedido inválido");
            }

            // Regra de negócio: total não pode ser negativo
            if (pedido.getTotal() < 0) {
                throw new RuntimeException("O valor do pedido não pode ser negativo");
            }

            // Regra de negócio: pedido precisa estar vinculado a um cliente
            if (pedido.getCliente() == null) {
                throw new RuntimeException("O pedido deve estar vinculado a um cliente");
            }

            // Confirma que o cliente existe no banco antes de salvar
            Cliente cliente = clienteRepository.buscarPorId(pedido.getCliente().getId());
            if (cliente == null) {
                throw new RuntimeException("Cliente não encontrado");
            }

            pedido.setCliente(cliente);
            pedidoRepository.salvar(pedido);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar pedido: " + e.getMessage(), e);
        }
    }

    // Busca um pedido pelo ID
    public Pedido buscarPedidoPorId(Long id) {
        try {
            Pedido pedido = pedidoRepository.buscarPorId(id);
            if (pedido == null) {
                throw new RuntimeException("Pedido não encontrado");
            }
            return pedido;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar pedido: " + e.getMessage(), e);
        }
    }

    // Retorna todos os pedidos cadastrados
    public List<Pedido> listarPedidos() {
        try {
            return pedidoRepository.listarTodos();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar pedidos: " + e.getMessage(), e);
        }
    }

    // Valida e atualiza um pedido existente
    public void atualizarPedido(Pedido pedido) {
        try {
            if (pedido == null) {
                throw new RuntimeException("Pedido inválido");
            }

            if (pedido.getId() == null) {
                throw new RuntimeException("ID do pedido é obrigatório para atualização");
            }

            // Confirma que o pedido existe no banco
            Pedido pedidoBanco = pedidoRepository.buscarPorId(pedido.getId());
            if (pedidoBanco == null) {
                throw new RuntimeException("Pedido não encontrado");
            }

            // Regra de negócio: total não pode ser negativo
            if (pedido.getTotal() < 0) {
                throw new RuntimeException("O valor do pedido não pode ser negativo");
            }

            if (pedido.getCliente() == null) {
                throw new RuntimeException("O pedido deve possuir um cliente");
            }

            // Confirma que o cliente existe no banco antes de atualizar
            Cliente cliente = clienteRepository.buscarPorId(pedido.getCliente().getId());
            if (cliente == null) {
                throw new RuntimeException("Cliente não encontrado");
            }

            pedido.setCliente(cliente);
            pedidoRepository.atualizar(pedido);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar pedido: " + e.getMessage(), e);
        }
    }

    // Remove um pedido pelo ID
    public void deletarPedido(Long id) {
        try {
            Pedido pedido = pedidoRepository.buscarPorId(id);
            if (pedido == null) {
                throw new RuntimeException("Pedido não encontrado");
            }
            pedidoRepository.deletar(id);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar pedido: " + e.getMessage(), e);
        }
    }
}