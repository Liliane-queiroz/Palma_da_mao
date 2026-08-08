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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 5242880, // 5 MB
        maxRequestSize = 5242880 // 5 MB
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
            // ===== ETAPA 1: Validar e processar upload de MÚLTIPLAS imagens =====
            Collection<Part> todasAsParts = requisicao.getParts()
                .stream()
                .filter(part -> "arquivo".equals(part.getName()) && part.getSize() > 0)
                .collect(Collectors.toList());

            if (todasAsParts.isEmpty()) {
                requisicao.setAttribute("erro", "Você deve selecionar pelo menos uma imagem");
                doGet(requisicao, resposta);
                return;
            }

            // Validar quantidade máxima
            if (todasAsParts.size() > 5) {
                requisicao.setAttribute("erro", "Máximo 5 fotos permitidas!");
                doGet(requisicao, resposta);
                return;
            }

            // Lista pra guardar os caminhos das fotos
            List<String> caminhosFotos = new ArrayList<>();
            List<String> caminhosCompletos = new ArrayList<>(); // Pra deletar se erro

            // Processar cada arquivo
            for (Part parteArquivo : todasAsParts) {
                
                // Validar se é imagem
                String tipoConteudo = parteArquivo.getContentType();
                if (!tipoConteudo.startsWith("image/")) {
                    // Deletar fotos já salvadas
                    for (String caminho : caminhosCompletos) {
                        deletarArquivo(caminho);
                    }
                    requisicao.setAttribute("erro", "Um ou mais arquivos não são imagens (JPG, PNG, GIF)");
                    doGet(requisicao, resposta);
                    return;
                }

                // Validar tamanho (máx 5MB)
                if (parteArquivo.getSize() > 5 * 1024 * 1024) {
                    // Deletar fotos já salvadas
                    for (String caminho : caminhosCompletos) {
                        deletarArquivo(caminho);
                    }
                    requisicao.setAttribute("erro", "Uma ou mais fotos ultrapassam 5 MB!");
                    doGet(requisicao, resposta);
                    return;
                }

                // Gerar nome do arquivo com timestamp
                String nomeOriginal = extrairNomeArquivo(parteArquivo);
                String extensao = extrairExtensao(nomeOriginal);
                String nomeArquivo = System.currentTimeMillis() + "_" + nomeOriginal;

                // Salvar arquivo no servidor
                String caminhoAbsoluto = getServletContext().getRealPath("/") + PASTA_UPLOAD;
                String caminhoRelativo = "/" + PASTA_UPLOAD + nomeArquivo;

                // Criar a pasta se não existir
                File pastaUpload = new File(caminhoAbsoluto);
                if (!pastaUpload.exists()) {
                    pastaUpload.mkdirs();
                    System.out.println("Pasta criada: " + caminhoAbsoluto);
                }

                String caminhoCompleto = caminhoAbsoluto + nomeArquivo;
                parteArquivo.write(caminhoCompleto);
                System.out.println("Arquivo salvo em: " + caminhoCompleto);

                // Adicionar na lista de caminhos
                caminhosFotos.add(caminhoRelativo);
                caminhosCompletos.add(caminhoCompleto);
            }

            // Juntar todos os caminhos em uma string separada por vírgula
            String fotosUrl = String.join(",", caminhosFotos);

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
                // Deletar fotos se erro
                for (String caminho : caminhosCompletos) {
                    deletarArquivo(caminho);
                }
                requisicao.setAttribute("erro", "Preencha todos os campos obrigatórios");
                doGet(requisicao, resposta);
                return;
            }

            // Converter tipos
            int categoriaId = Integer.parseInt(categoriaIdStr);
            Double produtoPrecoDouble = produtoPreco.isEmpty() ? null : Double.parseDouble(produtoPreco);
            Double quantidade = Double.parseDouble(quantidadeStr);
            LocalDate dataEntrega = dataEntregaStr.isEmpty() ? null
                    : LocalDate.parse(dataEntregaStr, DateTimeFormatter.ISO_LOCAL_DATE);

            // ===== ETAPA 3: Inserir Produto e Estoque em TRANSAÇÃO =====
            Connection conexao = null;
            try {
                conexao = ConexaoFactory.getConexao();

                // Desabilitar auto-commit para controlar transação
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
                    comandoProduto.setString(4, fotosUrl); // Múltiplas fotos separadas por vírgula
                    if (dataEntrega != null) {
                        comandoProduto.setObject(5, dataEntrega);
                    } else {
                        comandoProduto.setNull(5, java.sql.Types.DATE);
                    }
                    comandoProduto.setInt(6, categoriaId);
                    comandoProduto.setInt(7, 1); // situacao_id = 1 (ATIVO)

                    int linhasAfetadas = comandoProduto.executeUpdate();
                    System.out.println("INSERT Produto: " + linhasAfetadas + " linhas afetadas");

                    try (ResultSet chaveGerada = comandoProduto.getGeneratedKeys()) {
                        if (chaveGerada.next()) {
                            produtoId = chaveGerada.getInt(1);
                            System.out.println("Produto criado com ID: " + produtoId);
                        } else {
                            throw new SQLException("Falha ao obter ID do produto gerado");
                        }
                    }
                }

                // INSERT ESTOQUE - associa o produto ao usuário (produtor)
                String sqlEstoque = "INSERT INTO estoque (usuario_id, produto_id, est_qtd, est_unidade, situacao_id) " +
                        "VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement comandoEstoque = conexao.prepareStatement(sqlEstoque)) {
                    comandoEstoque.setInt(1, USUARIO_TESTE); // Maria Joaquina (ID 1)
                    comandoEstoque.setInt(2, produtoId);
                    comandoEstoque.setDouble(3, quantidade);
                    comandoEstoque.setString(4, unidade);
                    comandoEstoque.setInt(5, 1); // situacao_id = 1 (ATIVO)

                    int linhasAfetadas = comandoEstoque.executeUpdate();
                    System.out.println("INSERT Estoque: " + linhasAfetadas + " linhas afetadas");
                }

                // Se chegou aqui, ambos os INSERTs funcionaram
                conexao.commit();
                System.out.println("COMMIT realizado com sucesso");

            } catch (SQLException erro) {
                System.out.println("ERRO na transação: " + erro.getMessage());
                erro.printStackTrace();

                // Se qualquer coisa deu errado, desfaz tudo
                if (conexao != null) {
                    try {
                        conexao.rollback();
                        System.out.println("ROLLBACK realizado");
                    } catch (SQLException rollbackErro) {
                        rollbackErro.printStackTrace();
                    }
                }

                // Deletar todas as fotos em caso de erro
                for (String caminho : caminhosCompletos) {
                    deletarArquivo(caminho);
                }
                requisicao.setAttribute("erro", "Erro ao publicar o anúncio: " + erro.getMessage());
                try {
                    doGet(requisicao, resposta);
                } catch (ServletException e) {
                    e.printStackTrace();
                }
                return;

            } finally {
                // Restaurar auto-commit e fechar conexão
                if (conexao != null) {
                    try {
                        conexao.setAutoCommit(true);
                        conexao.close();
                        System.out.println("Conexão fechada");
                    } catch (SQLException erro) {
                        erro.printStackTrace();
                    }
                }
            }

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