package br.com.palmadocampo.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import br.com.palmadocampo.dao.ProdutoDAO;
import br.com.palmadocampo.model.ProdutoVitrine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/buscar")
public class BuscaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {
        try {
            // Lê o texto que o usuário digitou (ex.: /buscar?termo=batata)
            String termo = requisicao.getParameter("termo");

            // Se veio vazio ou nulo, manda pra vitrine normal (mostra tudo)
            if (termo == null || termo.isBlank()) {
                resposta.sendRedirect(requisicao.getContextPath() + "/vitrine");
                return;
            }

            ProdutoDAO produtoDAO = new ProdutoDAO();
            List<ProdutoVitrine> produtos = produtoDAO.pesquisarProdutos(termo.trim());

            // Manda tanto a lista quanto o termo buscado pro JSP
            requisicao.setAttribute("produtos", produtos);
            requisicao.setAttribute("termoBuscado", termo.trim());

            // Reusa a MESMA vitrine.jsp pra mostrar o resultado
            requisicao.getRequestDispatcher("/WEB-INF/views/vitrine/vitrine.jsp")
                      .forward(requisicao, resposta);

        } catch (SQLException erro) {
            throw new ServletException("Erro ao pesquisar produtos", erro);
        }
    }
}