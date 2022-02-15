<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ctg" uri="customtags" %>
<c:set var="abs">${pageContext.request.contextPath}</c:set>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="/localization/locale"/>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title><fmt:message key="item_edit.title"/></title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <div class="collapse navbar-collapse">
            <a class="navbar-brand mb-0 h1" href="${abs}/controller?command=to_main">MusicArt</a>
        </div>
        <div class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                <fmt:message key="nav.language"/>
            </a>
            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                <li>
                    <form class="dropdown-item" action="${abs}/controller" method="get">
                        <input type="hidden" name="command" value="change_locale">
                        <input type="hidden" name="language" value="en_EN">
                        <button class="btn btn-outline-primary" type="submit">English</button>
                    </form>
                </li>
                <li>
                    <form class="dropdown-item" action="${abs}/controller" method="get">
                        <input type="hidden" name="command" value="change_locale">
                        <input type="hidden" name="language" value="ru_RU">
                        <button class="btn btn-outline-primary" type="submit">Русский</button>
                    </form>
                </li>
            </ul>
        </div>
        <form class="nav-link" action="${abs}/controller" method="get">
            <input type="hidden" name="command" value="to_main">
            <button class="btn btn-primary" type="submit"><fmt:message key="nav.to_catalog"/></button>
        </form>
        <form class="nav-link" action="${abs}/controller" method="get">
            <input type="hidden" name="command" value="sign_out">
            <button class="btn btn-primary" type="submit"><fmt:message key="nav.sign_out"/></button>
        </form>
    </div>
</nav>
<div class="d-flex justify-content-center">
    <c:forEach var="item" items="${items}">
        <div class="container">
            <div class="row">
                <div class="col">
                    <br>${item.name}
                    <br>${item.description}
                    <br><fmt:message key="items.item_category"/>
                    <br>${item.category}
                    <br><fmt:message key="items.item_subcategory"/>
                    <br>${item.subcategory}
                    <br> ${item.price} <fmt:message key="add_item.item_price_currency"/>
                    <br><c:if test="${item.inStock eq true}"><fmt:message key="items.in_stock"/></c:if>
                    <br><c:if test="${item.inStock eq false}"><fmt:message key="items.sold_out"/></c:if>
                    <br>
                    <form class="col" action="${abs}/controller" method="get">
                        <input type="hidden" name="command" value="delete_item">
                        <button class="btn btn-danger" type="submit"><fmt:message key="item_edit.delete"/></button>
                    </form>
                </div>
                <form class="col" action="${abs}/controller" method="post">
                    <label><fmt:message key="add_item.item_name"/>
                        <br><input type="text" name="item_name" pattern="[\w, '-]{1,100}">
                    </label>
                    <br><label><fmt:message key="add_item.item_description"/>
                    <br><input type="text" name="item_description" pattern="[\w, '-]{1,1000}">
                </label>
                    <br><label><fmt:message key="add_item.item_category"/>
                    <br><input type="text" name="item_category" pattern="[\w, '-]{1,100}">
                </label>
                    <br><label><fmt:message key="add_item.item_subcategory"/>
                    <br><input type="text" name="item_subcategory" pattern="[\w, '-]{1,100}">
                </label>
                    <br><label><fmt:message key="add_item.item_price"/>
                    <br><input type="text" name="item_price"> <fmt:message key="add_item.item_price_currency"/>
                </label>
                    <br><label><fmt:message key="add_item.item_stock"/>
                    <br>
                    <div class="form-check form-switch">
                        <input class="form-check-input" type="checkbox" id="flexSwitchCheckChecked" name="item_in_stock"
                               checked>
                        <label class="form-check-label" for="flexSwitchCheckChecked"><fmt:message
                                key="add_item.item_is_in_stock"/></label>
                    </div>
                </label>
                    <div class="mx-auto">
                        <div>
                            <input type="hidden" name="command" value="edit_item">
                            <br>
                            <button class="btn btn-primary" type="submit"><fmt:message key="items.edit"/></button>
                        </div>
                    </div>
                </form>
            </div>
            <br>
        </div>
    </c:forEach>
</div>
<ctg:footertag/>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
        integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
        integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
        crossorigin="anonymous"></script>
</body>
</html>
