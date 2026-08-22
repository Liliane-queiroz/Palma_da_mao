
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="pt-br">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Entrar - Palma do Campo</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/global.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/login.css">

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

		<div class="lado-formulario">

			<div class="cadastro-cabecalho">

				<i class="bi bi-person-circle"></i>

				<h1>Entrar</h1>

			</div>


			<!-- Mensagem de erro -->
			<c:if test="${not empty erro}">

				<div class="erro">

					<strong>Erro:</strong> ${erro}

				</div>

			</c:if>


			<!-- Mensagem de sucesso após cadastro -->
			<c:if test="${param.cadastro == 'sucesso'}">

				<div class="sucesso">Conta criada com sucesso! Faça login para
					continuar.</div>

			</c:if>

			<form method="POST" action="${pageContext.request.contextPath}/login">

				<div class="form-group">

					<label for="email"> E-mail <span class="campo-obrigatorio">*</span>
					</label> <input type="email" id="email" name="email" required
						placeholder="voce@email.com">

				</div>

				<div class="form-group">

					<label for="senha"> Senha <span class="campo-obrigatorio">*</span>
					</label>

					<div class="campo-senha-wrapper">
						<input type="password" id="senha" name="senha" required
							placeholder="Sua senha"> <i
							class="bi bi-eye-slash toggle-senha" id="toggleSenha"></i>
					</div>

				</div>

				<button type="submit">

					<i class="bi bi-box-arrow-in-right"></i> Entrar

				</button>


				<div class="voltar">

					<a href="${pageContext.request.contextPath}/cadastro-conta">
						Não tem conta? Cadastre-se </a>

				</div>

			</form>

		</div>


		<div class="lado-imagem">

			<h2 class="boas-vindas">Conecte-se ao campo</h2>

			<img
				src="${pageContext.request.contextPath}/resources/images/logo/logo palma do campo 7.png"
				alt="Palma do Campo" class="logo-cadastro">


			<span class="nome-marca"> Palma do Campo </span>

		</div>

	</div>

	<script src="${pageContext.request.contextPath}/resources/js/login.js"></script>
</body>
</html>
