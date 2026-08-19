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
	<jsp:include page="/WEB-INF/views/componentes/navbar.jsp" />
	<div class="container-detalhes">

		<!-- LADO ESQUERDO: foto do produto com as setas (enfeite por enquanto) -->
		<div class="detalhes-foto">
			<c:set var="primeiraFoto"
				value="${not empty produto.fotoUrl ? fn:split(produto.fotoUrl, ',')[0] : ''}" />
			<button class="seta-foto seta-foto-esquerda"
				aria-label="Foto anterior">
				<i class="bi bi-arrow-left-circle"></i>
			</button>

			<img src="${pageContext.request.contextPath}/imagem/${primeiraFoto}"
				class="detalhes-imagem-fundo" alt="" aria-hidden="true" /> <img
				src="${pageContext.request.contextPath}/imagem/${primeiraFoto}"
				class="detalhes-imagem" alt="${produto.nome}" />

			<button class="seta-foto seta-foto-direita" aria-label="Próxima foto">
				<i class="bi bi-arrow-right-circle"></i>
			</button>
		</div>

		<!-- LADO DIREITO: painel creme com todas as informações -->
		<div class="detalhes-painel">

			<h1 class="detalhes-nome">
				<c:out value="${produto.nome}" />
			</h1>

			<p class="detalhes-preco">
				<fmt:formatNumber value="${produto.precoEstimado}" type="currency"
					currencySymbol="R$ " />
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

			<div class="detalhes-vendedor">
				<h2 class="vendedor-titulo">Informações do vendedor</h2>

				<a
					href="${pageContext.request.contextPath}/perfil?id=${produto.produtorId}"
					class="vendedor-perfil"> <i
					class="bi bi-file-person vendedor-avatar"></i> <span
					class="vendedor-nome"> <c:out
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

		</div>
	</div>

</body>
</html>