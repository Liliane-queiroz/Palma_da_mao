package br.com.palmadocampo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import br.com.palmadocampo.dao.CategoriaDAO;
import br.com.palmadocampo.dao.ConexaoFactory;
import br.com.palmadocampo.model.Categoria;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/cadastro-produto")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,      // 1 MB
    maxFileSize = 5242880,                // 5 MB
    maxRequestSize = 5242880              // 5 MB
)
public class CadastroProdutoServlet extends HttpServlet {

    private static final String PASTA_UPLOAD = "resources/images/uploads/produtos/";
    private static final int USUARIO_TESTE = 1; // Maria Joaquina

    @Override
    protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {

        try {
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            List<Categoria> categoriasAtivas = categoriaDAO.listarAtivas();
            requisicao.setAttribute("categorias", categoriasAtivas);
            requisicao.getRequestDispatcher("/WEB-INF/views/produtor/cadastro-produto.jsp")
                .forward(requisicao, resposta);
        } catch (SQLException erro) {
            erro.printStackTrace();
            resposta.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                "Erro ao carregar categorias");
        }
    }

    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {

        try {
            // ===== ETAPA 1: Validar e processar upload da imagem =====
            Part parteArquivo = requisicao.getPart("arquivo");
            if (parteArquivo == null || parteArquivo.getSize() == 0) {
                requisicao.setAttribute("erro", "Você deve selecionar uma imagem");
                doGet(requisicao, resposta);
                return;
            }

            // Validar se é imagem
            String tipoConteudo = parteArquivo.getContentType();
            if (!tipoConteudo.startsWith("image/")) {
                requisicao.setAttribute("erro", "O arquivo deve ser uma imagem (JPG, PNG, GIF)");
                doGet(requisicao, resposta);
                return;
            }

            // Gerar nome do arquivo com timestamp
            String nomeOriginal = extrairNomeArquivo(parteArquivo);
            String extensao = extrairExtensao(nomeOriginal);
            String nomeArquivo = System.currentTimeMillis() + "_" + nomeOriginal;

            // Salvar arquivo no servidor
            String caminhoAbsoluto = getServletContext().getRealPath("/" + PASTA_UPLOAD);
            File pastaUpload = new File(caminhoAbsoluto);
            if (!pastaUpload.exists()) {
                pastaUpload.mkdirs();
            }

            String caminhoCompleto = caminhoAbsoluto + File.separator + nomeArquivo;
            parteArquivo.write(caminhoCompleto);

            // Caminho relativo pra guardar no banco
            String caminhoRelativo = "/" + PASTA_UPLOAD + nomeArquivo;

            // ===== ETAPA 2: Coletar dados do formulário =====
            String produtoNome = requisicao.getParameter("nome").trim();
            String produtoDescricao = requisicao.getParameter("descricao").trim();
            String produtoPreco = requisicao.getParameter("preco").trim();
            String categoriaIdStr = requisicao.getParameter("categoria");
            String quantidadeStr = requisicao.getParameter("quantidade").trim();
            String unidade = requisicao.getParameter("unidade").trim();
            String dataEntregaStr = requisicao.getParameter("dataEntrega").trim();

            // Validar campos obrigatórios
            if (produtoNome.isEmpty() || categoriaIdStr == null || quantidadeStr.isEmpty() || 
                unidade.isEmpty()) {
                deletarArquivo(caminhoCompleto);
                requisicao.setAttribute("erro", "Preencha todos os campos obrigatórios");
                doGet(requisicao, resposta);
                return;
            }

            // Converter tipos
            int categoriaId = Integer.parseInt(categoriaIdStr);
            Double produtoPrecoDouble = produtoPreco.isEmpty() ? null : Double.parseDouble(produtoPreco);
            Double quantidade = Double.parseDouble(quantidadeStr);
            LocalDate dataEntrega = dataEntregaStr.isEmpty() ? null : 
                LocalDate.parse(dataEntregaStr, DateTimeFormatter.ISO_LOCAL_DATE);

            // ===== ETAPA 3: Inserir Produto e Estoque em TRANSAÇÃO =====
            Connection conexao = ConexaoFactory.getConexao();
            try {
                //Desabilitar auto-commit para controlar transação, ou seja, caso ocorra algum erro de falha de conexção de internet ele não salva automaticamente, 
                // pois o Mysql confirma cada comando atuomaticamente.
                conexao.setAutoCommit(false);

                // INSERT PRODUTO
                String sqlProduto = "INSERT INTO produto (prod_nome, prod_descricao, prod_preco_estimado, " +
                    "prod_foto_url, prod_data_prevista_entrega, categoria_id, situacao_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

                int produtoId;
                try (PreparedStatement comandoProduto = conexao.prepareStatement(sqlProduto,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {
                    comandoProduto.setString(1, produtoNome);
                    comandoProduto.setString(2, produtoDescricao);
                    if (produtoPrecoDouble != null) {
                        comandoProduto.setDouble(3, produtoPrecoDouble);
                    } else {
                        comandoProduto.setNull(3, java.sql.Types.DECIMAL);
                    }
                    comandoProduto.setString(4, caminhoRelativo);
                    if (dataEntrega != null) {
                        comandoProduto.setObject(5, dataEntrega);
                    } else {
                        comandoProduto.setNull(5, java.sql.Types.DATE);
                    }
                    comandoProduto.setInt(6, categoriaId);
                    comandoProduto.setInt(7, 1); // situacao_id = 1 (ATIVO)
                    comandoProduto.executeUpdate();

                    try (ResultSet chaveGerada = comandoProduto.getGeneratedKeys()) {
                        if (chaveGerada.next()) {
                            produtoId = chaveGerada.getInt(1);
                        } else {
                            throw new SQLException("Falha ao obter ID do produto gerado");
                        }
                    }
                }

                // INSERT ESTOQUE
                String sqlEstoque = "INSERT INTO estoque (usuario_id, produto_id, est_qtd, est_unidade, situacao_id) " +
                    "VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement comandoEstoque = conexao.prepareStatement(sqlEstoque)) {
                    comandoEstoque.setInt(1, USUARIO_TESTE); // Maria Joaquina (ID 1)
                    comandoEstoque.setInt(2, produtoId);
                    comandoEstoque.setDouble(3, quantidade);
                    comandoEstoque.setString(4, unidade);
                    comandoEstoque.setInt(5, 1); // situacao_id = 1 (ATIVO)
                    comandoEstoque.executeUpdate();
                }

                // Se chegou aqui, ambos os INSERTs funcionaram
                conexao.commit(); //seu trabalho é confirmar que tudo funcionou

            } catch (SQLException erro) {
                // Se qualquer coisa deu errado, desfaz tudo
                conexao.rollback();
                deletarArquivo(caminhoCompleto);
                erro.printStackTrace();
                requisicao.setAttribute("erro", "Erro ao publicar o anúncio: " + erro.getMessage());
                doGet(requisicao, resposta);
                return;
            } finally {
                // Restaurar auto-commit e fechar conexão
                conexao.setAutoCommit(true);
                conexao.close();
            }//apaga aqui

            // ===== ETAPA 4: POST-Redirect-GET =====
            resposta.sendRedirect(requisicao.getContextPath() + "/vitrine");

        } catch (NumberFormatException erro) {
            requisicao.setAttribute("erro", "Valores numéricos inválidos");
            try {
                doGet(requisicao, resposta);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        } catch (Exception erro) {
            erro.printStackTrace();
            try {
                requisicao.setAttribute("erro", "Erro inesperado: " + erro.getMessage());
                doGet(requisicao, resposta);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Extrai o nome do arquivo de um Part (multipart)
     */
    private String extrairNomeArquivo(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String token : contentDisposition.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "arquivo";
    }

    /**
     * Extrai a extensão do arquivo
     */
    private String extrairExtensao(String nomeArquivo) {
        int ultimoPonto = nomeArquivo.lastIndexOf(".");
        if (ultimoPonto > 0) {
            return nomeArquivo.substring(ultimoPonto);
        }
        return "";
    }

    /**
     * Deleta arquivo em caso de erro
     */
    private void deletarArquivo(String caminho) {
        try {
            Files.deleteIfExists(Paths.get(caminho));
        } catch (IOException erro) {
            System.err.println("Erro ao deletar arquivo: " + erro.getMessage());
        }
    }
}