package br.com.palmadocampo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import br.com.palmadocampo.model.Produto;
import br.com.palmadocampo.model.ProdutoVitrine;

/*Insere um novo produto(tabela) no banco e guarda o ID gerado*/
public class ProdutoDAO {

    public void inserir(Produto produto) throws SQLException {
        String sql = "INSERT INTO produto (prod_nome, prod_descricao, prod_preco_estimado, "
                   + "prod_foto_url, prod_data_prevista_entrega, categoria_id, situacao_id) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            comando.setString(1, produto.getNome());
            comando.setString(2, produto.getDescricao());
            comando.setBigDecimal(3, produto.getPrecoEstimado());
            comando.setString(4, produto.getFotoUrl());

            if (produto.getDataPrevistaEntrega() != null) {
                comando.setDate(5, Date.valueOf(produto.getDataPrevistaEntrega()));
            } else {
                comando.setNull(5, Types.DATE);
            }

            comando.setInt(6, produto.getCategoriaId());
            comando.setInt(7, produto.getSituacaoId());
            comando.executeUpdate();

            try (ResultSet resultado = comando.getGeneratedKeys()) {
                if (resultado.next()) {
                    produto.setId(resultado.getInt(1));
                }
            }
        }
    }

    public List<Produto> listarTodos() throws SQLException {
        String sql = "SELECT prod_id, prod_nome, prod_descricao, prod_preco_estimado, "
                   + "prod_foto_url, prod_data_prevista_entrega, categoria_id, situacao_id, "
                   + "data_criacao, data_atualizacao "
                   + "FROM produto ORDER BY prod_nome";

        List<Produto> produtos = new ArrayList<>();

        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                produtos.add(montarProduto(resultado));
            }
        }

        return produtos;
    }

/*Comando apenas para admin executar*/ 
    public Produto buscarPorId(int id) throws SQLException {
        String sql = "SELECT prod_id, prod_nome, prod_descricao, prod_preco_estimado, "
                   + "prod_foto_url, prod_data_prevista_entrega, categoria_id, situacao_id, "
                   + "data_criacao, data_atualizacao "
                   + "FROM produto WHERE prod_id = ?";

        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, id);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return montarProduto(resultado);
                }
            }
        }

        return null;
    }

/*Comando apenas para admin executar*/ 
    public void atualizar(Produto produto) throws SQLException {
        String sql = "UPDATE produto SET prod_nome = ?, prod_descricao = ?, "
                   + "prod_preco_estimado = ?, prod_foto_url = ?, prod_data_prevista_entrega = ?, "
                   + "categoria_id = ?, situacao_id = ? WHERE prod_id = ?";

        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, produto.getNome());
            comando.setString(2, produto.getDescricao());
            comando.setBigDecimal(3, produto.getPrecoEstimado());
            comando.setString(4, produto.getFotoUrl());

            if (produto.getDataPrevistaEntrega() != null) {
                comando.setDate(5, Date.valueOf(produto.getDataPrevistaEntrega()));
            } else {
                comando.setNull(5, Types.DATE);
            }

            comando.setInt(6, produto.getCategoriaId());
            comando.setInt(7, produto.getSituacaoId());
            comando.setInt(8, produto.getId());
            comando.executeUpdate();
        }
    }

 /*Comando apenas para admin executar*/    
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM produto WHERE prod_id = ?";

        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, id);
            comando.executeUpdate();
        }
    }

    private Produto montarProduto(ResultSet resultado) throws SQLException {
        Produto produto = new Produto();
        produto.setId(resultado.getInt("prod_id"));
        produto.setNome(resultado.getString("prod_nome"));
        produto.setDescricao(resultado.getString("prod_descricao"));
        produto.setPrecoEstimado(resultado.getBigDecimal("prod_preco_estimado"));
        produto.setFotoUrl(resultado.getString("prod_foto_url"));

        Date dataPrevista = resultado.getDate("prod_data_prevista_entrega");
        if (dataPrevista != null) {
            produto.setDataPrevistaEntrega(dataPrevista.toLocalDate());
        }

        produto.setCategoriaId(resultado.getInt("categoria_id"));
        produto.setSituacaoId(resultado.getInt("situacao_id"));
        produto.setDataCriacao(resultado.getTimestamp("data_criacao").toLocalDateTime());
        produto.setDataAtualizacao(resultado.getTimestamp("data_atualizacao").toLocalDateTime());
        return produto;
    }

/*Da um select em algumas coluna da tabela produto e uni o id categoria da tabela produdo com o id categoria da tabela categoria e ordena por ordem descrescente a coluna de data criação */
    public List<ProdutoVitrine> listarTodosComCategoria() throws SQLException {
    String sql = "SELECT p.prod_id, p.prod_nome, p.prod_descricao, p.prod_preco_estimado, "
               + "p.prod_foto_url, c.ctg_descricao "
               + "FROM produto p "
               + "INNER JOIN categoria c ON p.categoria_id = c.ctg_id "
               + "WHERE p.situacao_id = 1 "
               + "ORDER BY p.data_criacao DESC";

    List<ProdutoVitrine> produtos = new ArrayList<>();

    try (Connection conexao = ConexaoFactory.getConexao();
         PreparedStatement comando = conexao.prepareStatement(sql);
         ResultSet resultado = comando.executeQuery()) {

        while (resultado.next()) {
            ProdutoVitrine produto = new ProdutoVitrine();
            produto.setId(resultado.getInt("prod_id"));
            produto.setNome(resultado.getString("prod_nome"));
            produto.setDescricao(resultado.getString("prod_descricao"));
            produto.setPrecoEstimado(resultado.getBigDecimal("prod_preco_estimado"));
            produto.setFotoUrl(resultado.getString("prod_foto_url"));
            produto.setCategoriaDescricao(resultado.getString("ctg_descricao"));
            produtos.add(produto);
        }
    }

    return produtos;
}

}