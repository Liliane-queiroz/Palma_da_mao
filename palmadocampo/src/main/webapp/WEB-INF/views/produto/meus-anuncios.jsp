<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Meus Anúncios - Palma do Campo</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/global.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/meus-anuncios.css">

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com"
	crossorigin="anonymous">
<link
	href="https://fonts.googleapis.com/css2?family=Righteous&display=swap"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Inter&display=swap"
	rel="stylesheet">

</head>
<body>
	<jsp:include page="/WEB-INF/views/componentes/navbar.jsp" />

	<div class="secao-titulo-anuncios">
		<div class="titulo-container">
			<h1>Meus Anúncios</h1>
			<c:if test="${not empty produtos}">
				<span class="contador-produtos">${produtos.size()} produto<c:if
						test="${produtos.size() != 1}">s</c:if></span>
			</c:if>
		</div>
	</div>

	<div class="container-vitrine">
		<c:choose>
			<c:when test="${empty produtos}">
				<div class="vitrine-vazia-container">
					<div class="vitrine-vazia">
						<i class="bi bi-inbox"></i>
						<p>Você ainda não tem anúncios publicados.</p>
						<p class="subtexto">Comece agora e conecte-se com seus
							clientes!</p>
						<a href="${pageContext.request.contextPath}/cadastro-produto"
							class="btn-publicar-primeiro"> <i class="bi bi-plus-lg"></i>
							Publicar Primeiro Anúncio
						</a>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<c:forEach var="produto" items="${produtos}">
					<div class="card-produto-meus-anuncios">
						<div class="img-card">
							<c:set var="primeiraFoto"
								value="${not empty produto.fotoUrl ? fn:split(produto.fotoUrl, ',')[0] : ''}" />
							<c:if test="${not empty primeiraFoto}">
								<img
									src="${pageContext.request.contextPath}/imagem/${primeiraFoto}"
									class="card-imagem" alt="${produto.nome}" />
							</c:if>

							<div class="botoes-acao">
								<a
									href="${pageContext.request.contextPath}/cadastro-produto?id=${produto.id}"
									class="btn-acao btn-editar" title="Editar anúncio"> <i
									class="bi bi-pencil"></i>
								</a>
								<button class="btn-acao btn-deletar" title="Deletar anúncio"
									data-id="${produto.id}">
									<i class="bi bi-trash"></i>
								</button>
							</div>
						</div>

						<div class="card-info">
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
								class="btn-detalhes"> Visualizar Anúncio </a>
						</div>
					</div>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>

	<div class="navbar-mobile-menu">
		<a href="${pageContext.request.contextPath}/vitrine"
			class="mobile-menu-item"> <i class="bi bi-house-fill"></i> <span>Início</span>
		</a> <a href="${pageContext.request.contextPath}/cadastro-produto"
			class="mobile-menu-item"> <i class="bi bi-plus-circle"></i> <span>Anunciar</span>
		</a> <a href="#" class="mobile-menu-item"> <i class="bi bi-list"></i>
			<span>Menu</span>
		</a>
	</div>

	<div id="modal-deletar" class="modal-deletar" style="display: none;">
		<div class="modal-conteudo">
			<h3>Deletar Anúncio?</h3>
			<p>Esta ação é permanente e não pode ser desfeita.</p>
			<div class="modal-botoes">
				<button class="btn-cancelar" id="btn-cancelar-deletar">Cancelar</button>
				<button class="btn-confirmar-deletar" id="btn-confirmar-deletar">Deletar</button>
			</div>
		</div>
	</div>

	<script
		src="${pageContext.request.contextPath}/resources/js/meus-anuncios.js"></script>
</body>
</html>