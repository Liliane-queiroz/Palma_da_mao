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

@WebServlet("/categoria")
public class CategoriaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {
        try {
            // Lê a categoria da URL (ex.: /categoria?nome=Frutas)
            String categoria = requisicao.getParameter("nome");

            // Se veio vazia, volta pra vitrine normal
            if (categoria == null || categoria.isBlank()) {
                resposta.sendRedirect(requisicao.getContextPath() + "/vitrine");
                return;
            }

            ProdutoDAO produtoDAO = new ProdutoDAO();
            List<ProdutoVitrine> produtos = produtoDAO.listarPorCategoria(categoria.trim());

            requisicao.setAttribute("produtos", produtos);
            requisicao.setAttribute("categoriaSelecionada", categoria.trim());

            // Reusa a mesma vitrine
            requisicao.getRequestDispatcher("/WEB-INF/views/vitrine/vitrine.jsp")
                      .forward(requisicao, resposta);

        } catch (SQLException erro) {
            throw new ServletException("Erro ao filtrar por categoria", erro);
        }
    }
}