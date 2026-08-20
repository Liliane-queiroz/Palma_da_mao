<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Anunciar Produto - Palma do Campo</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/global.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/cadastro-produto.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com"
	crossorigin="anonymous">
<link
	href="https://fonts.googleapis.com/css2?family=Inter&family=Righteous&display=swap"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<!-- Cabeçalho verde com ícone laranja -->
		<div class="cadastro-cabecalho">
			<img
				src="${pageContext.request.contextPath}/resources/images/logo/logo palma do campo 7.png"
				alt="Logo" class="cadastro-logo">
			<c:choose>
				<c:when test="${eEdicao}">
					<h1>Editar Anúncio</h1>
				</c:when>
				<c:otherwise>
					<h1>Anunciar produto</h1>
				</c:otherwise>
			</c:choose>
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
		<form method="POST"
			action="${pageContext.request.contextPath}/cadastro-produto"
			enctype="multipart/form-data">
			<!-- Hidden field com ID (se for edição) -->
			<c:if test="${eEdicao}">
				<input type="hidden" name="id" value="${produtoParaEditar.id}">
			</c:if>

			<!-- Foto (obrigatória) - área tracejada clicável -->
			<div class="form-group">
				<label for="arquivo">Foto do produto <span
					class="campo-obrigatorio">*</span></label>
				<c:choose>
					<c:when test="${eEdicao}">
						<p class="aviso-foto">
							<i class="bi bi-info-circle"></i> Foto não pode ser alterada,para trocar a foto, delete e recrie o anúncio.
						</p>
					</c:when>
					<c:otherwise>
						<label for="arquivo" class="upload-foto"> <i
							class="bi bi-camera"></i>
							<p>Arraste ou clique para enviar</p> <input type="file"
							id="arquivo" name="arquivo" accept="image/*" multiple required>
						</label>
						<small>Formatos aceitos: JPG, PNG, GIF. Máximo 5 MB.
							Máximo 10 fotos.</small>
					</c:otherwise>
				</c:choose>
			</div>

			<!-- Preview das fotos -->
			<div id="preview-fotos" class="preview-container"></div>

			<!-- Nome (obrigatório) -->
			<div class="form-group">
				<label for="nome">Nome do produto <span
					class="campo-obrigatorio">*</span></label> <input type="text" id="nome"
					name="nome" required maxlength="150" placeholder="Ovos caipira"
					value="${eEdicao ? produtoParaEditar.nome : ''}">
			</div>

			<!-- Categoria (obrigatória) -->
			<div class="form-group">
				<label for="categoria">Categoria <span
					class="campo-obrigatorio">*</span></label> <select id="categoria"
					name="categoria" required>
					<option value="">-- Escolha uma categoria --</option>
					<c:forEach var="categoria" items="${categorias}">
						<option value="${categoria.id}"
							<c:if test="${eEdicao && produtoParaEditar.categoriaId == categoria.id}">selected</c:if>>
							${categoria.descricao}</option>
					</c:forEach>
				</select>
			</div>

			<!-- Descrição -->
			<div class="form-group">
				<label for="descricao">Descrição do produto</label>
				<textarea id="descricao" name="descricao" rows="4"
					placeholder="Descreva seu produto com detalhes...">${eEdicao ? produtoParaEditar.descricao : ''}</textarea>
			</div>

			<!-- Preço e Quantidade lado a lado -->
			<div class="form-row">
				<div class="form-group">
					<label for="preco">Preço (R$) <span
						class="campo-obrigatorio">*</span></label> <input type="number" id="preco"
						name="preco" step="0.01" required placeholder="0.00"
						value="${eEdicao ? produtoParaEditar.precoEstimado : ''}">
				</div>

				<c:if test="${eEdicao}">
					<fmt:formatNumber value="${estoque.quantidade}" pattern="#,##0.###"
						var="quantidadeFormatada" />
				</c:if>

				<div class="form-group">
					<label for="quantidade">Quantidade <span
						class="campo-obrigatorio">*</span></label> <input type="text"
						id="quantidade" name="quantidade" required placeholder="10"
						value="${eEdicao ? quantidadeFormatada : ''}">
				</div>
			</div>

			<!-- Unidade e Data prevista lado a lado -->
			<div class="form-row">
				<div class="form-group">
					<label for="unidade">Unidade <span
						class="campo-obrigatorio">*</span></label> <select id="unidade"
						name="unidade" required>
						<option value="">-- Escolha a unidade --</option>
						<option value="UN"
							<c:if test="${eEdicao && estoque.unidade == 'UN'}">selected</c:if>>Unidade
							(UN)</option>
						<option value="KG"
							<c:if test="${eEdicao && estoque.unidade == 'KG'}">selected</c:if>>Quilograma
							(KG)</option>
						<option value="L"
							<c:if test="${eEdicao && estoque.unidade == 'L'}">selected</c:if>>Litro
							(L)</option>
						<option value="DZ"
							<c:if test="${eEdicao && estoque.unidade == 'DZ'}">selected</c:if>>Dúzia
							(DZ)</option>
						<option value="MAÇO"
							<c:if test="${eEdicao && estoque.unidade == 'MAÇO'}">selected</c:if>>Maço</option>
					</select>
				</div>

				<div class="form-group">
					<label for="data-entrega">Data prevista de entrega</label> <input
						type="date" id="data-entrega" name="data-entrega">
				</div>
			</div>

			<!-- Botão de submissão -->
			<button type="submit" class="btn-submit">
				<i class="bi bi-check-circle"></i> Publicar anúncio
			</button>
		</form>

		<!-- Link voltar à vitrine -->
		<div class="voltar">
			<a href="${pageContext.request.contextPath}/vitrine">← Voltar à	vitrine
			</a>
		</div>
	</div>

	<!-- Script para preview de fotos -->
	<script
		src="${pageContext.request.contextPath}/resources/js/cadastro-produto.js"></script>
</body>
</html>