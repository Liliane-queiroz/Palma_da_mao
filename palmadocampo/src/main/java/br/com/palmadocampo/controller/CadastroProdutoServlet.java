package br.com.palmadocampo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import br.com.palmadocampo.dao.CategoriaDAO;
import br.com.palmadocampo.dao.ConexaoFactory;
import br.com.palmadocampo.dao.EstoqueDAO;
import br.com.palmadocampo.dao.ProdutoDAO;
import br.com.palmadocampo.model.Categoria;
import br.com.palmadocampo.model.Estoque;
import br.com.palmadocampo.model.Produto;
import br.com.palmadocampo.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import br.com.palmadocampo.util.ConfiguracaoUpload;

@WebServlet("/cadastro-produto")
@MultipartConfig(maxFileSize = 5242880, maxRequestSize = 52428800)
public class CadastroProdutoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	// private static final String PASTA_UPLOAD =
	// "resources/images/uploads/produtos/";//

	@Override
	protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws ServletException, IOException {

		// Pega o produtor logado
		HttpSession sessao = requisicao.getSession(false);
		Usuario produtorLogado = (sessao != null) ? (Usuario) sessao.getAttribute("usuarioLogado") : null;

		if (produtorLogado == null) {
			resposta.sendRedirect(requisicao.getContextPath() + "/login");
			return;
		}

		try {
			// Verifica se é edição (tem ID na URL) ou criação (sem ID)
			String produtoIdStr = requisicao.getParameter("id");
			Produto produtoParaEditar = null;
			boolean eEdicao = false;

			if (produtoIdStr != null && !produtoIdStr.isEmpty()) {
				try {
					int produtoId = Integer.parseInt(produtoIdStr);
					ProdutoDAO produtoDAO = new ProdutoDAO();
					produtoParaEditar = produtoDAO.buscarPorId(produtoId);

					// Valida que o produto pertence ao usuário logado
					if (produtoParaEditar != null) {
						// Busca o estoque pra confirmar que pertence ao usuário
						EstoqueDAO estoqueDAO = new EstoqueDAO();
						Estoque estoque = estoqueDAO.buscarPorProdutoId(produtoId);

						if (estoque != null && estoque.getUsuarioId() == produtorLogado.getId()) {
							eEdicao = true;
							requisicao.setAttribute("produtoParaEditar", produtoParaEditar);
							requisicao.setAttribute("estoque", estoque);
							requisicao.setAttribute("eEdicao", true);
						} else {
							// Produto não pertence ao usuário — redireciona
							resposta.sendRedirect(requisicao.getContextPath() + "/meus-anuncios");
							return;
						}
					}
				} catch (NumberFormatException erro) {
					// ID inválido — continua como criação
				}
			}

			// Se não é edição, apenas manda pra criar novo
			if (!eEdicao) {
				requisicao.setAttribute("eEdicao", false);
			}

			// Carrega as categorias
			CategoriaDAO categoriaDAO = new CategoriaDAO();
			List<Categoria> categorias = categoriaDAO.listarAtivas();
			requisicao.setAttribute("categorias", categorias);

			requisicao.getRequestDispatcher("/WEB-INF/views/produto/cadastro-produto.jsp").forward(requisicao,
					resposta);

		} catch (SQLException erro) {
			erro.printStackTrace();
			resposta.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao carregar dados do produto");
		}
	}

	@Override
	protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
			throws ServletException, IOException {

		// Pega o produtor logado — o AutenticacaoFilter já garante que existe sessão
		// aqui,
		// mas pegamos com segurança mesmo assim (defesa em profundidade)
		HttpSession sessao = requisicao.getSession(false);
		Usuario produtorLogado = (sessao != null) ? (Usuario) sessao.getAttribute("usuarioLogado") : null;

		if (produtorLogado == null) {
			resposta.sendRedirect(requisicao.getContextPath() + "/login");
			return;
		}

		try {
			// ===== ETAPA 1: Validar e processar upload de MÚLTIPLAS imagens =====
			Collection<Part> todasAsParts = requisicao.getParts();

			// Pasta de uploads FORA do Tomcat — o caminho é resolvido automaticamente
			// pra máquina atual (Windows local ou Linux na VM) e a pasta é criada
			// se ainda não existir.
			Path diretorioUploads = ConfiguracaoUpload.obterDiretorioUploads();

			List<String> nomesArquivos = new ArrayList<>();

			// Filtra só as partes que são arquivo (tem "filename")
			for (Part part : todasAsParts) {
				if (part.getSubmittedFileName() != null && !part.getSubmittedFileName().isEmpty()) {
					String nomeArquivo = gerarNomeUnicoArquivo(part.getSubmittedFileName());

					// Caminho absoluto do arquivo dentro da pasta externa
					Path destino = diretorioUploads.resolve(nomeArquivo);

					// Copia os bytes enviados pra esse caminho no disco
					try (InputStream entrada = part.getInputStream()) {
						Files.copy(entrada, destino, StandardCopyOption.REPLACE_EXISTING);
					}

					// No banco guardamos SÓ o nome do arquivo, nunca o caminho completo
					nomesArquivos.add(nomeArquivo);

					System.out.println("Arquivo salvo: " + destino);
				}
			}

			// Junta os nomes numa string separada por vírgula (pra múltiplas fotos)
			String fotosUrl = String.join(",", nomesArquivos);

			// ===== ETAPA 2: Validar e processar dados do formulário =====
			String nomeProduto = requisicao.getParameter("nome");
			String descricaoProduto = requisicao.getParameter("descricao");
			String precoStr = requisicao.getParameter("preco");
			String categoriaIdStr = requisicao.getParameter("categoria");
			String quantidade = requisicao.getParameter("quantidade");
			String unidade = requisicao.getParameter("unidade");

			// Validações básicas
			if (nomeProduto == null || nomeProduto.trim().isEmpty()) {
				throw new IllegalArgumentException("Nome do produto é obrigatório");
			}
			if (precoStr == null || precoStr.trim().isEmpty()) {
				throw new IllegalArgumentException("Preço é obrigatório");
			}
			if (categoriaIdStr == null || categoriaIdStr.trim().isEmpty()) {
				throw new IllegalArgumentException("Categoria é obrigatória");
			}

			java.math.BigDecimal precoProduto = new java.math.BigDecimal(precoStr.replace(",", "."));
			int categoriaId = Integer.parseInt(categoriaIdStr);
			int quantidadeInt = Integer.parseInt(quantidade != null ? quantidade : "0");

			System.out.println("Dados validados: nome=" + nomeProduto + ", preco=" + precoProduto);

			// ===== ETAPA 3: Inserir ou atualizar ESTOQUE =====

			Connection conexao = ConexaoFactory.getConexao();
			conexao.setAutoCommit(false);

			// Verifica se é edição (tem ID na URL)
			String produtoIdStr = requisicao.getParameter("id");
			boolean eEdicao = produtoIdStr != null && !produtoIdStr.isEmpty();
			int produtoId = 0;
			int produtoIdExistente = 0;

			if (eEdicao) {
				try {
					produtoIdExistente = Integer.parseInt(produtoIdStr);
				} catch (NumberFormatException erro) {
					eEdicao = false;
				}
			}

			if (eEdicao) {
				// ===== UPDATE: Editar produto e estoque existente =====
				String sqlUpdateProduto = "UPDATE produto SET prod_nome = ?, prod_descricao = ?, "
						+ "prod_preco_estimado = ?, categoria_id = ? WHERE prod_id = ?";

				try (PreparedStatement comandoProduto = conexao.prepareStatement(sqlUpdateProduto)) {
					comandoProduto.setString(1, nomeProduto);
					comandoProduto.setString(2, descricaoProduto);
					comandoProduto.setBigDecimal(3, precoProduto);
					comandoProduto.setInt(4, categoriaId);
					comandoProduto.setInt(5, produtoIdExistente);
					comandoProduto.executeUpdate();
				}

				// UPDATE no estoque (só quantidade e unidade)
				String sqlUpdateEstoque = "UPDATE estoque SET est_qtd = ?, est_unidade = ? WHERE produto_id = ?";

				try (PreparedStatement comandoEstoque = conexao.prepareStatement(sqlUpdateEstoque)) {
					comandoEstoque.setInt(1, quantidadeInt);
					comandoEstoque.setString(2, unidade);
					comandoEstoque.setInt(3, produtoIdExistente);
					comandoEstoque.executeUpdate();
				}

				System.out.println("UPDATE realizado com sucesso");
				requisicao.setAttribute("sucesso", "Anúncio atualizado com sucesso");

			} else {
				// ===== INSERT: Criar novo produto e estoque =====
				String sqlProduto = "INSERT INTO produto (prod_nome, prod_descricao, prod_preco_estimado, "
						+ "prod_foto_url, categoria_id, situacao_id, data_criacao) VALUES (?, ?, ?, ?, ?, 1, NOW())";

				try (PreparedStatement comandoProduto = conexao.prepareStatement(sqlProduto,
						Statement.RETURN_GENERATED_KEYS)) {
					comandoProduto.setString(1, nomeProduto);
					comandoProduto.setString(2, descricaoProduto);
					comandoProduto.setBigDecimal(3, precoProduto);
					comandoProduto.setString(4, fotosUrl);
					comandoProduto.setInt(5, categoriaId);
					comandoProduto.executeUpdate();

					try (ResultSet chaves = comandoProduto.getGeneratedKeys()) {
						if (chaves.next()) {
							produtoId = chaves.getInt(1);
						}
					}
				}

				String sqlEstoque = "INSERT INTO estoque (usuario_id, produto_id, est_qtd, est_unidade, situacao_id) "
						+ "VALUES (?, ?, ?, ?, 1)";

				try (PreparedStatement comandoEstoque = conexao.prepareStatement(sqlEstoque)) {
					comandoEstoque.setInt(1, produtorLogado.getId());
					comandoEstoque.setInt(2, produtoId);
					comandoEstoque.setInt(3, quantidadeInt);
					comandoEstoque.setString(4, unidade);
					comandoEstoque.executeUpdate();
				}

				System.out.println("INSERT realizado com sucesso");
				requisicao.setAttribute("sucesso", "Anúncio criado com sucesso");
			}

			// Se chegou aqui, tudo funcionou
			conexao.commit();
			System.out.println("COMMIT realizado com sucesso");

		} catch (SQLException erro) {
			System.out.println("ERRO na transação: " + erro.getMessage());
			erro.printStackTrace();

			// Se qualquer coisa deu errado, desfaz tudo
			try {
				Connection conexao = ConexaoFactory.getConexao();
				conexao.rollback();
				System.out.println("ROLLBACK realizado");
			} catch (SQLException rollbackErro) {
				rollbackErro.printStackTrace();
			}

			// Deletar todas as fotos em caso de erro
			// for (String caminho : caminhosCompletos) {
			// deletarArquivo(caminho);
			// }
			requisicao.setAttribute("erro", "Erro ao publicar o anúncio: " + erro.getMessage());
			try {
				doGet(requisicao, resposta);
			} catch (ServletException e) {
				e.printStackTrace();
			}
			return;

		} finally {
			// Restaurar auto-commit
			try {
				Connection conexao = ConexaoFactory.getConexao();
				conexao.setAutoCommit(true);
				System.out.println("Auto-commit restaurado");
			} catch (SQLException erro) {
				erro.printStackTrace();
			}
		}

		// Redireciona para "Meus anúncios" após sucesso
		resposta.sendRedirect(requisicao.getContextPath() + "/meus-anuncios");
	}

	private String gerarNomeUnicoArquivo(String nomeOriginal) {
		String extensao = "";
		int posicaoPonto = nomeOriginal.lastIndexOf(".");
		if (posicaoPonto >= 0) {
			extensao = nomeOriginal.substring(posicaoPonto);
		}
		// timestamp + pedaço aleatório = nome único mesmo com várias fotos de uma vez
		String parteAleatoria = java.util.UUID.randomUUID().toString().substring(0, 8);
		return System.currentTimeMillis() + "-" + parteAleatoria + extensao;
	}

	private void deletarArquivo(String caminho) {
		try {
			Files.deleteIfExists(Paths.get(caminho));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}