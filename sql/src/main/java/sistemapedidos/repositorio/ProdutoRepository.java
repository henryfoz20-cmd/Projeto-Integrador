package sistemapedidos.repositorio;

import jakarta.persistence.EntityManager;
import sistemapedidos.configuracao.JPAUtil;
import sistemapedidos.entidade.Produto;
import java.util.List;

public class ProdutoRepository {

    // Persiste um novo produto no banco de dados
    public void salvar(Produto produto) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(produto);
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

    // Atualiza um produto existente no banco de dados
    public void atualizar(Produto produto) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(produto);
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

    // Busca um produto pelo ID; retorna null se não encontrado
    public Produto buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Produto.class, id);
        } finally {
            em.close();
        }
    }

    // Retorna todos os produtos cadastrados
    public List<Produto> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("FROM Produto", Produto.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Remove um produto pelo ID; ignora silenciosamente se não encontrado
    public void deletar(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Busca antes de abrir transação para evitar transação desnecessária
            Produto produto = em.find(Produto.class, id);
            if (produto == null) return;

            em.getTransaction().begin();
            em.remove(produto);
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

    // Verifica se já existe um produto com o mesmo nome (regra de negócio)
    public boolean existePorNome(String nome) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Long quantidade = em.createQuery(
                            "SELECT COUNT(p) FROM Produto p WHERE p.nome = :nome", Long.class)
                    .setParameter("nome", nome)
                    .getSingleResult();
            return quantidade > 0;
        } finally {
            em.close();
        }
    }
}