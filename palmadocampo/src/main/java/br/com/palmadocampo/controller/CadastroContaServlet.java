package br.com.palmadocampo.controller;

import java.io.IOException;
import java.sql.SQLException;

import br.com.palmadocampo.dao.UsuarioDAO;
import br.com.palmadocampo.model.Usuario;
import br.com.palmadocampo.util.ValidadorCPF;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cadastro-conta")
public class CadastroContaServlet extends HttpServlet {

	// Exibe o formulário de cadastro
	@Override
	protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws ServletException, IOException {
		requisicao.getRequestDispatcher("/WEB-INF/views/autenticacao/cadastro-conta.jsp").forward(requisicao, resposta);
	}

	// Recebe os dados enviados pelo formulário
	@Override
	protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws ServletException, IOException {

		String nome = requisicao.getParameter("nome");
		String cpf = requisicao.getParameter("cpf");
		String email = requisicao.getParameter("email");
		String senha = requisicao.getParameter("senha");
		String telefone = requisicao.getParameter("telefone");
		String cidade = requisicao.getParameter("cidade");
		String regiao = requisicao.getParameter("regiao");
		String nomePropriedade = requisicao.getParameter("nomePropriedade");

		// Valida o CPF antes de qualquer coisa
		if (!ValidadorCPF.validar(cpf)) {
			requisicao.setAttribute("erro", "CPF inválido. Verifique os números digitados.");
			requisicao.getRequestDispatcher("/WEB-INF/views/autenticacao/cadastro-conta.jsp").forward(requisicao,
					resposta);
			return;
		}

		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();

			// Verifica se o CPF já está cadastrado
			if (usuarioDAO.cpfJaExiste(cpf)) {
				requisicao.setAttribute("erro", "Este CPF já está cadastrado.");
				requisicao.getRequestDispatcher("/WEB-INF/views/autenticacao/cadastro-conta.jsp").forward(requisicao,
						resposta);
				return;
			}

			// Verifica se o email já está cadastrado
			if (usuarioDAO.emailJaExiste(email)) {
				requisicao.setAttribute("erro", "Este e-mail já está cadastrado.");
				requisicao.getRequestDispatcher("/WEB-INF/views/autenticacao/cadastro-conta.jsp").forward(requisicao,
						resposta);
				return;
			}

			// Monta o objeto usuario (tipo PRODUTOR, situação 1 = ativo)
			Usuario usuario = new Usuario();
			usuario.setNome(nome);
			usuario.setCpfCnpj(cpf);
			usuario.setEmail(email);
			usuario.setTelefone(telefone);
			usuario.setCidade(cidade);
			usuario.setRegiao(regiao);
			usuario.setNomePropriedade(nomePropriedade);
			usuario.setTipo("PRODUTOR");
			usuario.setSituacaoId(1);

			// Cadastra (a senha vira hash dentro do DAO)
			usuarioDAO.cadastrar(usuario, senha);

			// Deu certo: manda pra tela de login
			resposta.sendRedirect(requisicao.getContextPath() + "/login?cadastro=sucesso");

		} catch (SQLException erro) {
			throw new ServletException("Erro ao cadastrar produtor", erro);
		}
	}
}