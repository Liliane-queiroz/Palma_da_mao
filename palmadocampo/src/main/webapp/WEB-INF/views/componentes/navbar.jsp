<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

	<nav class="navbar-geral">
    		<div class="container-parte-busca">
        		<div class="logo">
            		<img src="${pageContext.request.contextPath}/resources/images/logo/logo palma do campo 6.png" alt="Logo Palma do Campo">
            		<span>Palma do Campo</span>
        		</div>
				
        		<form class="navbar-buscar-group" action="${pageContext.request.contextPath}/buscar" method="get">
    				<input type="text" name="termo" class="buscar" placeholder="Buscar produtos">
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
           				<button type="submit" class="btn-buscar">
                			<i class="bi bi-search"></i>
            			</button>
        		</form>
        		<div class="navbar-buttons">
            			<button class="btn-anunciar">Anunciar</button>
            			<button class="btn-meus-anuncios">Meus anúncios</button>
            			<button class="btn-menu-produtor">
                			Menu do produtor
                			<i class="bi bi-person-gear"></i>
            			</button>
        		</div>
			</div>
				       	
    		<div class="container-categorias">
        		<div class="esteira-categorias" id="esteiraCategorias">
    				<a href="${pageContext.request.contextPath}/vitrine" class="categoria-link">Tudo</a>
    				<a href="${pageContext.request.contextPath}/categoria?nome=Animais para Criação" class="categoria-link">Animais para Criação</a>
    				<a href="${pageContext.request.contextPath}/categoria?nome=Artesanato Rural" class="categoria-link">Artesanato Rural</a>
    				<a href="${pageContext.request.contextPath}/categoria?nome=Carnes e Peixes" class="categoria-link">Carnes e Peixes</a>
    				<a href="${pageContext.request.contextPath}/categoria?nome=Doces, Geleias e Conservas" class="categoria-link">Doces, Geleias e Conservas</a>
    				<a href="${pageContext.request.contextPath}/categoria?nome=Flores, Plantas, Mudas e Sementes" class="categoria-link">Flores, Plantas, Mudas e Sementes</a>
    				<a href="${pageContext.request.contextPath}/categoria?nome=Frutas" class="categoria-link">Frutas</a>
    				<a href="${pageContext.request.contextPath}/categoria?nome=Grãos e Castanhas" class="categoria-link">Grãos e Castanhas</a>
    				<a href="${pageContext.request.contextPath}/categoria?nome=Leite, Queijos e Derivados" class="categoria-link">Leite, Queijos e Derivados</a>
    				<a href="${pageContext.request.contextPath}/categoria?nome=Mel e Produtos da Colmeia" class="categoria-link">Mel e Produtos da Colmeia</a>
    				<a href="${pageContext.request.contextPath}/categoria?nome=Ovos" class="categoria-link">Ovos</a>
    				<a href="${pageContext.request.contextPath}/categoria?nome=Pães, Bolos e Massas" class="categoria-link">Pães, Bolos e Massas</a>
    				<a href="${pageContext.request.contextPath}/categoria?nome=Raízes e Batatas" class="categoria-link">Raízes e Batatas</a>
    				<a href="${pageContext.request.contextPath}/categoria?nome=Serviços Rurais" class="categoria-link">Serviços Rurais</a>
    				<a href="${pageContext.request.contextPath}/categoria?nome=Temperos e Ervas" class="categoria-link">Temperos e Ervas</a>
    				<a href="${pageContext.request.contextPath}/categoria?nome=Verduras e Legumes" class="categoria-link">Verduras e Legumes</a>
</div>
        		<button class="btn-seta-categorias btn-seta-esquerda" id="setaEsquerda" aria-label="Categorias anteriores">
       				<i class="bi bi-arrow-left-circle-fill"></i>
   	 			</button>
    			<button class="btn-seta-categorias btn-seta-direita" id="setaDireita" aria-label="Ver mais categorias">
        			<i class="bi bi-arrow-right-circle-fill"></i>
    			</button>
       		</div> 	
    	</nav>

<script src="${pageContext.request.contextPath}/resources/js/global.js"></script>