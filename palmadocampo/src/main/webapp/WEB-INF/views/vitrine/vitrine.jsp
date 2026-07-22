<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Vitrine - Palma do Campo</title>
</head>
<body>

    <h1>Palma do Campo</h1>
    <h2>Vitrine de Produtos</h2>

    <c:choose>
        <c:when test="${empty produtos}">
            <p>Nenhum produto disponível no momento.</p>
        </c:when>
        <c:otherwise>
            <ul>
                <c:forEach var="produto" items="${produtos}">
                    <li>
                        <strong><c:out value="${produto.nome}" /></strong>
                        - R$ <c:out value="${produto.precoEstimado}" />
                        - <c:out value="${produto.categoriaDescricao}" />
                    </li>
                </c:forEach>
            </ul>
        </c:otherwise>
    </c:choose>

</body>
</html>