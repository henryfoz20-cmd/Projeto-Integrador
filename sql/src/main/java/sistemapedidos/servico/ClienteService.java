package sistemapedidos.servico;

import sistemapedidos.entidade.Cliente;
import sistemapedidos.repositorio.ClienteRepository;

public class ClienteService {

    // Repositório responsável pela persistência dos clientes
    private final ClienteRepository clienteRepository;

    public ClienteService() {
        this.clienteRepository = new ClienteRepository();
    }

    // Valida e salva um novo cliente
    public void salvar(Cliente cliente) {
        try {
            // Validação: nome obrigatório
            if (cliente.getNome() == null || cliente.getNome().isBlank()) {
                throw new RuntimeException("O nome do cliente não pode ser vazio");
            }

            // Validação: email obrigatório
            if (cliente.getEmail() == null || cliente.getEmail().isBlank()) {
                throw new RuntimeException("O email do cliente não pode ser vazio");
            }

            clienteRepository.salvar(cliente);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar cliente: " + e.getMessage(), e);
        }
    }

    // Busca um cliente pelo ID
    public Cliente buscarPorId(Long id) {
        try {
            Cliente cliente = clienteRepository.buscarPorId(id);
            if (cliente == null) {
                throw new RuntimeException("Cliente não encontrado para o ID: " + id);
            }
            return cliente;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar cliente: " + e.getMessage(), e);
        }
    }

    // Atualiza os dados de um cliente existente
    public void atualizar(Cliente cliente) {
        try {
            if (cliente.getId() == null) {
                throw new RuntimeException("ID do cliente não informado para atualização");
            }
            if (cliente.getNome() == null || cliente.getNome().isBlank()) {
                throw new RuntimeException("O nome do cliente não pode ser vazio");
            }
            clienteRepository.salvar(cliente);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar cliente: " + e.getMessage(), e);
        }
    }
}