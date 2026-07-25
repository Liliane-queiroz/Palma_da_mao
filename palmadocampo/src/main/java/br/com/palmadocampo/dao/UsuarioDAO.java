package br.com.palmadocampo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.palmadocampo.model.Usuario;

/*responsável por acessar o banco de dados da tabela usuario*/
public class UsuarioDAO {

/*Insere um novo usuário no banco*/
    public void inserir(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (usu_cpfcnpj, usu_nome, usu_telefone, usu_email, "
                   + "usu_senha_hash, usu_endereco, usu_cidade, usu_regiao, usu_nome_propriedade, "
                   + "usu_tipo, situacao_id) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            comando.setString(1, usuario.getCpfCnpj());
            comando.setString(2, usuario.getNome());
            comando.setString(3, usuario.getTelefone());
            comando.setString(4, usuario.getEmail());
            comando.setString(5, usuario.getSenhaHash());
            comando.setString(6, usuario.getEndereco());
            comando.setString(7, usuario.getCidade());
            comando.setString(8, usuario.getRegiao());
            comando.setString(9, usuario.getNomePropriedade());
            comando.setString(10, usuario.getTipo());
            comando.setInt(11, usuario.getSituacaoId());
            comando.executeUpdate();

            try (ResultSet resultado = comando.getGeneratedKeys()) {
                if (resultado.next()) {
                    usuario.setId(resultado.getInt(1));
                }
            }
        }
    }

/*Da um select em todas as colunas e ordena por nome do usuario*/
    public List<Usuario> listarTodos() throws SQLException {
        String sql = "SELECT usu_id, usu_cpfcnpj, usu_nome, usu_telefone, usu_email, "
                   + "usu_senha_hash, usu_endereco, usu_cidade, usu_regiao, usu_nome_propriedade, "
                   + "usu_tipo, situacao_id, data_criacao, data_atualizacao "
                   + "FROM usuario ORDER BY usu_nome";

        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                usuarios.add(montarUsuario(resultado));
            }
        }

        return usuarios;
    }

/*Insere um novo usuário no banco*/
    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT usu_id, usu_cpfcnpj, usu_nome, usu_telefone, usu_email, "
                   + "usu_senha_hash, usu_endereco, usu_cidade, usu_regiao, usu_nome_propriedade, "
                   + "usu_tipo, situacao_id, data_criacao, data_atualizacao "
                   + "FROM usuario WHERE usu_id = ?";

        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, id);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return montarUsuario(resultado);
                }
            }
        }

        return null;
    }

/*Comando apenas para admin executar*/ 
    public void atualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET usu_cpfcnpj = ?, usu_nome = ?, usu_telefone = ?, "
                   + "usu_email = ?, usu_senha_hash = ?, usu_endereco = ?, usu_cidade = ?, "
                   + "usu_regiao = ?, usu_nome_propriedade = ?, usu_tipo = ?, situacao_id = ? "
                   + "WHERE usu_id = ?";

        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, usuario.getCpfCnpj());
            comando.setString(2, usuario.getNome());
            comando.setString(3, usuario.getTelefone());
            comando.setString(4, usuario.getEmail());
            comando.setString(5, usuario.getSenhaHash());
            comando.setString(6, usuario.getEndereco());
            comando.setString(7, usuario.getCidade());
            comando.setString(8, usuario.getRegiao());
            comando.setString(9, usuario.getNomePropriedade());
            comando.setString(10, usuario.getTipo());
            comando.setInt(11, usuario.getSituacaoId());
            comando.setInt(12, usuario.getId());
            comando.executeUpdate();
        }
    }
/*Comando apenas para admin executar*/ 
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE usu_id = ?";

        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, id);
            comando.executeUpdate();
        }
    }

    private Usuario montarUsuario(ResultSet resultado) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(resultado.getInt("usu_id"));
        usuario.setCpfCnpj(resultado.getString("usu_cpfcnpj"));
        usuario.setNome(resultado.getString("usu_nome"));
        usuario.setTelefone(resultado.getString("usu_telefone"));
        usuario.setEmail(resultado.getString("usu_email"));
        usuario.setSenhaHash(resultado.getString("usu_senha_hash"));
        usuario.setEndereco(resultado.getString("usu_endereco"));
        usuario.setCidade(resultado.getString("usu_cidade"));
        usuario.setRegiao(resultado.getString("usu_regiao"));
        usuario.setNomePropriedade(resultado.getString("usu_nome_propriedade"));
        usuario.setTipo(resultado.getString("usu_tipo"));
        usuario.setSituacaoId(resultado.getInt("situacao_id"));
        usuario.setDataCriacao(resultado.getTimestamp("data_criacao").toLocalDateTime());
        usuario.setDataAtualizacao(resultado.getTimestamp("data_atualizacao").toLocalDateTime());
        return usuario;
    }
}