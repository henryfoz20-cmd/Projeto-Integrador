package sistemapedidos.repositorio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import sistemapedidos.configuracao.JPAUtil;
import sistemapedidos.entidade.Usuario;

public class UsuarioRepository {
    private EntityManager em = JPAUtil.getEntityManager();

    public Usuario autenticar(String email, String senha) {
        try {
            TypedQuery<Usuario> q = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.email = :email AND u.senha = :senha", Usuario.class);
            q.setParameter("email", email);
            q.setParameter("senha", senha);
            return q.getResultStream().findFirst().orElse(null);
        } catch (Exception e) {
            System.out.println("Erro ao autenticar: " + e.getMessage());
            return null;
        }
    }

    public void registrar(Usuario usuario) {
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Erro ao registrar: " + e.getMessage());
        }
    }
}