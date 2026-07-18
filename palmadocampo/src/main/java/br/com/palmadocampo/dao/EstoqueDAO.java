package br.com.palmadocampo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.palmadocampo.model.Estoque;

public class EstoqueDAO {

    public void inserir(Estoque estoque) throws SQLException {
        String sql = "INSERT INTO estoque (usuario_id, produto_id, est_qtd, est_unidade, situacao_id) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            comando.setInt(1, estoque.getUsuarioId());
            comando.setInt(2, estoque.getProdutoId());
            comando.setBigDecimal(3, estoque.getQuantidade());
            comando.setString(4, estoque.getUnidade());
            comando.setInt(5, estoque.getSituacaoId());
            comando.executeUpdate();

            try (ResultSet resultado = comando.getGeneratedKeys()) {
                if (resultado.next()) {
                    estoque.setId(resultado.getInt(1));
                }
            }
        }
    }

    public List<Estoque> listarTodos() throws SQLException {
        String sql = "SELECT est_id, usuario_id, produto_id, est_qtd, est_unidade, "
                   + "situacao_id, data_criacao, data_atualizacao "
                   + "FROM estoque ORDER BY est_id";

        List<Estoque> estoques = new ArrayList<>();

        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                estoques.add(montarEstoque(resultado));
            }
        }

        return estoques;
    }

    public Estoque buscarPorId(int id) throws SQLException {
        String sql = "SELECT est_id, usuario_id, produto_id, est_qtd, est_unidade, "
                   + "situacao_id, data_criacao, data_atualizacao "
                   + "FROM estoque WHERE est_id = ?";

        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, id);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return montarEstoque(resultado);
                }
            }
        }

        return null;
    }

    public List<Estoque> listarPorUsuario(int usuarioId) throws SQLException {
        String sql = "SELECT est_id, usuario_id, produto_id, est_qtd, est_unidade, "
                   + "situacao_id, data_criacao, data_atualizacao "
                   + "FROM estoque WHERE usuario_id = ? ORDER BY data_criacao DESC";

        List<Estoque> estoques = new ArrayList<>();

        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, usuarioId);

            try (ResultSet resultado = comando.executeQuery()) {
                while (resultado.next()) {
                    estoques.add(montarEstoque(resultado));
                }
            }
        }

        return estoques;
    }

    public void atualizar(Estoque estoque) throws SQLException {
        String sql = "UPDATE estoque SET usuario_id = ?, produto_id = ?, est_qtd = ?, "
                   + "est_unidade = ?, situacao_id = ? WHERE est_id = ?";

        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, estoque.getUsuarioId());
            comando.setInt(2, estoque.getProdutoId());
            comando.setBigDecimal(3, estoque.getQuantidade());
            comando.setString(4, estoque.getUnidade());
            comando.setInt(5, estoque.getSituacaoId());
            comando.setInt(6, estoque.getId());
            comando.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM estoque WHERE est_id = ?";

        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, id);
            comando.executeUpdate();
        }
    }

    private Estoque montarEstoque(ResultSet resultado) throws SQLException {
        Estoque estoque = new Estoque();
        estoque.setId(resultado.getInt("est_id"));
        estoque.setUsuarioId(resultado.getInt("usuario_id"));
        estoque.setProdutoId(resultado.getInt("produto_id"));
        estoque.setQuantidade(resultado.getBigDecimal("est_qtd"));
        estoque.setUnidade(resultado.getString("est_unidade"));
        estoque.setSituacaoId(resultado.getInt("situacao_id"));
        estoque.setDataCriacao(resultado.getTimestamp("data_criacao").toLocalDateTime());
        estoque.setDataAtualizacao(resultado.getTimestamp("data_atualizacao").toLocalDateTime());
        return estoque;
    }
}