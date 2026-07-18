package br.com.palmadocampo.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.com.palmadocampo.model.Produto;

public class TesteProdutoDAO {

    public static void main(String[] args) {
        ProdutoDAO produtoDAO = new ProdutoDAO();

        try {
            Produto produtoNovo = new Produto(
                    "Alface Crespa",
                    "Alface crespa colhida na manhã, sem agrotóxico.",
                    new BigDecimal("3.50"),
                    1,  // categoria_id (Hortaliças e Verduras)
                    1   // situacao_id (ATIVO)
            );
            produtoNovo.setDataPrevistaEntrega(LocalDate.of(2026, 8, 1));

            produtoDAO.inserir(produtoNovo);
            System.out.println("Produto inserido com ID: " + produtoNovo.getId());

            System.out.println("\nProdutos cadastrados:");
            List<Produto> produtos = produtoDAO.listarTodos();
            for (Produto produto : produtos) {
                System.out.println("  ID " + produto.getId()
                                 + " | " + produto.getNome()
                                 + " | R$ " + produto.getPrecoEstimado()
                                 + " | entrega em " + produto.getDataPrevistaEntrega());
            }

            Produto produtoBuscado = produtoDAO.buscarPorId(produtoNovo.getId());
            System.out.println("\nBuscado pelo ID " + produtoNovo.getId()
                             + ": " + produtoBuscado.getNome()
                             + " | descrição: " + produtoBuscado.getDescricao());

            produtoBuscado.setPrecoEstimado(new BigDecimal("4.00"));
            produtoDAO.atualizar(produtoBuscado);
            System.out.println("\nPreço atualizado.");

            Produto produtoConfirmado = produtoDAO.buscarPorId(produtoNovo.getId());
            System.out.println("Novo preço: R$ " + produtoConfirmado.getPrecoEstimado());

        } catch (Exception excecao) {
            System.out.println("Erro no teste:");
            excecao.printStackTrace();
        }
    }
}