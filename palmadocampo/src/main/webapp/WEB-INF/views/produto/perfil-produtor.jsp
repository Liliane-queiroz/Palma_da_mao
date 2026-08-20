<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Perfil - ${produtor.nome} - Palma do Campo</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/global.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/perfil-produtor.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/vitrine.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com"
	crossorigin="anonymous">
<link
	href="https://fonts.googleapis.com/css2?family=Inter&family=Righteous&display=swap"
	rel="stylesheet">
</head>
<body>
	<jsp:include page="/WEB-INF/views/componentes/navbar.jsp" />
	<div class="perfil-topo">
		<a href="${pageContext.request.contextPath}/vitrine"
			class="btn-voltar-perfil"> <i class="bi bi-arrow-left"></i>
			Voltar
		</a>
	</div>
	<div class="container-perfil">
		<div class="perfil-header">
			<div class="avatar-grande">
				<i class="bi bi-person-circle"></i>
			</div>

			<div class="perfil-info">
				<h2 class="nome-produtor">${produtor.nome}</h2>

				<c:if test="${not empty produtor.apresentacao}">
					<p class="bio-produtor">${produtor.apresentacao}</p>
				</c:if>

				<div class="perfil-stats">
					<div class="stat-item">
						<span class="stat-numero">${totalProdutos}</span> <span
							class="stat-label">Produto<c:if
								test="${totalProdutos != 1}">s</c:if></span>
					</div>
					<div class="stat-divisor"></div>
					<div class="stat-item">
						<span class="stat-label">Desde</span> <span class="stat-data">${dataCadastroFormatada}</span>
					</div>
				</div>

				<c:if test="${sessionScope.usuarioLogado.id == produtor.id}">
					<a href="${pageContext.request.contextPath}/editar-perfil"
						class="btn-editar-perfil"> <i class="bi bi-pencil"></i> Editar
						perfil
					</a>
				</c:if>
			</div>
		</div>

		<div class="perfil-produtos">
			<h2>Produtos publicados</h2>

			<c:if test="${totalProdutos > 0}">
				<p class="texto-info">Produtos deste produtor na plataforma</p>

				<div class="container-vitrine">
					<c:forEach var="produto" items="${produtos}">
						<div class="card-produto">
							<div class="img-card">
								<c:set var="primeiraFoto"
									value="${not empty produto.fotoUrl ? fn:split(produto.fotoUrl, ',')[0] : ''}" />
								<c:if test="${not empty primeiraFoto}">
									<img
										src="${pageContext.request.contextPath}/imagem/${primeiraFoto}"
										class="card-imagem" alt="${produto.nome}" />
								</c:if>
							</div>
							<h3 class="card-nome">
								<c:out value="${produto.nome}" />
							</h3>
							<p class="card-preco">
								<fmt:formatNumber value="${produto.precoEstimado}"
									type="currency" currencySymbol="R$ " />
							</p>
							<p class="card-categoria">
								<c:out value="${produto.categoriaDescricao}" />
							</p>
							<a
								href="${pageContext.request.contextPath}/detalhes?id=${produto.id}"
								class="btn-detalhes"> Mais detalhes </a>
						</div>
					</c:forEach>
				</div>
			</c:if>
			<c:if test="${totalProdutos == 0}">
				<div class="produtos-vazios">
					<i class="bi bi-inbox"></i>
					<p>Este produtor ainda não publicou produtos</p>
				</div>
			</c:if>
		</div>
	</div>

	<script src="${pageContext.request.contextPath}/resources/js/global.js"></script>
</body>
</html>