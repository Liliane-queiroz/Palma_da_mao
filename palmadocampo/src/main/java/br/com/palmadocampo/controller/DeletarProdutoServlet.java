package br.com.palmadocampo.controller;

import java.io.IOException;
import java.sql.SQLException;

import br.com.palmadocampo.dao.ProdutoDAO;
import br.com.palmadocampo.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/deletar-produto")
public class DeletarProdutoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws ServletException, IOException {

		resposta.setContentType("application/json;charset=UTF-8");

		// Pega o produtor logado
		HttpSession sessao = requisicao.getSession(false);
		Usuario produtorLogado = (sessao != null)
				? (Usuario) sessao.getAttribute("usuarioLogado")
				: null;

		if (produtorLogado == null) {
			resposta.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			resposta.getWriter().write("{\"sucesso\": false, \"erro\": \"Não autenticado\"}");
			return;
		}

		try {
			// Pega o ID do produto a deletar
			String produtoIdStr = requisicao.getParameter("produtoId");
			if (produtoIdStr == null || produtoIdStr.isEmpty()) {
				resposta.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				resposta.getWriter().write("{\"sucesso\": false, \"erro\": \"ID do produto não informado\"}");
				return;
			}

			int produtoId = Integer.parseInt(produtoIdStr);

			// Deleta (valida que pertence ao usuário logado)
			ProdutoDAO produtoDAO = new ProdutoDAO();
			boolean deletouComSucesso = produtoDAO.deletarComEstoque(produtoId, produtorLogado.getId());

			if (deletouComSucesso) {
				resposta.setStatus(HttpServletResponse.SC_OK);
				resposta.getWriter().write("{\"sucesso\": true, \"mensagem\": \"Anúncio deletado com sucesso\"}");
			} else {
				resposta.setStatus(HttpServletResponse.SC_FORBIDDEN);
				resposta.getWriter().write("{\"sucesso\": false, \"erro\": \"Você não tem permissão para deletar este anúncio\"}");
			}

		} catch (NumberFormatException erro) {
			resposta.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resposta.getWriter().write("{\"sucesso\": false, \"erro\": \"ID do produto inválido\"}");
		} catch (SQLException erro) {
			erro.printStackTrace();
			resposta.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resposta.getWriter().write("{\"sucesso\": false, \"erro\": \"Erro ao deletar anúncio\"}");
		}
	}
}