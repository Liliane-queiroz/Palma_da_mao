package br.com.palmadocampo.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import br.com.palmadocampo.dao.UsuarioDAO;
import br.com.palmadocampo.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // Exibe o formulário de login
    @Override
    protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {
        requisicao.getRequestDispatcher("/WEB-INF/views/autenticacao/login.jsp")
                  .forward(requisicao, resposta);
    }

    // Recebe email e senha e tenta autenticar
    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {

        String email = requisicao.getParameter("email");
        String senha = requisicao.getParameter("senha");

        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            // Busca o usuário pelo email
            Usuario usuario = usuarioDAO.buscarPorEmail(email);

            // Confere: o usuário existe E a senha bate com o hash?
            if (usuario != null && BCrypt.checkpw(senha, usuario.getSenhaHash())) {

                // Login válido: cria a sessão e guarda o usuário nela
                HttpSession sessao = requisicao.getSession();
                sessao.setAttribute("usuarioLogado", usuario);

                // Redireciona pra vitrine (por enquanto)
                resposta.sendRedirect(requisicao.getContextPath() + "/vitrine");

            } else {
                // Email não existe OU senha errada — mesma mensagem genérica (segurança)
                requisicao.setAttribute("erro", "E-mail ou senha incorretos.");
                requisicao.getRequestDispatcher("/WEB-INF/views/autenticacao/login.jsp")
                          .forward(requisicao, resposta);
            }

        } catch (SQLException erro) {
            throw new ServletException("Erro ao fazer login", erro);
        }
    }
}