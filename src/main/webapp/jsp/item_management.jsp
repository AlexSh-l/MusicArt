<%--
  Created by IntelliJ IDEA.
  User: Алексей
  Date: 07.02.2022
  Time: 22:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="abs">${pageContext.request.contextPath}</c:set>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Manage store items</title>
</head>
<body>

<c:forEach var="item" items="${items}">
<div class="container">
    <div class="row">
        <div class="col">
            ${item.name}
        </div>
        <div class="col">
            ${item.description}
        </div>
        <div class="col">
            Category:
            <br>${item.category}
            <br>Subcategory:
            <br>${item.subcategory}
        </div>
        <div class="col">
            ${item.price} Br
        </div>
        <div class="col">
            <c:if test="${item.inStock eq true}">Is in stock</c:if>
            <c:if test="${item.inStock eq false}">Sold out</c:if>
        </div>
        <div class="col">
            <button type="button" class="btn btn-outline-primary">Изменить</button>
        </div>
    </div>
</div>
</c:forEach>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
        integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
        integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
        crossorigin="anonymous"></script>
</body>
</html>
