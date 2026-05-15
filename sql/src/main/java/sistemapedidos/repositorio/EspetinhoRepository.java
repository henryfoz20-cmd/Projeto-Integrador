package sistemapedidos.repositorio;

import jakarta.persistence.EntityManager;
import sistemapedidos.configuracao.JPAUtil;
import sistemapedidos.entidade.Espetinho;
import sistemapedidos.entidade.Usuario;
import java.util.List;

public class EspetinhoRepository {
    private EntityManager em = JPAUtil.getEntityManager();

    public void listarCardapio() {
        try {
            List<Espetinho> lista = em.createQuery("SELECT e FROM Espetinho e", Espetinho.class).getResultList();
            lista.forEach(e -> System.out.println(e.getId() + " - " + e.getSabor() + " - R$ " + e.getPreco() + " (Estoque: " + e.getEstoque() + ")"));
        } catch (Exception e) {
            System.out.println("Erro ao listar cardápio: " + e.getMessage());
        }
    }

    public void atualizarEstoque(Long id, int quantidade, Usuario usuario) {
        if (!usuario.isChefe()) {
            System.out.println("Apenas o chefe pode atualizar o estoque!");
            return;
        }
        try {
            em.getTransaction().begin();
            Espetinho e = em.find(Espetinho.class, id);
            if (e != null) {
                e.setEstoque(e.getEstoque() + quantidade);
                em.merge(e);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Erro ao atualizar estoque: " + e.getMessage());
        }
    }

    public Espetinho findById(Long id) {
        try {
            return em.find(Espetinho.class, id);
        } catch (Exception e) {
            System.out.println("Erro ao buscar espetinho: " + e.getMessage());
            return null;
        }
    }
}