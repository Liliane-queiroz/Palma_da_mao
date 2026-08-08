<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Vitrine - Palma do Campo</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/global.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/vitrine.css">

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin="anonymous">
<link href="https://fonts.googleapis.com/css2?family=Righteous&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Inter&display=swap" rel="stylesheet">

</head>
<body>
    	<nav class="navbar-geral">
    		<div class="container-parte-busca">
        		<div class="logo">
            		<img src="${pageContext.request.contextPath}/resources/images/logo/logo palma do campo 4.png" alt="Logo Palma do Campo">
            		<span>Palma do Campo</span>
        		</div>
				
        		<div class="navbar-buscar-group">
            		<input type="text" class="buscar" placeholder="Buscar produtos">
            		<span class="separador"></span>
            		<div class="navbar-localizacao-wrapper"> 
            	  		<i class="bi bi-geo-alt"></i>
            			<select class="navbar-localizacao">
              				<option value="AC">AC</option>
                			<option value="AM">AM</option>
                			<option value="PA">PA</option>
                			<option value="RO">RO</option>
                			<option value="RR">RR</option>
               				<option value="TO">TO</option>
           				</select>
           			</div>
           				<span class="separador"></span>
           				<button class="btn-buscar">
                			<i class="bi bi-search"></i>
            			</button>
        		</div>
        		<div class="navbar-buttons">
            			<button class="btn-anunciar">Anunciar</button>
            			<button class="btn-meus-anuncios">Meus anúncios</button>
            			<button class="btn-menu-produtor">
                			Menu do produtor
                			<i class="bi bi-person-gear"></i>
            			</button>
        		</div>
			</div>
			<!-- onchange="if(this.value) window.location.href = this.value;"  (colocar no categoria-dropdown) -->
				       	
    		<div class="container-categorias">
        		<div class="esteira-categorias" id="esteiraCategorias">
        			<a href="#" class="categoria-link">Tudo</a>
        			<a href="#" class="categoria-link">Animais para Criação</a>
        			<a href="#" class="categoria-link">Artesanato Rural</a>
        			<a href="#" class="categoria-link">Carnes e Peixes</a>
        			<a href="#" class="categoria-link">Doces, Geleias e Conservas</a>
        			<a href="#" class="categoria-link">Flores, Plantas, Mudas e Sementes</a>
        			<a href="#" class="categoria-link">Frutas</a>
        			<a href="#" class="categoria-link">Grãos e Castanhas</a>
        			<a href="#" class="categoria-link">Leite, Queijos e Derivados</a>
        			<a href="#" class="categoria-link">Mel e Produtos da Colmeia</a>
        			<a href="#" class="categoria-link">Ovos</a>
        			<a href="#" class="categoria-link">Pães, Bolos e Massas</a>
        			<a href="#" class="categoria-link">Raízes e Batatas</a>
        			<a href="#" class="categoria-link">Serviços Rurais</a>
        			<a href="#" class="categoria-link">Temperos e Ervas</a>
        			<a href="#" class="categoria-link">Verduras e Legumes</a>
        		</div>
        		<button class="btn-seta-categorias btn-seta-esquerda" id="setaEsquerda" aria-label="Categorias anteriores">
       				<i class="bi bi-arrow-left-circle"></i>
   	 			</button>
    			<button class="btn-seta-categorias btn-seta-direita" id="setaDireita" aria-label="Ver mais categorias">
        			<i class="bi bi-arrow-right-circle"></i>
    			</button>
       		</div> 	
    	</nav>
            
       <div class="container-vitrine">
        	<c:choose>
            	<c:when test="${empty produtos}">
                	<p>Nenhum produto disponível no momento.</p>
            	</c:when>
            <c:otherwise>
                    <c:forEach var="produto" items="${produtos}">
				<div class="card-produto">
					<div class="img-card">
						<img src="${pageContext.request.contextPath}/resources/images/uploads/produtos/frango_italiano_teste.jpg" class="card-imagem" alt="${produto.nome}"/>
					</div>
					
						<h3 class="card-nome">
						<c:out value="${produto.nome}" />
					</h3>
					<p class="card-preco">
						R$
						<c:out value="${produto.precoEstimado}" />
					</p>
					<p class="card-categoria">
						<c:out value="${produto.categoriaDescricao}" />
					</p>
					<button class="btn-detalhes">Mais detalhes</button>
				</div>
				
					</c:forEach>
			</c:otherwise>
			</c:choose>
		</div>
	
	<!-- Menu Mobile -->
    <div class="navbar-mobile-menu">
        <a href="#" class="mobile-menu-item">
            <i class="bi bi-house-fill"></i>
            <span>Início</span>
        </a>
        <a href="#" class="mobile-menu-item">
            <i class="bi bi-plus-circle"></i>
            <span>Anunciar</span>
        </a>
        <a href="#" class="mobile-menu-item">
            <i class="bi bi-list"></i>
            <span>Menu</span>
        </a>	
	</div>
</body>
<script src="${pageContext.request.contextPath}/resources/js/global.js"></script>
</html>
