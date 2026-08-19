package br.com.palmadocampo.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import br.com.palmadocampo.util.ConfiguracaoUpload;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * Serve as fotos dos produtos que ficam guardadas FORA do Tomcat.
 */
@WebServlet("/imagem/*")
public class ImagemServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws ServletException, IOException {

		// O que vem depois de /imagem/ (ex.: /imagem/172.jpg -> "/172.jpg")
		String caminhoInformado = requisicao.getPathInfo();

		if (caminhoInformado == null || caminhoInformado.equals("/") || caminhoInformado.isBlank()) {
			resposta.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// Tira a barra inicial pra sobrar só o nome do arquivo
		String nomeArquivo = caminhoInformado.substring(1);

		Path diretorio = ConfiguracaoUpload.obterDiretorioUploads();

		// Resolve o arquivo pedido DENTRO da pasta e normaliza o caminho.
		// Protege contra alguém tentar "subir" pastas (ex.: ../../algo-secreto).
		Path arquivo = diretorio.resolve(nomeArquivo).normalize();

		// Se o caminho final saiu de dentro da pasta de uploads, barra na hora
		if (!arquivo.startsWith(diretorio)) {
			resposta.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		// Se não existe ou é uma pasta, responde "não encontrado"
		if (!Files.exists(arquivo) || Files.isDirectory(arquivo)) {
			resposta.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// Descobre o tipo (image/jpeg, image/png...) pra o navegador entender
		String tipoConteudo = getServletContext().getMimeType(arquivo.toString());
		if (tipoConteudo == null) {
			tipoConteudo = Files.probeContentType(arquivo);
		}
		if (tipoConteudo == null) {
			tipoConteudo = "application/octet-stream";
		}

		resposta.setContentType(tipoConteudo);
		resposta.setContentLengthLong(Files.size(arquivo));

		// Copia os bytes do arquivo direto pra resposta
		try (OutputStream saida = resposta.getOutputStream()) {
			Files.copy(arquivo, saida);
		}
	}
}