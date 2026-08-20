<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Como funciona - Palma do Campo</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/global.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/como-funciona.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin="anonymous">
<link href="https://fonts.googleapis.com/css2?family=Inter&family=Righteous&display=swap" rel="stylesheet">
</head>
<body>
	<jsp:include page="/WEB-INF/views/componentes/navbar.jsp" />

	<div class="container-como-funciona">

		<!-- Seção 1: O que é -->
		<section class="cf-hero">
			<h1>Como funciona a Palma do Campo</h1>
			<p class="cf-subtitulo">
				Uma vitrine digital que conecta produtores rurais diretamente a quem
				quer comprar produtos frescos e locais — sem intermediários, sem
				complicação.
			</p>
		</section>

		<!-- Seção 2: Para o cliente -->
		<section class="cf-secao">
			<h2><i class="bi bi-cart-check"></i> Para quem quer comprar</h2>
			<p class="cf-intro">
				Encontre produtos direto de quem planta e cria, com fotos reais e a
				procedência de cada produtor.
			</p>
			<div class="cf-passos">
				<div class="cf-passo">
					<span class="cf-numero">1</span>
					<h3>Navegue pelos produtos</h3>
					<p>Explore a vitrine por categoria ou busque o que procura.</p>
				</div>
				<div class="cf-passo">
					<span class="cf-numero">2</span>
					<h3>Veja os detalhes</h3>
					<p>Fotos reais, preço, localização e informações do produtor.</p>
				</div>
				<div class="cf-passo">
					<span class="cf-numero">3</span>
					<h3>Converse no WhatsApp</h3>
					<p>Fale direto com o produtor, sem intermediários.</p>
				</div>
				<div class="cf-passo">
					<span class="cf-numero">4</span>
					<h3>Combine tudo</h3>
					<p>Preço, quantidade e entrega são combinados diretamente com ele.</p>
				</div>
			</div>
		</section>

		<!-- Seção 3: Para o produtor -->
		<section class="cf-secao cf-secao-alt">
			<h2><i class="bi bi-shop"></i> Para quem quer vender</h2>
			<p class="cf-intro">
				Simples de usar, mesmo para quem não tem intimidade com tecnologia.
				Seus produtos organizados num só lugar, aparecendo para novos clientes.
			</p>
			<div class="cf-passos">
				<div class="cf-passo">
					<span class="cf-numero">1</span>
					<h3>Crie sua conta</h3>
					<p>Cadastro rápido e gratuito. Anunciar não custa nada.</p>
				</div>
				<div class="cf-passo">
					<span class="cf-numero">2</span>
					<h3>Anuncie seus produtos</h3>
					<p>Adicione fotos, descrição e o preço que você definir.</p>
				</div>
				<div class="cf-passo">
					<span class="cf-numero">3</span>
					<h3>Receba contatos</h3>
					<p>Clientes falam com você direto pelo WhatsApp.</p>
				</div>
				<div class="cf-passo">
					<span class="cf-numero">4</span>
					<h3>Negocie sem atravessador</h3>
					<p>O preço é seu. Venda direto e receba um valor melhor pela sua produção.</p>
				</div>
			</div>
		</section>

		<!-- Seção 4: Transparência -->
		<section class="cf-secao">
			<h2><i class="bi bi-shield-check"></i> Contato direto, do seu jeito</h2>
			<p class="cf-texto-transparencia">
				A Palma do Campo é uma vitrine: mostramos os produtos e conectamos
				você diretamente ao produtor. A negociação, o pagamento e a entrega
				são combinados entre vocês, pelo WhatsApp. Isso dá mais autonomia ao
				produtor e um contato próximo e transparente para o cliente — do jeito
				que já acontece na feira, agora também pela internet.
			</p>
			<div class="cf-cta">
				<a href="${pageContext.request.contextPath}/vitrine" class="cf-btn-primario">
					Ver produtos
				</a>
				<a href="${pageContext.request.contextPath}/cadastro-conta" class="cf-btn-secundario">
					Criar conta de produtor
				</a>
			</div>
		</section>

	</div>

	<jsp:include page="/WEB-INF/views/componentes/rodape.jsp" />
</body>
</html>