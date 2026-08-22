<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Criar conta - Palma do Campo</title>
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/logo/logo palma do campo 7.png">

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/global.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/cadastro-conta.css">

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
	<div class="container">

		<div class="lado-imagem">
			<h2 class="boas-vindas">Bem-vindo!</h2>
			<img
				src="${pageContext.request.contextPath}/resources/images/logo/logo palma do campo 7.png"
				alt="Palma do Campo" class="logo-cadastro"> <span
				class="nome-marca">Palma do Campo</span>
		</div>

		<div class="lado-formulario">

			<div class="cadastro-cabecalho">
				<i class="bi bi-person-plus"></i>
				<h1>Criar conta de produtor</h1>
			</div>

			<!-- Mensagem de erro -->
			<c:if test="${not empty erro}">
				<div class="erro">
					<strong>Erro:</strong> ${erro}
				</div>
			</c:if>

			<form method="POST"
				action="${pageContext.request.contextPath}/cadastro-conta">

				<div class="form-group">
					<label for="nome">Nome completo <span
						class="campo-obrigatorio">*</span></label> <input type="text" id="nome"
						name="nome" required maxlength="150" placeholder="Digite seu nome">
				</div>

				<div class="form-group">
					<label for="cpf">CPF <span class="campo-obrigatorio">*</span></label>
					<input type="text" id="cpf" name="cpf" required maxlength="14"
						placeholder="000.000.000-00">
				</div>

				<div class="form-group">
					<label for="senha">Senha <span class="campo-obrigatorio">*</span></label>
					<input type="password" id="senha" name="senha" required
						minlength="6" placeholder="Digite sua senha">
				</div>

				<div class="form-group">
					<label for="email">E-mail <span class="campo-obrigatorio">*</span></label>
					<input type="email" id="email" name="email" required
						placeholder="voce@email.com">
				</div>

				<div class="form-group">
					<label for="telefone">WhatsApp <span
						class="campo-obrigatorio">*</span></label> <input type="text"
						id="telefone" name="telefone" required maxlength="20"
						placeholder="(69) 90000-0000">
				</div>

								<div class="form-row">
					<div class="form-group">
						<label for="cidade">Cidade <span class="campo-obrigatorio">*</span></label>
						<select id="cidade" name="cidade" required>
							<option value="">-- Escolha sua cidade --</option>
							<option value="Alta Floresta D'Oeste">Alta Floresta D'Oeste</option>
							<option value="Alto Alegre dos Parecis">Alto Alegre dos Parecis</option>
							<option value="Alto Paraíso">Alto Paraíso</option>
							<option value="Alvorada D'Oeste">Alvorada D'Oeste</option>
							<option value="Ariquemes">Ariquemes</option>
							<option value="Buritis">Buritis</option>
							<option value="Cabixi">Cabixi</option>
							<option value="Cacaulândia">Cacaulândia</option>
							<option value="Cacoal">Cacoal</option>
							<option value="Campo Novo de Rondônia">Campo Novo de Rondônia</option>
							<option value="Candeias do Jamari">Candeias do Jamari</option>
							<option value="Castanheiras">Castanheiras</option>
							<option value="Cerejeiras">Cerejeiras</option>
							<option value="Chupinguaia">Chupinguaia</option>
							<option value="Colorado do Oeste">Colorado do Oeste</option>
							<option value="Corumbiara">Corumbiara</option>
							<option value="Costa Marques">Costa Marques</option>
							<option value="Cujubim">Cujubim</option>
							<option value="Espigão D'Oeste">Espigão D'Oeste</option>
							<option value="Governador Jorge Teixeira">Governador Jorge Teixeira</option>
							<option value="Guajará-Mirim">Guajará-Mirim</option>
							<option value="Itapuã do Oeste">Itapuã do Oeste</option>
							<option value="Jaru">Jaru</option>
							<option value="Ji-Paraná">Ji-Paraná</option>
							<option value="Machadinho D'Oeste">Machadinho D'Oeste</option>
							<option value="Ministro Andreazza">Ministro Andreazza</option>
							<option value="Mirante da Serra">Mirante da Serra</option>
							<option value="Monte Negro">Monte Negro</option>
							<option value="Nova Brasilândia D'Oeste">Nova Brasilândia D'Oeste</option>
							<option value="Nova Mamoré">Nova Mamoré</option>
							<option value="Nova União">Nova União</option>
							<option value="Novo Horizonte do Oeste">Novo Horizonte do Oeste</option>
							<option value="Ouro Preto do Oeste">Ouro Preto do Oeste</option>
							<option value="Parecis">Parecis</option>
							<option value="Pimenta Bueno">Pimenta Bueno</option>
							<option value="Pimenteiras do Oeste">Pimenteiras do Oeste</option>
							<option value="Porto Velho">Porto Velho</option>
							<option value="Presidente Médici">Presidente Médici</option>
							<option value="Primavera de Rondônia">Primavera de Rondônia</option>
							<option value="Rio Crespo">Rio Crespo</option>
							<option value="Rolim de Moura">Rolim de Moura</option>
							<option value="Santa Luzia D'Oeste">Santa Luzia D'Oeste</option>
							<option value="São Felipe D'Oeste">São Felipe D'Oeste</option>
							<option value="São Francisco do Guaporé">São Francisco do Guaporé</option>
							<option value="São Miguel do Guaporé">São Miguel do Guaporé</option>
							<option value="Seringueiras">Seringueiras</option>
							<option value="Teixeirópolis">Teixeirópolis</option>
							<option value="Theobroma">Theobroma</option>
							<option value="Urupá">Urupá</option>
							<option value="Vale do Anari">Vale do Anari</option>
							<option value="Vale do Paraíso">Vale do Paraíso</option>
							<option value="Vilhena">Vilhena</option>
						</select>
					</div>

					<div class="form-group">
						<label for="regiao">Estado</label>
						<input type="text" id="regiao" name="regiao" value="Rondônia" readonly>
					</div>
				</div>

				<div class="form-group">
					<label for="nomePropriedade">Nome da propriedade (opcional)</label>
					<input type="text" id="nomePropriedade" name="nomePropriedade"
						maxlength="150" placeholder="Digite o nome da sua propriedade">
				</div>

				<button type="submit">
					<i class="bi bi-check-lg"></i> Criar conta
				</button>

				<div class="voltar">
					<a href="${pageContext.request.contextPath}/login">Já tem conta? Entrar</a>
				</div>
			</form>
		</div>

	</div>

		<script src="${pageContext.request.contextPath}/resources/js/cadastro-conta.js"></script>
</body>
</html>