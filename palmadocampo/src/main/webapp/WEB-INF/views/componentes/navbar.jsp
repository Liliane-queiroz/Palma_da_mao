<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/menu-produtor.css">

<nav class="navbar-geral">
	<div class="container-parte-busca">
		<div class="logo">
			<img
				src="${pageContext.request.contextPath}/resources/images/logo/logo palma do campo 7.png"
				alt="Logo Palma do Campo"> <span>Palma do Campo</span>
		</div>

		<form class="navbar-buscar-group"
			action="${pageContext.request.contextPath}/buscar" method="get">
			<input type="text" name="termo" class="buscar"
				placeholder="Buscar produtos"> <span class="separador"></span>
			<div class="navbar-localizacao-wrapper">
				<i class="bi bi-geo-alt"></i> <select class="navbar-localizacao"
					name="cidade" id="selectCidade">
					<option value="">Todas as cidades</option>
					<option value="Alta Floresta D'Oeste">Alta Floresta
						D'Oeste</option>
					<option value="Alto Alegre dos Parecis">Alto Alegre dos
						Parecis</option>
					<option value="Alto Paraíso">Alto Paraíso</option>
					<option value="Alvorada D'Oeste">Alvorada D'Oeste</option>
					<option value="Ariquemes">Ariquemes</option>
					<option value="Buritis">Buritis</option>
					<option value="Cabixi">Cabixi</option>
					<option value="Cacaulândia">Cacaulândia</option>
					<option value="Cacoal">Cacoal</option>
					<option value="Campo Novo de Rondônia">Campo Novo de
						Rondônia</option>
					<option value="Candeias do Jamari">Candeias do Jamari</option>
					<option value="Castanheiras">Castanheiras</option>
					<option value="Cerejeiras">Cerejeiras</option>
					<option value="Chupinguaia">Chupinguaia</option>
					<option value="Colorado do Oeste">Colorado do Oeste</option>
					<option value="Corumbiara">Corumbiara</option>
					<option value="Costa Marques">Costa Marques</option>
					<option value="Cujubim">Cujubim</option>
					<option value="Espigão D'Oeste">Espigão D'Oeste</option>
					<option value="Governador Jorge Teixeira">Governador Jorge
						Teixeira</option>
					<option value="Guajará-Mirim">Guajará-Mirim</option>
					<option value="Itapuã do Oeste">Itapuã do Oeste</option>
					<option value="Jaru">Jaru</option>
					<option value="Ji-Paraná">Ji-Paraná</option>
					<option value="Machadinho D'Oeste">Machadinho D'Oeste</option>
					<option value="Ministro Andreazza">Ministro Andreazza</option>
					<option value="Mirante da Serra">Mirante da Serra</option>
					<option value="Monte Negro">Monte Negro</option>
					<option value="Nova Brasilândia D'Oeste">Nova Brasilândia
						D'Oeste</option>
					<option value="Nova Mamoré">Nova Mamoré</option>
					<option value="Nova União">Nova União</option>
					<option value="Novo Horizonte do Oeste">Novo Horizonte do
						Oeste</option>
					<option value="Ouro Preto do Oeste">Ouro Preto do Oeste</option>
					<option value="Parecis">Parecis</option>
					<option value="Pimenta Bueno">Pimenta Bueno</option>
					<option value="Pimenteiras do Oeste">Pimenteiras do Oeste</option>
					<option value="Porto Velho">Porto Velho</option>
					<option value="Presidente Médici">Presidente Médici</option>
					<option value="Primavera de Rondônia">Primavera de
						Rondônia</option>
					<option value="Rio Crespo">Rio Crespo</option>
					<option value="Rolim de Moura">Rolim de Moura</option>
					<option value="Santa Luzia D'Oeste">Santa Luzia D'Oeste</option>
					<option value="São Felipe D'Oeste">São Felipe D'Oeste</option>
					<option value="São Francisco do Guaporé">São Francisco do
						Guaporé</option>
					<option value="São Miguel do Guaporé">São Miguel do
						Guaporé</option>
					<option value="Seringueiras">Seringueiras</option>
					<option value="Teixeirópolis">Teixeirópolis</option>
					<option value="Theobroma">Theobroma</option>
					<option value="Urupá">Urupá</option>
					<option value="Vale do Anari">Vale do Anari</option>
					<option value="Vale do Paraíso">Vale do Paraíso</option>
					<option value="Vilhena">Vilhena</option>
				</select>
			</div>
			<span class="separador"></span>
			<button type="submit" class="btn-buscar">
				<i class="bi bi-search"></i>
			</button>
		</form>
		<div class="navbar-buttons">
			<c:choose>
				<c:when test="${not empty sessionScope.usuarioLogado}">
					<!-- PRODUTOR LOGADO -->
					<a href="${pageContext.request.contextPath}/cadastro-produto"
						class="btn-anunciar">Anunciar</a>
					<a href="${pageContext.request.contextPath}/meus-anuncios"
						class="btn-meus-anuncios">Meus anúncios</a>
					<div class="menu-produtor-container">
						<button class="btn-menu-produtor">
							<i class="bi bi-person-circle"></i> Olá,
							${sessionScope.usuarioLogado.nome} <i class="bi bi-chevron-down"></i>
						</button>

						<div class="dropdown-menu-produtor" id="dropdownMenuProdutor">
							<div class="menu-header">
								<img
									src="${pageContext.request.contextPath}/resources/images/logo/logo palma do campo 7.png"
									alt="Logo" class="logo-pequena">
								<button class="btn-fechar-menu" id="btnFecharMenu">
									<i class="bi bi-x"></i>
								</button>
							</div>

							<div class="menu-produtor">
								<div class="produtor-info">
									<div class="avatar-produtor">
										<i class="bi bi-person-circle"></i>
									</div>
									<div class="produtor-dados">
										<p class="nome-produtor">${sessionScope.usuarioLogado.nome}</p>
										<a
											href="${pageContext.request.contextPath}/perfil?id=${sessionScope.usuarioLogado.id}"
											class="link-perfil">Meu perfil</a>
									</div>
								</div>

								<div class="secao-menu">
									<h4 class="secao-titulo">MEUS PRODUTOS</h4>
									<a href="${pageContext.request.contextPath}/cadastro-produto"
										class="menu-item"> <i class="bi bi-plus-circle"></i>
										Anunciar produto
									</a> <a href="${pageContext.request.contextPath}/meus-anuncios"
										class="menu-item"> <i class="bi bi-file-text"></i> Meus
										anúncios
									</a>
								</div>

								<div class="secao-menu">
									<h4 class="secao-titulo">CONTA</h4>
									<a href="${pageContext.request.contextPath}/editar-perfil"
										class="menu-item"> <i class="bi bi-gear"></i> Editar dados
									</a> <a href="${pageContext.request.contextPath}/logout"
										class="menu-item menu-item-sair"> <i
										class="bi bi-box-arrow-right"></i> Sair
									</a>
								</div>
							</div>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<!-- VISITANTE (não logado) -->
					<a href="${pageContext.request.contextPath}/login"
						class="btn-anunciar">Anunciar</a>
					<a href="${pageContext.request.contextPath}/login"
						class="btn-entrar">Entrar</a>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<div class="container-categorias">
		<div class="esteira-categorias" id="esteiraCategorias">
			<a href="${pageContext.request.contextPath}/vitrine"
				class="categoria-link">Tudo</a> <a
				href="${pageContext.request.contextPath}/categoria?nome=Animais para Criação"
				class="categoria-link">Animais para Criação</a> <a
				href="${pageContext.request.contextPath}/categoria?nome=Artesanato Rural"
				class="categoria-link">Artesanato Rural</a> <a
				href="${pageContext.request.contextPath}/categoria?nome=Carnes e Peixes"
				class="categoria-link">Carnes e Peixes</a> <a
				href="${pageContext.request.contextPath}/categoria?nome=Doces, Geleias e Conservas"
				class="categoria-link">Doces, Geleias e Conservas</a> <a
				href="${pageContext.request.contextPath}/categoria?nome=Flores, Plantas, Mudas e Sementes"
				class="categoria-link">Flores, Plantas, Mudas e Sementes</a> <a
				href="${pageContext.request.contextPath}/categoria?nome=Frutas"
				class="categoria-link">Frutas</a> <a
				href="${pageContext.request.contextPath}/categoria?nome=Grãos e Castanhas"
				class="categoria-link">Grãos e Castanhas</a> <a
				href="${pageContext.request.contextPath}/categoria?nome=Leite, Queijos e Derivados"
				class="categoria-link">Leite, Queijos e Derivados</a> <a
				href="${pageContext.request.contextPath}/categoria?nome=Mel e Produtos da Colmeia"
				class="categoria-link">Mel e Produtos da Colmeia</a> <a
				href="${pageContext.request.contextPath}/categoria?nome=Ovos"
				class="categoria-link">Ovos</a> <a
				href="${pageContext.request.contextPath}/categoria?nome=Pães, Bolos e Massas"
				class="categoria-link">Pães, Bolos e Massas</a> <a
				href="${pageContext.request.contextPath}/categoria?nome=Raízes e Batatas"
				class="categoria-link">Raízes e Batatas</a> <a
				href="${pageContext.request.contextPath}/categoria?nome=Serviços Rurais"
				class="categoria-link">Serviços Rurais</a> <a
				href="${pageContext.request.contextPath}/categoria?nome=Temperos e Ervas"
				class="categoria-link">Temperos e Ervas</a> <a
				href="${pageContext.request.contextPath}/categoria?nome=Verduras e Legumes"
				class="categoria-link">Verduras e Legumes</a>
		</div>
		<button class="btn-seta-categorias btn-seta-esquerda"
			id="setaEsquerda" aria-label="Categorias anteriores">
			<i class="bi bi-arrow-left-circle-fill"></i>
		</button>
		<button class="btn-seta-categorias btn-seta-direita" id="setaDireita"
			aria-label="Ver mais categorias">
			<i class="bi bi-arrow-right-circle-fill"></i>
		</button>
	</div>
	<div class="menu-overlay" id="menuOverlay"></div>
</nav>

<script src="${pageContext.request.contextPath}/resources/js/global.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/menu-produtor.js"></script>
<script>
	// Marca a cidade selecionada no dropdown após a busca
	var cidadeSelecionada = "${cidadeBuscada}";
	if (cidadeSelecionada) {
		document.getElementById("selectCidade").value = cidadeSelecionada;
	}
</script>	