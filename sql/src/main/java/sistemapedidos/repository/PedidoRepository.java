package sistemapedidos.repository;

import jakarta.persistence.EntityManager;
import sistemapedidos.config.JPAUtil;
import sistemapedidos.entity.Pedido;
import java.util.List;

public class PedidoRepository {

    // Persiste um novo pedido no banco de dados
    public void salvar(Pedido pedido) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(pedido);
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

    // Atualiza um pedido existente no banco de dados
    public void atualizar(Pedido pedido) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(pedido);
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

    // Busca um pedido pelo ID; retorna null se não encontrado
    public Pedido buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Pedido.class, id);
        } finally {
            em.close();
        }
    }

    // Retorna todos os pedidos cadastrados
    public List<Pedido> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("FROM Pedido", Pedido.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Remove um pedido pelo ID; ignora silenciosamente se não encontrado
    public void deletar(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Busca antes de abrir transação para evitar transação desnecessária
            Pedido pedido = em.find(Pedido.class, id);
            if (pedido == null) return;

            em.getTransaction().begin();
            em.remove(pedido);
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
}