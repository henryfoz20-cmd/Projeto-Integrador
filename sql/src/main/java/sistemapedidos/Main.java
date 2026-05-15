package sistemapedidos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import sistemapedidos.configuracao.FlywayConfig;
import sistemapedidos.entidade.Espetinho;
import sistemapedidos.entidade.Usuario;
import sistemapedidos.repositorio.EspetinhoRepository;
import sistemapedidos.repositorio.UsuarioRepository;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Executa as migrations do Flyway antes de qualquer operação
        FlywayConfig.migrate();

        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("meuPU");
            em = emf.createEntityManager();

            UsuarioRepository usuarioRepository = new UsuarioRepository();
            EspetinhoRepository espetinhoRepository = new EspetinhoRepository();

            Scanner sc = new Scanner(System.in);

            System.out.println("1 - Login | 2 - Registrar");
            int opcao = sc.nextInt();
            sc.nextLine();

            Usuario usuario = null;
            if (opcao == 1) {
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("Senha: ");
                String senha = sc.nextLine();
                usuario = usuarioRepository.autenticar(email, senha);
                if (usuario == null) {
                    System.out.println("Credenciais inválidas!");
                    return;
                }
            } else {
                Usuario novo = new Usuario();
                System.out.print("Nome: ");
                novo.setNome(sc.nextLine());
                System.out.print("Email: ");
                novo.setEmail(sc.nextLine());
                System.out.print("Senha: ");
                novo.setSenha(sc.nextLine());
                novo.setChefe(false);
                usuarioRepository.registrar(novo);
                System.out.println("Registro concluído!");
                usuario = novo;
            }

            System.out.println("Bem-vindo, " + usuario.getNome());
            espetinhoRepository.listarCardapio();

            double total = 0;
            System.out.println("Digite o ID do espetinho para comprar (0 para sair):");
            while (true) {
                Long id = sc.nextLong();
                if (id == 0) break;
                Espetinho e = espetinhoRepository.findById(id);
                if (e != null && e.getEstoque() > 0) {
                    total += e.getPreco();
                    try {
                        em.getTransaction().begin();
                        e.setEstoque(e.getEstoque() - 1);
                        em.merge(e);
                        em.getTransaction().commit();
                        System.out.println("Adicionado: " + e.getSabor());
                    } catch (Exception ex) {
                        em.getTransaction().rollback();
                        System.out.println("Erro ao processar compra: " + ex.getMessage());
                    }
                } else {
                    System.out.println("Espetinho indisponível!");
                }
            }

            System.out.println("Total da compra: R$ " + total);
        } catch (Exception e) {
            System.out.println("Erro no sistema: " + e.getMessage());
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }
}