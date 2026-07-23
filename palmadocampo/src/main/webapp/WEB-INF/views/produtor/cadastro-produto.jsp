<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Anunciar Produto - Palma do Campo</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            padding: 20px;
        }

        .container {
            max-width: 600px;
            margin: 0 auto;
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        h1 {
            color: #333;
            margin-bottom: 30px;
            text-align: center;
        }

        .form-group {
            margin-bottom: 20px;
            display: flex;
            flex-direction: column;
        }

        label {
            font-weight: bold;
            color: #333;
            margin-bottom: 5px;
            font-size: 14px;
        }

        input[type="text"],
        input[type="number"],
        input[type="date"],
        input[type="file"],
        select,
        textarea {
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            font-family: Arial, sans-serif;
        }

        input[type="text"]:focus,
        input[type="number"]:focus,
        input[type="date"]:focus,
        input[type="file"]:focus,
        select:focus,
        textarea:focus {
            outline: none;
            border-color: #4CAF50;
            box-shadow: 0 0 5px rgba(76, 175, 80, 0.3);
        }

        textarea {
            resize: vertical;
            min-height: 100px;
        }

        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 15px;
        }

        .form-group.full {
            grid-column: 1 / -1;
        }

        button {
            background-color: #4CAF50;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            font-weight: bold;
            margin-top: 20px;
            width: 100%;
        }

        button:hover {
            background-color: #45a049;
        }

        .erro {
            background-color: #f8d7da;
            color: #721c24;
            padding: 12px;
            border-radius: 4px;
            margin-bottom: 20px;
            border: 1px solid #f5c6cb;
        }

        .sucesso {
            background-color: #d4edda;
            color: #155724;
            padding: 12px;
            border-radius: 4px;
            margin-bottom: 20px;
            border: 1px solid #c3e6cb;
        }

        .voltar {
            text-align: center;
            margin-top: 20px;
        }

        .voltar a {
            color: #4CAF50;
            text-decoration: none;
            font-size: 14px;
        }

        .voltar a:hover {
            text-decoration: underline;
        }

        .campo-obrigatorio {
            color: red;
        }

        @media (max-width: 768px) {
            .form-row {
                grid-template-columns: 1fr;
            }

            .container {
                padding: 20px;
            }

            h1 {
                font-size: 24px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Anunciar Produto</h1>

        <!-- Exibir mensagens de erro se houver -->
        <c:if test="${not empty erro}">
            <div class="erro">
                <strong>Erro:</strong> ${erro}
            </div>
        </c:if>

        <!-- Exibir mensagem de sucesso se houver -->
        <c:if test="${not empty sucesso}">
            <div class="sucesso">
                <strong>Sucesso:</strong> ${sucesso}
            </div>
        </c:if>

        <!-- Formulário -->
        <form method="POST" action="${pageContext.request.contextPath}/cadastro-produto" enctype="multipart/form-data">

            <!-- Foto (obrigatória) -->
            <div class="form-group">
                <label for="arquivo">Foto do Produto <span class="campo-obrigatorio">*</span></label>
                <input type="file" id="arquivo" name="arquivo" accept="image/*" required>
                <small style="color: #666; margin-top: 5px;">Formatos aceitos: JPG, PNG, GIF. Máximo 5 MB.</small>
            </div>

            <!-- Nome (obrigatório) -->
            <div class="form-group">
                <label for="nome">Nome do Produto <span class="campo-obrigatorio">*</span></label>
                <input type="text" id="nome" name="nome" required maxlength="150">
            </div>

            <!-- Categoria (obrigatória) -->
            <div class="form-group">
                <label for="categoria">Categoria <span class="campo-obrigatorio">*</span></label>
                <select id="categoria" name="categoria" required>
                    <option value="">-- Escolha uma categoria --</option>
                    <c:forEach var="categoria" items="${categorias}">
                        <option value="${categoria.id}">${categoria.descricao}</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Descrição -->
            <div class="form-group">
                <label for="descricao">Descrição do Produto</label>
                <textarea id="descricao" name="descricao" maxlength="1000" placeholder="Descreva seu produto com detalhes..."></textarea>
            </div>

            <!-- Preço e Quantidade em linha -->
            <div class="form-row">
                <div class="form-group">
                    <label for="preco">Preço Estimado (R$)</label>
                    <input type="number" id="preco" name="preco" step="0.01" min="0" placeholder="0,00">
                    <small style="color: #666; margin-top: 5px;">Deixe vazio para "a combinar"</small>
                </div>

                <div class="form-group">
                    <label for="quantidade">Quantidade <span class="campo-obrigatorio">*</span></label>
                    <input type="number" id="quantidade" name="quantidade" step="0.01" min="0" required>
                </div>
            </div>

            <!-- Unidade e Data de Entrega em linha -->
            <div class="form-row">
                <div class="form-group">
                    <label for="unidade">Unidade <span class="campo-obrigatorio">*</span></label>
                    <select id="unidade" name="unidade" required>
                        <option value="">-- Escolha a unidade --</option>
                        <option value="UN">Unidade (UN)</option>
                        <option value="KG">Quilograma (KG)</option>
                        <option value="G">Grama (G)</option>
                        <option value="L">Litro (L)</option>
                        <option value="ML">Mililitro (ML)</option>
                        <option value="DZ">Dúzia (DZ)</option>
                        <option value="MACO">Maço (MACO)</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="dataEntrega">Data Prevista de Entrega</label>
                    <input type="date" id="dataEntrega" name="dataEntrega">
                </div>
            </div>

            <!-- Botão de envio -->
            <button type="submit">Publicar Anúncio</button>

            <!-- Link para voltar -->
            <div class="voltar">
                <a href="${pageContext.request.contextPath}/vitrine">← Voltar à vitrine</a>
            </div>
        </form>
    </div>
</body>
</html>