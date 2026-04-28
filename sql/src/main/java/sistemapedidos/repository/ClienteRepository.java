package sistemapedidos.repository;

import jakarta.persistence.EntityManager;
import sistemapedidos.config.JPAUtil;
import sistemapedidos.entity.Cliente;
import java.util.List;

public class ClienteRepository {

    // Persiste um novo cliente no banco de dados
    public void salvar(Cliente cliente) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // desfaz em caso de erro
            }
            throw e;
        } finally {
            em.close();
        }
    }

    // Busca um cliente pelo ID; retorna null se não encontrado
    public Cliente buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }
}