<%--
  Created by IntelliJ IDEA.
  User: Алексей
  Date: 13.02.2022
  Time: 12:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ctg" uri="customtags" %>
<c:set var="abs">${pageContext.request.contextPath}</c:set>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>All orders</title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <div class="collapse navbar-collapse">
            <a class="navbar-brand mb-0 h1" href="${abs}/controller?command=to_main">MusicArt</a>

            <form class="d-flex" action="${abs}/controller" method="get">
                <input type="hidden" name="command" value="item_search">
                <input class="form-control me-2" name="item_name" placeholder="Search"
                       aria-label="Search">
                <button class="btn btn-outline-primary" type="submit">Search</button>
            </form>
        </div>

        <form class="nav-link" action="${abs}/controller" method="get">
            <input type="hidden" name="command" value="sign_out">
            <button class="btn btn-primary" type="submit">Sign Out</button>
        </form>

    </div>
</nav>

<c:forEach var="order" items="${orders}">
    <div class="container">
        <div class="row">
            <div class="col">
                User login:
                <br>${order.user.login}
                <br>Name:
                <br>${order.user.name}
            </div>
            <div class="col">
                <form class="nav-link" action="${abs}/controller" method="get">
                    <input type="hidden" name="command" value="to_order_items">
                    <input type="hidden" name="order_id" value="${order.orderId}">
                    <button class="btn btn-primary" type="submit">Items</button>
                </form>
            </div>
            <div class="col">
                    ${order.address}
            </div>
            <div class="col">
                    ${order.status}
            </div>
            <div class="col">
                    ${order.timestamp}
            </div>
            <div class="col">
                    ${order.paymentType}
            </div>
            <div class="col">
                    ${order.price} Br
            </div>
            <div class="col">
                <form class="nav-link" action="${abs}/controller" method="get">
                    <input type="hidden" name="command" value="to_order_edit">
                    <input type="hidden" name="order_id" value="${order.orderId}">
                    <button class="btn btn-outline-primary" type="submit">Edit</button>
                </form>
            </div>
            <div class="col">
                <form class="nav-link" action="${abs}/controller" method="get">
                    <input type="hidden" name="command" value="delete_order">
                    <input type="hidden" name="order_id" value="${order.orderId}">
                    <button class="btn btn-danger" type="submit">Delete</button>
                </form>
            </div>
        </div>
        <br>${order_deletion_result}
        <br>
    </div>
</c:forEach>
<ctg:footertag/>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
        integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
        integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
        crossorigin="anonymous"></script>
</body>
</html>
