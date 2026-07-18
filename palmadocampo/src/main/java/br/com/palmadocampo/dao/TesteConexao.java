package br.com.palmadocampo.dao;

import java.sql.Connection;

public class TesteConexao {

    public static void main(String[] args) {
        try {
            Connection conexao = ConexaoFactory.getConexao();
            System.out.println("Conexão bem-sucedida ao banco!");
            System.out.println("Banco: " + conexao.getCatalog());
            conexao.close();
        } catch (Exception e) {
            System.out.println(" Erro ao conectar:");
            
        }
    }
}
