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
    <title><fmt:message key="catalog.title"/></title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <div class="collapse navbar-collapse">
            <a class="navbar-brand mb-0 h1" href="${abs}/controller?command=to_main&page_number=1">MusicArt</a>
            <form class="d-flex" action="${abs}/controller" method="get">
                <input type="hidden" name="command" value="item_search">
                <input class="form-control me-2" name="item_name" placeholder="<fmt:message key="nav.search"/>"
                       aria-label="Search">
                <button class="btn btn-outline-primary" type="submit"><fmt:message key="nav.search"/></button>
            </form>
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
        <c:choose>
            <c:when test="${sign_in_result eq true}">
                <c:if test="${user.role eq 'CLIENT'}">
                    <div class="nav-link">
                        <a class="navbar-brand mb-0 h1" href="${abs}/controller?command=to_cart">${cart.cartSize}
                            <fmt:message key="nav.cart"/></a>
                    </div>
                </c:if>
                <c:if test="${user.role eq 'ADMIN'}">
                    <form class="nav-link" action="${abs}/controller" method="get">
                        <input type="hidden" name="command" value="to_orders">
                        <button class="btn btn-primary" type="submit"><fmt:message key="nav.show_orders"/></button>
                    </form>
                    <form class="nav-link" action="${abs}/controller" method="get">
                        <input type="hidden" name="command" value="to_add_item">
                        <button class="btn btn-primary" type="submit"><fmt:message key="nav.add_new_item"/></button>
                    </form>
                </c:if>
                <form class="nav-link" action="${abs}/controller" method="get">
                    <input type="hidden" name="command" value="sign_out">
                    <button class="btn btn-primary" type="submit"><fmt:message key="nav.sign_out"/></button>
                </form>
            </c:when>
            <c:otherwise>
                <form class="nav-link" action="${abs}/controller" method="get">
                    <input type="hidden" name="command" value="to_sign_in">
                    <button class="btn btn-primary" type="submit"><fmt:message key="nav.sign_in"/></button>
                </form>
                <form class="nav-link" action="${abs}/controller" method="get">
                    <input type="hidden" name="command" value="to_registration">
                    <button class="btn btn-primary" type="submit"><fmt:message key="nav.register"/></button>
                </form>
            </c:otherwise>
        </c:choose>
    </div>
</nav>
<div class="container">
    <div class="d-flex flex-wrap">
        <c:forEach var="item" items="${items}">
            <div class="card" style="width: 17rem;">
                <c:choose>
                    <c:when test="${item.imagePath eq ''}">
                        <img src="${abs}/images/default_image.jpg" style='width: 100%'
                             class="card-img-top" alt=""/>
                    </c:when>
                    <c:otherwise>
                        <img src="${item.imagePath}" style='width: 100%'
                             class="card-img-top" alt=""/>
                    </c:otherwise>
                </c:choose>
                <div class="card-body">
                        ${item.name}<br>
                    <br>Category: ${item.category}
                    <br>Subcategory: ${item.subcategory}<br>
                    <br>${item.description}<br>
                    <br>${item.price} <fmt:message key="add_item.item_price_currency"/><br>
                    <c:if test="${item.inStock eq true}"><fmt:message key="items.in_stock"/></c:if>
                    <c:if test="${item.inStock eq false}"><fmt:message key="items.sold_out"/></c:if>
                    <c:if test="${sign_in_result eq true and item.inStock eq true and user.role eq 'CLIENT'}">
                        <form class="nav-link" action="${abs}/controller" method="get">
                            <input type="hidden" name="command" value="add_to_cart">
                            <input type="hidden" name="item_id" value="${item.itemId}">
                            <button class="btn btn-primary" type="submit"><fmt:message
                                    key="items.add_to_cart"/></button>
                        </form>
                    </c:if>
                    <c:if test="${sign_in_result eq true and user.role eq 'ADMIN'}">
                        <form class="nav-link" action="${abs}/controller" method="get">
                            <input type="hidden" name="command" value="to_item_edit">
                            <input type="hidden" name="item_id" value="${item.itemId}">
                            <button class="btn btn-outline-primary" type="submit"><fmt:message
                                    key="items.edit"/></button>
                        </form>
                        <br>
                        <form action="${abs}/controller" method="post" enctype="multipart/form-data">
                            <label>item image
                                <br><input type="file" name="img" accept="image/*">
                            </label>
                            <input type="hidden" name="command" value="upload_images">
                            <input type="hidden" name="item_id" value="${item.itemId}">
                            <button class="btn btn-primary" type="submit">Upload</button>
                        </form>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </div>
    <br>
</div>
<ctg:pagination page_number="${current_page_number}" next_pages="${next_pages}" url="${abs}/controller?command="/>
<ctg:footertag/>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
        integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
        integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
        crossorigin="anonymous"></script>
</body>
</html>
