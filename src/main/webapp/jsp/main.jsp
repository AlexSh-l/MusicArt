<%--
  Created by IntelliJ IDEA.
  User: Алексей
  Date: 30.01.2022
  Time: 14:29
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
    <title>Catalog</title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <div class="collapse navbar-collapse">
            <a class="navbar-brand mb-0 h1" href="${abs}/controller?command=to_main">MusicArt</a>
            <div class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    Category
                </a>
                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <li><a class="dropdown-item" href="${abs}/controller?command=to_main">None</a></li>
                    <li><a class="dropdown-item" href="#">Guitars</a></li>
                </ul>
            </div>
            <form class="d-flex" action="${abs}/controller" method="get">
                <input type="hidden" name="command" value="item_search">
                <input class="form-control me-2" name="item_name" placeholder="Search"
                       aria-label="Search">
                <button class="btn btn-outline-primary" type="submit">Search</button>
            </form>
        </div>
        <c:choose>
            <c:when test="${sign_in_result eq true}">
                <form class="nav-link" action="${abs}/controller" method="get">
                    <input type="hidden" name="command" value="sign_out">
                    <button class="btn btn-primary" type="submit">Sign Out</button>
                </form>
            </c:when>
            <c:otherwise>
                <form class="nav-link" action="${abs}/controller" method="get">
                    <input type="hidden" name="command" value="to_sign_in">
                    <button class="btn btn-primary" type="submit">Sign In</button>
                </form>
                <form class="nav-link" action="${abs}/controller" method="get">
                    <input type="hidden" name="command" value="to_registration">
                    <button class="btn btn-primary" type="submit">Register</button>
                </form>
            </c:otherwise>
        </c:choose>
    </div>
</nav>
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
