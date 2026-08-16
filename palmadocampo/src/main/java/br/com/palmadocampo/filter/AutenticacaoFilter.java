package br.com.palmadocampo.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// Protege as URLs listadas: só deixa passar quem está logado.
@WebFilter(urlPatterns = {"/cadastro-produto", "/meus-anuncios"})
public class AutenticacaoFilter implements Filter {

    @Override
    public void doFilter(ServletRequest requisicao, ServletResponse resposta, FilterChain chain)
            throws IOException, ServletException {

        // Converte pros tipos HTTP (pra acessar sessão e redirect)
        HttpServletRequest requisicaoHttp = (HttpServletRequest) requisicao;
        HttpServletResponse respostaHttp = (HttpServletResponse) resposta;

        // Pega a sessão SEM criar uma nova (o "false" é importante)
        HttpSession sessao = requisicaoHttp.getSession(false);

        // Verifica se há um usuário logado na sessão
        boolean estaLogado = (sessao != null && sessao.getAttribute("usuarioLogado") != null);

        if (estaLogado) {
            // Está logado: deixa a requisição seguir pro seu destino
            chain.doFilter(requisicao, resposta);
        } else {
            // Não está logado: manda pro login
            respostaHttp.sendRedirect(requisicaoHttp.getContextPath() + "/login");
        }
    }
}