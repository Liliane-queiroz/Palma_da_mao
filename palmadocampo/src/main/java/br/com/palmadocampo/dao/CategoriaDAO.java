package br.com.palmadocampo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.palmadocampo.model.Categoria;

/*Insere na categoria(tabela) no banco e guarda o ID gerado*/
public class CategoriaDAO {

    public void inserir(Categoria categoria) throws SQLException {
        String sql = "INSERT INTO categoria (ctg_descricao, situacao_id) VALUES (?, ?)";

        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            comando.setString(1, categoria.getDescricao());
            comando.setInt(2, categoria.getSituacaoId());
            comando.executeUpdate();

            try (ResultSet resultado = comando.getGeneratedKeys()) {
                if (resultado.next()) {
                    categoria.setId(resultado.getInt(1));
                }
            }
        }
    }

/*Da um select nas colunas da tabela categoria e ordena descrição por ordem crescente*/
//Retorna todas as categorias//
    public List<Categoria> listarTodas() throws SQLException {
        String sql = "SELECT ctg_id, ctg_descricao, situacao_id, data_criacao, data_atualizacao FROM categoria ORDER BY ctg_descricao";

        List<Categoria> categorias = new ArrayList<>();

        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {


/*Enquanto tiver linhas preenchidas no banco eu crio um novo objeto chamado categoria, defino os valores das colunas e adiciono na lista*/                  
            while (resultado.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(resultado.getInt("ctg_id"));
                categoria.setDescricao(resultado.getString("ctg_descricao"));
                categoria.setSituacaoId(resultado.getInt("situacao_id"));
                categoria.setDataCriacao(resultado.getTimestamp("data_criacao").toLocalDateTime());
                categoria.setDataAtualizacao(resultado.getTimestamp("data_atualizacao").toLocalDateTime());
                categorias.add(categoria);
            }
        }

        return categorias;
    }

/*Da um select nas colunas da tabela categoria onde situacao_id é = 1(ativo) e ordena descrição por ordem crescente*/ 
//Retorna todas só categorias ativas//

    public List<Categoria> listarAtivas() throws SQLException {
    String sql = "SELECT ctg_id, ctg_descricao, situacao_id, data_criacao, data_atualizacao FROM categoria WHERE situacao_id = 1 ORDER BY ctg_descricao";

    List<Categoria> categorias = new ArrayList<>();

    try (Connection conexao = ConexaoFactory.getConexao();
         PreparedStatement comando = conexao.prepareStatement(sql);
         ResultSet resultado = comando.executeQuery()) {

        while (resultado.next()) {
            Categoria categoria = new Categoria();
            categoria.setId(resultado.getInt("ctg_id"));
            categoria.setDescricao(resultado.getString("ctg_descricao"));
            categoria.setSituacaoId(resultado.getInt("situacao_id"));
            categoria.setDataCriacao(resultado.getTimestamp("data_criacao").toLocalDateTime());
            categoria.setDataAtualizacao(resultado.getTimestamp("data_atualizacao").toLocalDateTime());
            categorias.add(categoria);
        }
    }

    return categorias;
}

/*Comando apenas para admin executar*/ 
    public Categoria buscarPorId(int id) throws SQLException {
        String sql = "SELECT ctg_id, ctg_descricao, situacao_id, data_criacao, data_atualizacao FROM categoria WHERE ctg_id = ?";

        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, id);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    Categoria categoria = new Categoria();
                    categoria.setId(resultado.getInt("ctg_id"));
                    categoria.setDescricao(resultado.getString("ctg_descricao"));
                    categoria.setSituacaoId(resultado.getInt("situacao_id"));
                    categoria.setDataCriacao(resultado.getTimestamp("data_criacao").toLocalDateTime());
                    categoria.setDataAtualizacao(resultado.getTimestamp("data_atualizacao").toLocalDateTime());
                    return categoria;
                }
            }
        }

        return null;
    }

/*Comando apenas para admin executar*/ 
    public void atualizar(Categoria categoria) throws SQLException {
        String sql = "UPDATE categoria SET ctg_descricao = ?, situacao_id = ? WHERE ctg_id = ?";

        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, categoria.getDescricao());
            comando.setInt(2, categoria.getSituacaoId());
            comando.setInt(3, categoria.getId());
            comando.executeUpdate();
        }
    }

/*Comando apenas para admin executar*/ 
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM categoria WHERE ctg_id = ?";

        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, id);
            comando.executeUpdate();
        }
    }
}