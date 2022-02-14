<%--
  Created by IntelliJ IDEA.
  User: Алексей
  Date: 12.02.2022
  Time: 13:13
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
    <title>Order</title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <div class="collapse navbar-collapse">
            <a class="navbar-brand mb-0 h1" href="${abs}/controller?command=to_main">MusicArt</a>
        </div>
        <form class="nav-link" action="${abs}/controller" method="get">
            <input type="hidden" name="command" value="to_main">
            <button class="btn btn-primary" type="submit">To catalog</button>
        </form>
        <form class="nav-link" action="${abs}/controller" method="get">
            <input type="hidden" name="command" value="sign_out">
            <button class="btn btn-primary" type="submit">Sign Out</button>
        </form>
    </div>
</nav>
<div class="d-flex justify-content-center">
    <form action="${abs}/controller" method="post">
        <label>Please, enter address:
            <br><input type="text" name="address">
        </label>
        <br>
        <br>
        <label>
            <strong>Способ оплаты:</strong>
        </label>
        <br><label>
        <input name="payment_type" type="radio" value="card"> Картой
    </label>
        <br><label>
        <input name="payment_type" type="radio" value="cash" checked> Наличными
    </label>
        <div class="mx-auto">
            <input type="hidden" name="command" value="confirm_order">
            <br>
            <button class="btn btn-primary" type="submit">Order</button>
            <c:choose>
                <c:when test="${order_result eq true}">
                    <br><label>Заказ оформлен</label>
                </c:when>
                <c:otherwise>
                    <br><label>${order_result}</label>
                </c:otherwise>
            </c:choose>
        </div>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
        integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
        integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
        crossorigin="anonymous"></script>
</body>
</html>