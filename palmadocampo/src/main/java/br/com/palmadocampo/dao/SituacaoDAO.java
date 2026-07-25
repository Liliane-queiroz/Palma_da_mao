package br.com.palmadocampo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.palmadocampo.model.Situacao;


/* Classe responsável por acessar o banco de dados da tabela situacao*/
public class SituacaoDAO {

    public void inserir(Situacao situacao) throws SQLException {
        String sql = "INSERT INTO situacao (sit_descricao) VALUES (?)";

        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            comando.setString(1, situacao.getDescricao());
            comando.executeUpdate();

            try (ResultSet resultado = comando.getGeneratedKeys()) {
                if (resultado.next()) {
                    situacao.setId(resultado.getInt(1));
                }
            }
        }
    }

    public List<Situacao> listarTodas() throws SQLException {
        String sql = "SELECT sit_id, sit_descricao FROM situacao ORDER BY sit_descricao";

        List<Situacao> situacoes = new ArrayList<>();

        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                Situacao situacao = new Situacao();
                situacao.setId(resultado.getInt("sit_id"));
                situacao.setDescricao(resultado.getString("sit_descricao"));
                situacoes.add(situacao);
            }
        }

        return situacoes;
    }

/*Comando apenas para admin executar*/ 
    public Situacao buscarPorId(int id) throws SQLException {
        String sql = "SELECT sit_id, sit_descricao FROM situacao WHERE sit_id = ?";

        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, id);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    Situacao situacao = new Situacao();
                    situacao.setId(resultado.getInt("sit_id"));
                    situacao.setDescricao(resultado.getString("sit_descricao"));
                    return situacao;
                }
            }
        }

        return null;
    }

/*Comando apenas para admin executar*/ 
    public void atualizar(Situacao situacao) throws SQLException {
        String sql = "UPDATE situacao SET sit_descricao = ? WHERE sit_id = ?";

        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, situacao.getDescricao());
            comando.setInt(2, situacao.getId());
            comando.executeUpdate();
        }
    }

/*Comando apenas para admin executar*/ 
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM situacao WHERE sit_id = ?";

        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, id);
            comando.executeUpdate();
        }
    }
}