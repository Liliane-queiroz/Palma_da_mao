package br.com.palmadocampo.dao;

import java.util.List;

import br.com.palmadocampo.model.Categoria;

public class TesteCategoriaDAO {

    public static void main(String[] args) {
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        try {
            Categoria categoriaNova = new Categoria("Hortaliças", 1);
            categoriaDAO.inserir(categoriaNova);
            System.out.println("Categoria inserida com ID: " + categoriaNova.getId());

            System.out.println("\nCategorias cadastradas:");
            List<Categoria> categorias = categoriaDAO.listarTodas();
            for (Categoria categoria : categorias) {
                System.out.println("  ID " + categoria.getId()
                                 + " | " + categoria.getDescricao()
                                 + " | criada em " + categoria.getDataCriacao());
            }

            Categoria categoriaBuscada = categoriaDAO.buscarPorId(categoriaNova.getId());
            System.out.println("\nBuscada pelo ID " + categoriaNova.getId()
                             + ": " + categoriaBuscada.getDescricao());

            categoriaBuscada.setDescricao("Hortaliças e Verduras");
            categoriaDAO.atualizar(categoriaBuscada);
            System.out.println("\nCategoria atualizada.");

            Categoria categoriaConfirmada = categoriaDAO.buscarPorId(categoriaNova.getId());
            System.out.println("Nova descrição: " + categoriaConfirmada.getDescricao());

            categoriaDAO.deletar(categoriaNova.getId());
            System.out.println("\nCategoria deletada.");

        } catch (Exception excecao) {
            System.out.println("Erro no teste:");
            excecao.printStackTrace();
        }
    }
}