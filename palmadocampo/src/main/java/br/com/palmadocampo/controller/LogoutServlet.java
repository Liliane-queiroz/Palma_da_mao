package br.com.palmadocampo.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {

        // Pega a sessão SEM criar uma nova.
        // getSession(false) retorna null se não existir sessão — o mesmo truque do AutenticacaoFilter.
        HttpSession sessao = requisicao.getSession(false);

        // Se há sessão ativa, invalida: apaga o "usuarioLogado" e todo o resto de uma vez.
        if (sessao != null) {
            sessao.invalidate();
        }

        // Devolve o usuário pra vitrine, já como visitante.
        resposta.sendRedirect(requisicao.getContextPath() + "/vitrine");
    }
}