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

@WebServlet("/vitrine")
public class VitrineServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {

        try {
            ProdutoDAO produtoDAO = new ProdutoDAO();
            List<ProdutoVitrine> produtos = produtoDAO.listarTodosComCategoria();

            requisicao.setAttribute("produtos", produtos);
            requisicao.getRequestDispatcher("/WEB-INF/views/vitrine/vitrine.jsp").forward(requisicao, resposta);

        } catch (SQLException erro) {
            throw new ServletException("Erro ao carregar produtos da vitrine", erro);
        }
    }
}