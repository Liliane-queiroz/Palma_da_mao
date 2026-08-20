<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Perfil - Palma do Campo</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/global.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/cadastro-produto.css">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Inter&family=Righteous&display=swap" rel="stylesheet">
</head>
<body>
    <div class="container">

        <!-- Cabeçalho verde -->
        <div class="cadastro-cabecalho">
            <img src="${pageContext.request.contextPath}/resources/images/logo/logo palma do campo 7.png" alt="Logo" class="cadastro-logo">
            <h1>Editar Perfil</h1>
        </div>

        <!-- Formulário -->
        <form method="POST" action="${pageContext.request.contextPath}/editar-perfil">

            <!-- Nome da propriedade -->
            <div class="form-group">
                <label for="nomePropriedade">Nome da propriedade</label>
                <input type="text" id="nomePropriedade" name="nomePropriedade" maxlength="150"
                       placeholder="Sítio Boa Esperança" value="${produtor.nomePropriedade}">
            </div>

            <!-- Telefone -->
            <div class="form-group">
                <label for="telefone">Telefone / WhatsApp <span class="campo-obrigatorio">*</span></label>
                <input type="text" id="telefone" name="telefone" required maxlength="20"
                       placeholder="(69) 99999-9999" value="${produtor.telefone}">
            </div>

            <!-- Bio / Apresentação -->
            <div class="form-group">
                <label for="apresentacao">Sobre você e sua propriedade</label>
                <textarea id="apresentacao" name="apresentacao" maxlength="500" rows="5"
                          placeholder="Conte um pouco sobre você, sua propriedade e seus produtos...">${produtor.apresentacao}</textarea>
            </div>

            <!-- Botão de envio -->
            <button type="submit">
                <i class="bi bi-check-lg"></i>
                Salvar alterações
            </button>

            <!-- Link para voltar -->
            <div class="voltar">
                <a href="${pageContext.request.contextPath}/perfil?id=${produtor.id}">← Voltar ao perfil</a>
            </div>
        </form>
    </div>
	<script src="${pageContext.request.contextPath}/resources/js/cadastro-conta.js"></script>
</body>
</html>