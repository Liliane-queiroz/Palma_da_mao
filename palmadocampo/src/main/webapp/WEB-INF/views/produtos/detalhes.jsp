<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>${produto.nome} - Palma do Campo</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/global.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/detalhes.css">

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin="anonymous">
<link href="https://fonts.googleapis.com/css2?family=Inter&family=Righteous&display=swap" rel="stylesheet">
</head>
<body>
	<jsp:include page="/WEB-INF/views/componentes/navbar.jsp"/>  
    <div class="container-detalhes">

        <!-- LADO ESQUERDO: foto do produto com as setas (enfeite por enquanto) -->
        <div class="detalhes-foto">
            <button class="seta-foto seta-foto-esquerda" aria-label="Foto anterior">
                <i class="bi bi-arrow-left-circle"></i>
            </button>

			<img src="${pageContext.request.contextPath}/resources/images/uploads/produtos/frango_italiano_teste.jpg"
        		 class="detalhes-imagem-fundo" alt="" aria-hidden="true"/>
            <img src="${pageContext.request.contextPath}/resources/images/uploads/produtos/frango_italiano_teste.jpg"
                 class="detalhes-imagem" alt="${produto.nome}"/>
                 <!-- ${produto.fotoUrl} -->

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
                R$ <c:out value="${produto.precoEstimado}" />
            </p>

            <p class="detalhes-localizacao">
                <i class="bi bi-geo-alt"></i>
                Anunciado em <c:out value="${produto.produtorCidade}" />, <c:out value="${produto.produtorRegiao}" />
            </p>

            <div class="detalhes-descricao">
                <strong>Detalhes do produto:</strong>
                <p><c:out value="${produto.descricao}" /></p>
            </div>

            <div class="detalhes-vendedor">
                <h2 class="vendedor-titulo">Informações do vendedor</h2>

                <div class="vendedor-perfil">
                    <i class="bi bi-file-person vendedor-avatar"></i>
                    <span class="vendedor-nome">
                        <c:out value="${produto.produtorNome}" />
                    </span>
                </div>

                <p class="vendedor-desde">
                    No Palma do Campo desde de julho de 2026
                </p>

                <!-- Avaliação: estrelas FIXAS, só enfeite por enquanto -->
                <h3 class="vendedor-avaliacao-titulo">Avaliação de vendas</h3>
                <div class="vendedor-estrelas">
                    <i class="bi bi-star-fill"></i>
                    <i class="bi bi-star-fill"></i>
                    <i class="bi bi-star-fill"></i>
                    <i class="bi bi-star-half"></i>
                    <i class="bi bi-star"></i>
                </div>
            </div>

            <!-- Botão de contato via WhatsApp -->
            <button class="btn-whatsapp">
                <i class="bi bi-whatsapp"></i>
                Enviar mensagem
            </button>

        </div>
    </div>

</body>
</html>