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
            // Lê o estado selecionado (ex.: /buscar?estado=Rondônia)
            String estado = requisicao.getParameter("estado");

            boolean temTermo = termo != null && !termo.isBlank();
            boolean temEstado = estado != null && !estado.isBlank();

            // Se não veio nem texto nem estado, manda pra vitrine normal
            if (!temTermo && !temEstado) {
                resposta.sendRedirect(requisicao.getContextPath() + "/vitrine");
                return;
            }

            ProdutoDAO produtoDAO = new ProdutoDAO();
            List<ProdutoVitrine> produtos = produtoDAO.pesquisarComFiltros(
                    temTermo ? termo.trim() : null,
                    temEstado ? estado.trim() : null
            );

            // Manda os dados pro JSP
            requisicao.setAttribute("produtos", produtos);
            requisicao.setAttribute("termoBuscado", temTermo ? termo.trim() : "");
            requisicao.setAttribute("estadoBuscado", temEstado ? estado.trim() : "");

            // Reusa a MESMA vitrine.jsp pra mostrar o resultado
            requisicao.getRequestDispatcher("/WEB-INF/views/vitrine/vitrine.jsp")
                      .forward(requisicao, resposta);

        } catch (SQLException erro) {
            throw new ServletException("Erro ao pesquisar produtos", erro);
        }
    }
}