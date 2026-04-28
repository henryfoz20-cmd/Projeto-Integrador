package sistemapedidos.repository;

import jakarta.persistence.EntityManager;
import sistemapedidos.config.JPAUtil;
import sistemapedidos.entity.Categoria;
import java.util.List;

public class CategoriaRepository {

    // Persiste uma nova categoria no banco de dados
    public void salvar(Categoria categoria) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(categoria);
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

    // Busca uma categoria pelo ID; retorna null se não encontrada
    public Categoria buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Categoria.class, id);
        } finally {
            em.close();
        }
    }

    // Retorna todas as categorias cadastradas
    public List<Categoria> listarTodas() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("FROM Categoria", Categoria.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Verifica se já existe uma categoria com o nome informado (regra de negócio)
    public boolean existePorNome(String nome) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Long count = em.createQuery(
                            "SELECT COUNT(c) FROM Categoria c WHERE c.nome = :nome", Long.class)
                    .setParameter("nome", nome)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }
}