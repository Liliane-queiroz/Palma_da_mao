package br.com.palmadocampo.dao;

import java.util.List;

import br.com.palmadocampo.model.ProdutoVitrine;

public class TesteVitrine {

    public static void main(String[] args) {
        ProdutoDAO produtoDAO = new ProdutoDAO();

        try {
            List<ProdutoVitrine> produtos = produtoDAO.listarTodosComCategoria();

            System.out.println("=== Vitrine Palma do Campo ===\n");

            for (ProdutoVitrine produto : produtos) {
                System.out.println(produto.getNome()
                                 + " - R$ " + produto.getPrecoEstimado()
                                 + " - " + produto.getCategoriaDescricao());
            }

        } catch (Exception excecao) {
            System.out.println("Erro no teste:");
            excecao.printStackTrace();
        }
    }
}