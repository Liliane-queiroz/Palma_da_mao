package br.com.palmadocampo.controller;

import java.io.IOException;
import java.sql.SQLException;
import br.com.palmadocampo.dao.ProdutoDAO;
import br.com.palmadocampo.model.ProdutoDetalhe;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@WebServlet("/detalhes")
public class DetalheProdutoServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws ServletException, IOException {
		try {
			// Lê o id que veio na URL (ex.: /detalhes?id=5)
			String idTexto = requisicao.getParameter("id");

			// Se não veio id, ou veio vazio, volta pra vitrine
			if (idTexto == null || idTexto.isBlank()) {
				resposta.sendRedirect(requisicao.getContextPath() + "/vitrine");
				return;
			}

			int id = Integer.parseInt(idTexto);

			ProdutoDAO produtoDAO = new ProdutoDAO();
			ProdutoDetalhe produto = produtoDAO.buscarDetalhePorId(id);

			// Se não achou produto com esse id, manda pro tratamento de "não encontrado"
			if (produto == null) {
				resposta.sendError(HttpServletResponse.SC_NOT_FOUND, "Produto não encontrado");
				return;
			}

			// Formata a data de cadastro do produtor
			DateTimeFormatter formatador = DateTimeFormatter.ofPattern("MMMM 'de' yyyy", new Locale("pt", "BR"));
			String dataCadastroFormatada = produto.getProdutorDataCadastro().format(formatador);
			
			// Formata a data prevista de entrega, se o produtor preencheu
			String dataEntregaFormatada = null;
			if (produto.getDataPrevistaEntrega() != null) {
				DateTimeFormatter formatadorEntrega = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy",
						new Locale("pt", "BR"));
				dataEntregaFormatada = produto.getDataPrevistaEntrega().format(formatadorEntrega);
			}
			requisicao.setAttribute("dataEntregaFormatada", dataEntregaFormatada);

			requisicao.setAttribute("produto", produto);
			requisicao.setAttribute("dataCadastroFormatada", dataCadastroFormatada);
			requisicao.getRequestDispatcher("/WEB-INF/views/produto/detalhes.jsp").forward(requisicao, resposta);

		} catch (NumberFormatException erro) {
			// O id veio, mas não era um número (ex.: /detalhes?id=abc)
			resposta.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id inválido");
		} catch (SQLException erro) {
			throw new ServletException("Erro ao carregar detalhes do produto", erro);
		}
	}
}