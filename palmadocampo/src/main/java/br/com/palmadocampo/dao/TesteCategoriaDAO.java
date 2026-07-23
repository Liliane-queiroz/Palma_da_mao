package br.com.palmadocampo.dao;

import java.util.List;

import br.com.palmadocampo.model.Categoria;

public class TesteCategoriaDAO {

    public static void main(String[] args) {
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        try {
            System.out.println("=== Testando listarTodas() ===");
            List<Categoria> todasAsCategorias = categoriaDAO.listarTodas();
            System.out.println("Total de categorias: " + todasAsCategorias.size());
            for (Categoria categoria : todasAsCategorias) {
                System.out.println("  ID " + categoria.getId()
                                 + " | " + categoria.getDescricao()
                                 + " | situacao_id: " + categoria.getSituacaoId());
            }

            System.out.println("\n=== Testando listarAtivas() ===");
            List<Categoria> categoriasAtivas = categoriaDAO.listarAtivas();
            System.out.println("Categorias ATIVAS: " + categoriasAtivas.size());
            for (Categoria categoria : categoriasAtivas) {
                System.out.println("  ID " + categoria.getId()
                                 + " | " + categoria.getDescricao());
            }

        } catch (Exception excecao) {
            System.out.println("Erro no teste:");
            excecao.printStackTrace();
        }
    }
}