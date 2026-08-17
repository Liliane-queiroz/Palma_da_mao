package br.com.palmadocampo.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import br.com.palmadocampo.dao.ProdutoDAO;
import br.com.palmadocampo.model.ProdutoVitrine;
import br.com.palmadocampo.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/meus-anuncios")
public class MeusAnunciosServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {

        // O AutenticacaoFilter já garante que só chega aqui quem está logado,
        // mas pegamos o produtor com segurança mesmo assim
        HttpSession sessao = requisicao.getSession(false);
        Usuario produtorLogado = (sessao != null)
                ? (Usuario) sessao.getAttribute("usuarioLogado")
                : null;

        if (produtorLogado == null) {
            resposta.sendRedirect(requisicao.getContextPath() + "/login");
            return;
        }

        try {
            ProdutoDAO produtoDAO = new ProdutoDAO();
            List<ProdutoVitrine> meusProdutos = produtoDAO.listarPorUsuario(produtorLogado.getId());

            requisicao.setAttribute("produtos", meusProdutos);
            requisicao.getRequestDispatcher("/WEB-INF/views/produto/meus-anuncios.jsp")
                    .forward(requisicao, resposta);

        } catch (SQLException erro) {
            erro.printStackTrace();
            resposta.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao carregar seus anúncios");
        }
    }
}