<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Vitrine - Palma do Campo</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/global.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/vitrine.css">

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
	
	<main class="conteudo-principal">

		<c:if test="${not empty termoBuscado or not empty cidadeBuscada}">
		<div class="titulo-busca">
			<h2>
				<c:choose>
					<c:when test="${not empty termoBuscado}">
						Resultados para "<c:out value='${termoBuscado}' />"<c:if test="${not empty cidadeBuscada}"> em <c:out value='${cidadeBuscada}' /></c:if>
					</c:when>
					<c:otherwise>
						Produtos em <c:out value='${cidadeBuscada}' />
					</c:otherwise>
				</c:choose>
			</h2>
		</div>
	</c:if>
	<c:if test="${not empty categoriaSelecionada}">
		<div class="titulo-busca">
			<h2>
				Categoria:
				<c:out value='${categoriaSelecionada}' />
			</h2>
		</div>
	</c:if>
	<div class="container-vitrine">
		<c:choose>
			<c:when test="${empty produtos}">
								<c:choose>
					<c:when test="${not empty termoBuscado and not empty cidadeBuscada}">
						<p class="vitrine-vazia">
							Nenhum resultado para "<c:out value='${termoBuscado}' />" em <c:out value='${cidadeBuscada}' />.
							<br>Tente buscar em outra cidade.
						</p>
					</c:when>
					<c:when test="${not empty termoBuscado}">
						<p class="vitrine-vazia">
							Nenhum resultado para "<c:out value='${termoBuscado}' />".
						</p>
					</c:when>
					<c:when test="${not empty cidadeBuscada}">
						<p class="vitrine-vazia">
							Nenhum produto disponível em <c:out value='${cidadeBuscada}' /> no momento.
							<br>Tente buscar em outra cidade.
						</p>
					</c:when>
					<c:otherwise>
						<p class="vitrine-vazia">Nenhum produto disponível no momento.</p>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<c:forEach var="produto" items="${produtos}">
					<div class="card-produto">
						<div class="img-card">

							<!-- ${produto.fotoUrl} -->
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
			</c:otherwise>
		</c:choose>
	</div>

	<!-- Menu Mobile
	<div class="navbar-mobile-menu">
		<a href="#" class="mobile-menu-item"> <i class="bi bi-house-fill"></i>
			<span>Início</span>
		</a> <a href="#" class="mobile-menu-item"> <i
			class="bi bi-plus-circle"></i> <span>Anunciar</span>
		</a> <a href="#" class="mobile-menu-item"> <i class="bi bi-list"></i>
			<span>Menu</span>
		</a>
	</div>-->
	
	</main>
	
	<jsp:include page="/WEB-INF/views/componentes/rodape.jsp" />
</body>
</html>
