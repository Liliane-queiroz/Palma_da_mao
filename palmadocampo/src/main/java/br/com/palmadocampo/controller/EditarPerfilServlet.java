package br.com.palmadocampo.controller;

import java.io.IOException;
import java.sql.SQLException;
import br.com.palmadocampo.dao.UsuarioDAO;
import br.com.palmadocampo.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.io.InputStream;
import java.util.UUID;
import br.com.palmadocampo.util.ConfiguracaoUpload;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;

@WebServlet("/editar-perfil")
@MultipartConfig(maxFileSize = 5242880, maxRequestSize = 10485760)
public class EditarPerfilServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws ServletException, IOException {

		// Pega o produtor logado
		HttpSession sessao = requisicao.getSession(false);
		Usuario produtorLogado = (sessao != null)
				? (Usuario) sessao.getAttribute("usuarioLogado")
				: null;

		if (produtorLogado == null) {
			resposta.sendRedirect(requisicao.getContextPath() + "/login");
			return;
		}

		// Manda os dados atuais pra JSP preencher o formulário
		requisicao.setAttribute("produtor", produtorLogado);
		requisicao.getRequestDispatcher("/WEB-INF/views/produto/editar-perfil.jsp")
				.forward(requisicao, resposta);
	}

	@Override
	protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws ServletException, IOException {

		// Pega o produtor logado
		HttpSession sessao = requisicao.getSession(false);
		Usuario produtorLogado = (sessao != null)
				? (Usuario) sessao.getAttribute("usuarioLogado")
				: null;

		if (produtorLogado == null) {
			resposta.sendRedirect(requisicao.getContextPath() + "/login");
			return;
		}

		try {
			// Pega os dados do formulário
			String telefone = requisicao.getParameter("telefone");
			String nomePropriedade = requisicao.getParameter("nomePropriedade");
			String apresentacao = requisicao.getParameter("apresentacao");

			UsuarioDAO usuarioDAO = new UsuarioDAO();

			// Atualiza os dados de texto no banco
			usuarioDAO.atualizarPerfil(produtorLogado.getId(), telefone, nomePropriedade, apresentacao);

			// Trata a foto de perfil, se o produtor enviou uma
			Part parteFoto = requisicao.getPart("fotoPerfil");
			if (parteFoto != null && parteFoto.getSubmittedFileName() != null
					&& !parteFoto.getSubmittedFileName().isEmpty()) {

				String nomeOriginal = parteFoto.getSubmittedFileName();
				String extensao = "";
				int posicaoPonto = nomeOriginal.lastIndexOf(".");
				if (posicaoPonto >= 0) {
					extensao = nomeOriginal.substring(posicaoPonto);
				}
				String nomeArquivo = System.currentTimeMillis() + "-"
						+ UUID.randomUUID().toString().substring(0, 8) + extensao;

				// Salva na mesma pasta externa das fotos de produto
				Path diretorioUploads = ConfiguracaoUpload.obterDiretorioUploads();
				Path destino = diretorioUploads.resolve(nomeArquivo);
				try (InputStream entrada = parteFoto.getInputStream()) {
					Files.copy(entrada, destino, StandardCopyOption.REPLACE_EXISTING);
				}

				// Grava só o nome no banco e atualiza a sessão
				usuarioDAO.atualizarFotoPerfil(produtorLogado.getId(), nomeArquivo);
				produtorLogado.setFotoUrl(nomeArquivo);
			}

			// Atualiza os dados na sessão também (pra refletir sem precisar deslogar)
			produtorLogado.setTelefone(telefone);
			produtorLogado.setNomePropriedade(nomePropriedade);
			produtorLogado.setApresentacao(apresentacao);
			sessao.setAttribute("usuarioLogado", produtorLogado);

			// Redireciona pro perfil
			resposta.sendRedirect(requisicao.getContextPath() + "/perfil?id=" + produtorLogado.getId());

		} catch (SQLException erro) {
			erro.printStackTrace();
			resposta.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Erro ao atualizar perfil");
		}
	}
}