package br.com.palmadocampo.controller;

import java.io.IOException;
import java.sql.SQLException;

import br.com.palmadocampo.dao.UsuarioDAO;
import br.com.palmadocampo.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/editar-perfil")
public class EditarPerfilServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws ServletException, IOException {

		// Pega o produtor logado
		HttpSession sessao = requisicao.getSession(false);
		Usuario produtorLogado = (sessao != null)
				? (Usuario) sessao.getAttribute("usuarioLogado")
				: null;

		if (produtorLogado == null) {
			resposta.sendRedirect(requisicao.getContextPath() + "/login");
			return;
		}

		// Manda os dados atuais pra JSP preencher o formulário
		requisicao.setAttribute("produtor", produtorLogado);
		requisicao.getRequestDispatcher("/WEB-INF/views/produto/editar-perfil.jsp")
				.forward(requisicao, resposta);
	}

	@Override
	protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws ServletException, IOException {

		// Pega o produtor logado
		HttpSession sessao = requisicao.getSession(false);
		Usuario produtorLogado = (sessao != null)
				? (Usuario) sessao.getAttribute("usuarioLogado")
				: null;

		if (produtorLogado == null) {
			resposta.sendRedirect(requisicao.getContextPath() + "/login");
			return;
		}

		try {
			// Pega os dados do formulário
			String telefone = requisicao.getParameter("telefone");
			String nomePropriedade = requisicao.getParameter("nomePropriedade");
			String apresentacao = requisicao.getParameter("apresentacao");

			// Atualiza no banco
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioDAO.atualizarPerfil(produtorLogado.getId(), telefone, nomePropriedade, apresentacao);

			// Atualiza os dados na sessão também (pra refletir sem precisar deslogar)
			produtorLogado.setTelefone(telefone);
			produtorLogado.setNomePropriedade(nomePropriedade);
			produtorLogado.setApresentacao(apresentacao);
			sessao.setAttribute("usuarioLogado", produtorLogado);

			// Redireciona pro perfil
			resposta.sendRedirect(requisicao.getContextPath() + "/perfil?id=" + produtorLogado.getId());

		} catch (SQLException erro) {
			erro.printStackTrace();
			resposta.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Erro ao atualizar perfil");
		}
	}
}