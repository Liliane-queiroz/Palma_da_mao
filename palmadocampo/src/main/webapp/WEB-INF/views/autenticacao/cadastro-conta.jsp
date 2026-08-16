<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Criar conta - Palma do Campo</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/global.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/cadastro-conta.css">

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin="anonymous">
<link href="https://fonts.googleapis.com/css2?family=Inter&family=Righteous&display=swap" rel="stylesheet">
</head>
<body>
    <div class="container">

        <!-- ===== LADO ESQUERDO: imagem + logo ===== -->
        <div class="lado-imagem">
            <h2 class="boas-vindas">Bem-vindo!</h2>
            <img src="${pageContext.request.contextPath}/resources/images/logo/logo palma do campo 7.png"
                 alt="Palma do Campo" class="logo-cadastro">
            <span class="nome-marca">Palma do Campo</span>
        </div>

        <!-- ===== LADO DIREITO: formulário ===== -->
        <div class="lado-formulario">

            <!-- Cabeçalho -->
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

            <!-- Formulário -->
            <form method="POST" action="${pageContext.request.contextPath}/cadastro-conta">

                <div class="form-group">
                    <label for="nome">Nome completo <span class="campo-obrigatorio">*</span></label>
                    <input type="text" id="nome" name="nome" required maxlength="150" placeholder="Digite seu nome">
                </div>

                <div class="form-group">
                    <label for="cpf">CPF <span class="campo-obrigatorio">*</span></label>
                    <input type="text" id="cpf" name="cpf" required maxlength="14" placeholder="000.000.000-00">
                </div>

                <div class="form-group">
                    <label for="senha">Senha <span class="campo-obrigatorio">*</span></label>
                    <input type="password" id="senha" name="senha" required minlength="6" placeholder="Digite sua senha">
                </div>

                <div class="form-group">
                    <label for="telefone">WhatsApp <span class="campo-obrigatorio">*</span></label>
                    <input type="text" id="telefone" name="telefone" required maxlength="20" placeholder="(69) 90000-0000">
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="cidade">Cidade <span class="campo-obrigatorio">*</span></label>
                        <input type="text" id="cidade" name="cidade" required maxlength="100" placeholder="Digite o nome da sua cidade">
                    </div>

                    <div class="form-group">
                        <label for="regiao">Estado <span class="campo-obrigatorio">*</span></label>
                        <select id="regiao" name="regiao" required>
                            <option value="">-- Escolha --</option>
                            <option value="Acre">Acre</option>
                            <option value="Amazonas">Amazonas</option>
                            <option value="Pará">Pará</option>
                            <option value="Rondônia">Rondônia</option>
                            <option value="Roraima">Roraima</option>
                            <option value="Tocantins">Tocantins</option>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label for="nomePropriedade">Nome da propriedade (opcional)</label>
                    <input type="text" id="nomePropriedade" name="nomePropriedade" maxlength="150" placeholder="Digite o nome da sua propriedade">
                </div>

                <button type="submit">
                    <i class="bi bi-check-lg"></i>
                    Criar conta
                </button>

                <div class="voltar">
                    <a href="${pageContext.request.contextPath}/login">Já tem conta? Entrar</a>
                </div>
            </form>
        </div>

    </div>

    <script src="${pageContext.request.contextPath}/resources/js/cadastro-produtor.js"></script>
</body>
</html>