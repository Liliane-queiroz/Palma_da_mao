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
            // Lê a cidade selecionada (ex.: /buscar?cidade=Cacoal)
            String cidade = requisicao.getParameter("cidade");

            boolean temTermo = termo != null && !termo.isBlank();
            boolean temCidade = cidade != null && !cidade.isBlank();

            // Se não veio nem texto nem cidade, manda pra vitrine normal
            if (!temTermo && !temCidade) {
                resposta.sendRedirect(requisicao.getContextPath() + "/vitrine");
                return;
            }

            ProdutoDAO produtoDAO = new ProdutoDAO();
            List<ProdutoVitrine> produtos = produtoDAO.pesquisarComFiltros(
                    temTermo ? termo.trim() : null,
                    temCidade ? cidade.trim() : null
            );

            // Manda os dados pro JSP
            requisicao.setAttribute("produtos", produtos);
            requisicao.setAttribute("termoBuscado", temTermo ? termo.trim() : "");
            requisicao.setAttribute("cidadeBuscada", temCidade ? cidade.trim() : "");

            // Reusa a MESMA vitrine.jsp pra mostrar o resultado
            requisicao.getRequestDispatcher("/WEB-INF/views/vitrine/vitrine.jsp")
                      .forward(requisicao, resposta);

        } catch (SQLException erro) {
            throw new ServletException("Erro ao pesquisar produtos", erro);
        }
    }
}