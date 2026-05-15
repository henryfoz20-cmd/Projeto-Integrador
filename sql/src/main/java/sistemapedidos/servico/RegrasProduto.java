package sistemapedidos.servico;

import sistemapedidos.entidade.Produto;

public class RegrasProduto {
    // Classe auxiliar para regras de produto

    public void validarProdutoExiste(Produto produto) {
        if (produto == null) {
            throw new RuntimeException("Produto não encontrado.");
        }
    }

    public void validarNome(Produto produto) {
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new RuntimeException("Nome do produto é obrigatório.");
        }
    }

    public void validarPreco(Produto produto) {
        if (produto.getPreco() <= 0) {
            throw new RuntimeException("Preço do produto deve ser maior que zero.");
        }
    }

    public void validarEstoque(Produto produto) {
        if (produto.getEstoque() < 0) {
            throw new RuntimeException("Estoque do produto não pode ser negativo.");
        }
    }

    public void validarNomeDuplicado(boolean nomeExiste) {
        if (nomeExiste) {
            throw new RuntimeException("Já existe um produto com este nome.");
        }
    }
}