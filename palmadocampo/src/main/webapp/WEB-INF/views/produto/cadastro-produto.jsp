<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Anunciar Produto - Palma do Campo</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/global.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/cadastro-produto.css">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Inter&family=Righteous&display=swap" rel="stylesheet">
</head>
<body>
    <div class="container">

        <!-- Cabeçalho verde com ícone laranja -->
        <div class="cadastro-cabecalho">
            <img src="${pageContext.request.contextPath}/resources/images/logo/logo palma do campo 7.png" alt="Logo" class="cadastro-logo">
            <h1>Anunciar produto</h1>
        </div>

        <!-- Mensagem de erro (se houver) -->
        <c:if test="${not empty erro}">
            <div class="erro">
                <strong>Erro:</strong> ${erro}
            </div>
        </c:if>

        <!-- Mensagem de sucesso (se houver) -->
        <c:if test="${not empty sucesso}">
            <div class="sucesso">
                <strong>Sucesso:</strong> ${sucesso}
            </div>
        </c:if>

        <!-- Formulário -->
        <form method="POST" action="${pageContext.request.contextPath}/cadastro-produto" enctype="multipart/form-data">

            <!-- Foto (obrigatória) - área tracejada clicável -->
            <div class="form-group">
                <label for="arquivo">Foto do produto <span class="campo-obrigatorio">*</span></label>
                <label for="arquivo" class="upload-foto">
                    <i class="bi bi-camera"></i>
                    <p>Arraste ou clique para enviar</p>
                    <input type="file" id="arquivo" name="arquivo" accept="image/*" multiple required>
                </label>
                <small>Formatos aceitos: JPG, PNG, GIF. Máximo 5 MB. Máximo 10 fotos.</small>
            </div>

            <!-- Preview das fotos -->
            <div id="preview-fotos" class="preview-container"></div>

            <!-- Nome (obrigatório) -->
            <div class="form-group">
                <label for="nome">Nome do produto <span class="campo-obrigatorio">*</span></label>
                <input type="text" id="nome" name="nome" required maxlength="150" placeholder="Ovos caipira">
            </div>

            <!-- Categoria (obrigatória) -->
            <div class="form-group">
                <label for="categoria">Categoria <span class="campo-obrigatorio">*</span></label>
                <select id="categoria" name="categoria" required>
                    <option value="">-- Escolha uma categoria --</option>
                    <c:forEach var="categoria" items="${categorias}">
                        <option value="${categoria.id}">${categoria.descricao}</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Descrição -->
            <div class="form-group">
   				<label for="descricao">Descrição do produto <span class="campo-obrigatorio">*</span></label>
    			<textarea id="descricao" name="descricao" maxlength="1000" required placeholder="Descreva seu produto com detalhes..."></textarea>
			</div>

            <!-- Preço e Quantidade em duas colunas -->
            <div class="form-row">
                <div class="form-group">
    				<label for="preco">Preço (R$) <span class="campo-obrigatorio">*</span></label>
    				<input type="number" id="preco" name="preco" step="0.01" min="0" required placeholder="0,00">
				</div>

                <div class="form-group">
                    <label for="quantidade">Quantidade <span class="campo-obrigatorio">*</span></label>
                    <input type="number" id="quantidade" name="quantidade" step="0.01" min="0" required placeholder="50">
                </div>
            </div>

            <!-- Unidade e Data de entrega em duas colunas -->
            <div class="form-row">
                <div class="form-group">
                    <label for="unidade">Unidade <span class="campo-obrigatorio">*</span></label>
                    <select id="unidade" name="unidade" required>
                        <option value="">-- Escolha a unidade --</option>
                        <option value="UN">Unidade (UN)</option>
                        <option value="KG">Quilograma (KG)</option>
                        <option value="G">Grama (G)</option>
                        <option value="L">Litro (L)</option>
                        <option value="ML">Mililitro (ML)</option>
                        <option value="DZ">Dúzia (DZ)</option>
                        <option value="MACO">Maço (MACO)</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="dataEntrega">Data prevista de entrega</label>
                    <input type="date" id="dataEntrega" name="dataEntrega">
                </div>
            </div>

            <!-- Botão de envio -->
            <button type="submit">
                <i class="bi bi-check-lg"></i>
                Publicar anúncio
            </button>

            <!-- Link para voltar -->
            <div class="voltar">
                <a href="${pageContext.request.contextPath}/vitrine">← Voltar à vitrine</a>
            </div>
        </form>
    </div>

    <!-- Link do arquivo JS -->
    <script src="${pageContext.request.contextPath}/resources/js/cadastro-produto.js"></script>
</body>
</html>