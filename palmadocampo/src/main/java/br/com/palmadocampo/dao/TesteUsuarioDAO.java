package br.com.palmadocampo.dao;

import java.util.List;

import br.com.palmadocampo.model.Usuario;

public class TesteUsuarioDAO {

    public static void main(String[] args) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        try {
            Usuario usuarioNovo = new Usuario(
                    "Maria Joaquina",
                    "12345678900",
                    "maria@teste.com",
                    "senha_temporaria",
                    "5511999999999",
                    "PRODUTOR",
                    1
            );
            usuarioNovo.setCidade("Pimenta Bueno");
            usuarioNovo.setRegiao("Rondônia");
            usuarioNovo.setNomePropriedade("Sítio Some Cirilo");

            usuarioDAO.inserir(usuarioNovo);
            System.out.println("Usuário inserido com ID: " + usuarioNovo.getId());

            System.out.println("\nUsuários cadastrados:");
            List<Usuario> usuarios = usuarioDAO.listarTodos();
            for (Usuario usuario : usuarios) {
                System.out.println("  ID " + usuario.getId()
                                 + " | " + usuario.getNome()
                                 + " | " + usuario.getEmail()
                                 + " | " + usuario.getTipo());
            }

            Usuario usuarioBuscado = usuarioDAO.buscarPorId(usuarioNovo.getId());
            System.out.println("\nBuscado pelo ID " + usuarioNovo.getId()
                             + ": " + usuarioBuscado.getNome()
                             + " | propriedade: " + usuarioBuscado.getNomePropriedade());

            usuarioBuscado.setTelefone("5511888888888");
            usuarioDAO.atualizar(usuarioBuscado);
            System.out.println("\nTelefone atualizado.");

            Usuario usuarioConfirmado = usuarioDAO.buscarPorId(usuarioNovo.getId());
            System.out.println("Novo telefone: " + usuarioConfirmado.getTelefone());

        } catch (Exception excecao) {
            System.out.println("Erro no teste:");
            excecao.printStackTrace();
        }
    }
}