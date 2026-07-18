package br.com.palmadocampo.dao;

import java.util.List;

import br.com.palmadocampo.model.Situacao;

public class TesteSituacaoDAO {

    public static void main(String[] args) {
        SituacaoDAO situacaoDAO = new SituacaoDAO();

        try {
            System.out.println("Situações cadastradas atualmente:");
            List<Situacao> situacoes = situacaoDAO.listarTodas();
            for (Situacao situacao : situacoes) {
                System.out.println("  ID " + situacao.getId() + " | " + situacao.getDescricao());
            }

            Situacao situacaoBuscada = situacaoDAO.buscarPorId(1);
            System.out.println("\nBuscada pelo ID 1: " + situacaoBuscada.getDescricao());

        } catch (Exception excecao) {
            System.out.println("Erro no teste:");
            excecao.printStackTrace();
        }
    }
}