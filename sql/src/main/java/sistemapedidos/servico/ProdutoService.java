package sistemapedidos.servico;

import sistemapedidos.entidade.Produto;
import sistemapedidos.repositorio.ProdutoRepository;
import java.util.List;

public class ProdutoService {

    // Repositório responsável pela persistência dos produtos
    private final ProdutoRepository produtoRepository;

    // Classe auxiliar com as regras de validação de produto
    private final RegrasProduto regrasProduto;

    public ProdutoService() {
        this.produtoRepository = new ProdutoRepository();
        this.regrasProduto = new RegrasProduto();
    }

    // Valida e salva um novo produto
    public void salvarProduto(Produto produto) {
        try {
            regrasProduto.validarProdutoExiste(produto);
            regrasProduto.validarNome(produto);
            regrasProduto.validarPreco(produto);
            regrasProduto.validarEstoque(produto);

            // Regra de negócio: nome do produto deve ser único
            boolean nomeExiste = produtoRepository.existePorNome(produto.getNome());
            regrasProduto.validarNomeDuplicado(nomeExiste);

            produtoRepository.salvar(produto);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar produto: " + e.getMessage(), e);
        }
    }

    // Busca um produto pelo ID
    public Produto buscarProdutoPorId(Long id) {
        try {
            Produto produto = produtoRepository.buscarPorId(id);
            regrasProduto.validarProdutoExiste(produto);
            return produto;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar produto: " + e.getMessage(), e);
        }
    }

    // Retorna todos os produtos cadastrados
    public List<Produto> listarProdutos() {
        try {
            return produtoRepository.listarTodos();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar produtos: " + e.getMessage(), e);
        }
    }

    // Valida e atualiza um produto existente
    public void atualizarProduto(Produto produto) {
        try {
            regrasProduto.validarProdutoExiste(produto);
            regrasProduto.validarNome(produto);
            regrasProduto.validarPreco(produto);
            regrasProduto.validarEstoque(produto);

            // Confirma que o produto existe no banco antes de atualizar
            Produto produtoBanco = produtoRepository.buscarPorId(produto.getId());
            regrasProduto.validarProdutoExiste(produtoBanco);

            produtoRepository.atualizar(produto);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar produto: " + e.getMessage(), e);
        }
    }

    // Remove um produto pelo ID
    public void deletarProduto(Long id) {
        try {
            Produto produto = produtoRepository.buscarPorId(id);
            regrasProduto.validarProdutoExiste(produto);
            produtoRepository.deletar(id);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar produto: " + e.getMessage(), e);
        }
    }

    // Regra de negócio: baixa o estoque após uma venda
    public void baixarEstoque(Long idProduto, int quantidade) {
        try {
            Produto produto = produtoRepository.buscarPorId(idProduto);
            regrasProduto.validarProdutoExiste(produto);

            if (quantidade <= 0) {
                throw new RuntimeException("A quantidade deve ser maior que zero");
            }

            // Regra de negócio: estoque não pode ficar negativo
            if (produto.getEstoque() < quantidade) {
                throw new RuntimeException("Estoque insuficiente");
            }

            produto.setEstoque(produto.getEstoque() - quantidade);
            produtoRepository.atualizar(produto);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao baixar estoque: " + e.getMessage(), e);
        }
    }
}