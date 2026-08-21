<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>${produto.nome}-PalmadoCampo</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/global.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/detalhes.css">

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
	<c:set var="listaFotos"
		value="${not empty produto.fotoUrl ? fn:split(produto.fotoUrl, ',') : null}" />

	<jsp:include page="/WEB-INF/views/componentes/navbar.jsp" />
	<div class="container-detalhes">

		<!-- LADO ESQUERDO: foto do produto com as setas (enfeite por enquanto) -->
		<div class="detalhes-foto">
			<button type="button" class="seta-foto seta-foto-esquerda"
				aria-label="Foto anterior" onclick="trocarFoto(-1)">
				<i class="bi bi-chevron-left"></i>
			</button>

			<c:choose>
				<c:when test="${not empty listaFotos}">
					<img id="imagem-fundo"
						src="${pageContext.request.contextPath}/imagem/${listaFotos[0]}"
						class="detalhes-imagem-fundo" alt="" aria-hidden="true" />
					<img id="imagem-principal"
						src="${pageContext.request.contextPath}/imagem/${listaFotos[0]}"
						class="detalhes-imagem" alt="${produto.nome}" />
				</c:when>
				<c:otherwise>
					<div class="detalhes-imagem"
						style="display: flex; align-items: center; justify-content: center;">
						<i class="bi bi-image" style="font-size: 4rem; color: #ccc;"></i>
					</div>
				</c:otherwise>
			</c:choose>

			<button type="button" class="seta-foto seta-foto-direita"
				aria-label="Próxima foto" onclick="trocarFoto(1)">
				<i class="bi bi-chevron-right"></i>
			</button>

			<c:if test="${fn:length(listaFotos) > 1}">
				<div class="miniaturas">
					<c:forEach var="foto" items="${listaFotos}" varStatus="s">
						<img src="${pageContext.request.contextPath}/imagem/${foto}"
							class="miniatura ${s.index == 0 ? 'miniatura-ativa' : ''}"
							onclick="mostrarFoto(${s.index})" alt="Miniatura ${s.count}" />
					</c:forEach>
				</div>
			</c:if>
		</div>

		<!-- LADO DIREITO: painel creme com todas as informações -->
		<div class="detalhes-painel">

			<h1 class="detalhes-nome">
				<c:out value="${produto.nome}" />
			</h1>

			<p class="detalhes-preco">
				<fmt:formatNumber value="${produto.precoEstimado}" type="currency"
					currencySymbol="R$ " />
				<c:if test="${not empty produto.unidade}">
					<span class="preco-unidade">/ ${produto.unidade}</span>
				</c:if>
			</p>

			<p class="detalhes-localizacao">
				<i class="bi bi-geo-alt"></i> Anunciado em
				<c:out value="${produto.produtorCidade}" />
				,
				<c:out value="${produto.produtorRegiao}" />
			</p>

			<div class="detalhes-descricao">
				<strong>Detalhes do produto:</strong>
				<p>
					<c:out value="${produto.descricao}" />
				</p>
			</div>

			<!-- Informações extras do produto -->
			<div class="detalhes-infos">
				<p class="info-linha">
					<i class="bi bi-tag"></i> <span>Categoria:</span>
					<c:out value="${produto.categoriaDescricao}" />
				</p>

				<c:if test="${not empty dataEntregaFormatada}">
					<p class="info-linha">
						<i class="bi bi-calendar-check"></i> <span>Entrega
							prevista:</span> ${dataEntregaFormatada}
					</p>
				</c:if>
			</div>

			<div class="detalhes-vendedor">
				<h2 class="vendedor-titulo">Informações do vendedor</h2>

				<a
					href="${pageContext.request.contextPath}/perfil?id=${produto.produtorId}"
					class="vendedor-perfil">
					<c:choose>
						<c:when test="${not empty produto.produtorFotoUrl}">
							<img src="${pageContext.request.contextPath}/imagem/${produto.produtorFotoUrl}"
								alt="${produto.produtorNome}" class="vendedor-avatar-foto" />
						</c:when>
						<c:otherwise>
							<i class="bi bi-file-person vendedor-avatar"></i>
						</c:otherwise>
					</c:choose>
					<span class="vendedor-nome"> <c:out
							value="${produto.produtorNome}" />
					</span>
				</a>

				<p class="vendedor-desde">No Palma do Campo desde
					${dataCadastroFormatada}</p>

			</div>

			<!-- Botão de contato via WhatsApp -->
			<a
				href="https://wa.me/55${fn:replace(fn:replace(fn:replace(fn:replace(produto.produtorTelefone, '(', ''), ')', ''), '-', ''), ' ', '')}?text=Olá! Me interessei pelo seu ${produto.nome} anunciado no Palma do Campo. Ainda está disponível?"
				class="btn-whatsapp" target="_blank"> <i class="bi bi-whatsapp"></i>
				Enviar mensagem
			</a>
			<!-- Dica de segurança para o cliente -->
			<div class="dica-seguranca">
				<i class="bi bi-shield-check"></i>
				<p>
					<strong>Para sua segurança:</strong> prefira combinar a retirada ou
					entrega em local seguro e pague preferencialmente na hora de
					receber. Desconfie de pedidos de pagamento antecipado.
				</p>
			</div>
		</div>
	</div>


	<c:if test="${not empty listaFotos}">
		<script>
		// Única coisa que precisa do servidor: a lista de fotos deste produto
		const fotos = [
			<c:forEach var="foto" items="${listaFotos}" varStatus="s">"${pageContext.request.contextPath}/imagem/${foto}"<c:if test="${not s.last}">, </c:if></c:forEach>
		];
	</script>
		<script
			src="${pageContext.request.contextPath}/resources/js/detalhes.js">
		</script>
	</c:if>
	<jsp:include page="/WEB-INF/views/componentes/rodape.jsp" />
</body>
</html>