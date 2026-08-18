package br.com.palmadocampo.controller;

import java.io.IOException;
import java.sql.SQLException;
import br.com.palmadocampo.dao.ProdutoDAO;
import br.com.palmadocampo.dao.UsuarioDAO;
import br.com.palmadocampo.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@WebServlet("/perfil")
public class ProdutorPerfilServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws ServletException, IOException {

		try {
			// Pega o ID do produtor da URL: /perfil?id=X
			String produtorIdStr = requisicao.getParameter("id");

			if (produtorIdStr == null || produtorIdStr.isEmpty()) {
				resposta.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do produtor não informado");
				return;
			}

			int produtorId = Integer.parseInt(produtorIdStr);

			// Busca os dados do produtor
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			Usuario produtor = usuarioDAO.buscarPorId(produtorId);

			if (produtor == null) {
				resposta.sendError(HttpServletResponse.SC_NOT_FOUND, "Produtor não encontrado");
				return;
			}

			// Formata a data de cadastro
			DateTimeFormatter formatador = DateTimeFormatter.ofPattern("MMMM 'de' yyyy", new Locale("pt", "BR"));
			String dataFormatada = produtor.getDataCriacao().format(formatador);

			// Conta quantos produtos o produtor tem publicados
			ProdutoDAO produtoDAO = new ProdutoDAO();
			int totalProdutos = produtoDAO.contarPorUsuario(produtorId);

			// Busca a lista de produtos do produtor
			java.util.List<br.com.palmadocampo.model.ProdutoVitrine> produtos = produtoDAO.listarPorUsuario(produtorId);

			// Passa os dados pra JSP
			requisicao.setAttribute("produtor", produtor);
			requisicao.setAttribute("totalProdutos", totalProdutos);
			requisicao.setAttribute("dataCadastroFormatada", dataFormatada);
			requisicao.setAttribute("produtos", produtos);

			requisicao.getRequestDispatcher("/WEB-INF/views/produto/perfil-produtor.jsp").forward(requisicao, resposta);

		} catch (NumberFormatException erro) {
			resposta.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do produtor inválido");
		} catch (SQLException erro) {
			erro.printStackTrace();
			resposta.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao carregar perfil do produtor");
		}
	}
}