package br.com.palmadocampo.dao;

import java.math.BigDecimal;
import java.util.List;

import br.com.palmadocampo.model.Estoque;

public class TesteEstoqueDAO {

    public static void main(String[] args) {
        EstoqueDAO estoqueDAO = new EstoqueDAO();

        try {
            Estoque estoqueNovo = new Estoque(
                    1,                          // usuario_id (Maria Joaquina)
                    1,                          // produto_id (Alface Crespa - ajuste se for outro ID)
                    new BigDecimal("15.500"),   // 15,5 kg
                    "KG",                       // unidade
                    1                           // situacao_id (ATIVO)
            );

            estoqueDAO.inserir(estoqueNovo);
            System.out.println("Estoque inserido com ID: " + estoqueNovo.getId());

            System.out.println("\nTodos os registros de estoque:");
            List<Estoque> estoques = estoqueDAO.listarTodos();
            for (Estoque estoque : estoques) {
                System.out.println("  ID " + estoque.getId()
                                 + " | usuário " + estoque.getUsuarioId()
                                 + " | produto " + estoque.getProdutoId()
                                 + " | " + estoque.getQuantidade() + " " + estoque.getUnidade());
            }

            System.out.println("\nEstoques do usuário 1 (Maria Joaquina):");
            List<Estoque> estoquesDaMaria = estoqueDAO.listarPorUsuario(1);
            for (Estoque estoque : estoquesDaMaria) {
                System.out.println("  ID " + estoque.getId()
                                 + " | produto " + estoque.getProdutoId()
                                 + " | " + estoque.getQuantidade() + " " + estoque.getUnidade()
                                 + " | criado em " + estoque.getDataCriacao());
            }

            Estoque estoqueBuscado = estoqueDAO.buscarPorId(estoqueNovo.getId());
            System.out.println("\nBuscado pelo ID " + estoqueNovo.getId()
                             + ": " + estoqueBuscado.getQuantidade() + " " + estoqueBuscado.getUnidade());

            estoqueBuscado.setQuantidade(new BigDecimal("12.000"));
            estoqueDAO.atualizar(estoqueBuscado);
            System.out.println("\nQuantidade atualizada.");

            Estoque estoqueConfirmado = estoqueDAO.buscarPorId(estoqueNovo.getId());
            System.out.println("Nova quantidade: " + estoqueConfirmado.getQuantidade()
                             + " " + estoqueConfirmado.getUnidade());

        } catch (Exception excecao) {
            System.out.println("Erro no teste:");
            excecao.printStackTrace();
        }
    }
}