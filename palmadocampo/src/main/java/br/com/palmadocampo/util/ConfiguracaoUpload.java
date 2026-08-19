package br.com.palmadocampo.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 * Descobre em qual pasta as fotos dos produtos devem ser salvas.
 */
public class ConfiguracaoUpload {

	private static final String VARIAVEL_AMBIENTE = "PALMA_UPLOADS_DIR";
	private static final String PASTA_PADRAO = "palma-do-campo-uploads";

	public static Path obterDiretorioUploads() {
		String caminhoConfigurado = System.getenv(VARIAVEL_AMBIENTE);

		Path diretorio;
		if (caminhoConfigurado != null && !caminhoConfigurado.isBlank()) {
			// Ao ser definido um caminho específico (ex.: na VM)
			
			diretorio = Paths.get(caminhoConfigurado);
		} else {

			// Vira C:\Users\SeuNome\... no Windows e /home/usuario/... no Linux.
			String pastaUsuario = System.getProperty("user.home");
			diretorio = Paths.get(pastaUsuario, PASTA_PADRAO);
		}

		// Cria a pasta na primeira vez, se ainda não existir
		try {
			Files.createDirectories(diretorio);
		} catch (IOException erro) {
			throw new RuntimeException("Não foi possível criar a pasta de uploads: " + diretorio, erro);
		}

		return diretorio;
	}
}