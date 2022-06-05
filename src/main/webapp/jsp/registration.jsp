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
    <title><fmt:message key="registration.title"/></title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <div class="collapse navbar-collapse">
            <a class="navbar-brand mb-0 h1" href="${abs}/controller?command=to_main&page_number=1">MusicArt</a>
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
            <input type="hidden" name="page_number" value="1">
            <button class="btn btn-primary" type="submit"><fmt:message key="nav.to_catalog"/></button>
        </form>
    </div>
</nav>
<div class="d-flex justify-content-center">
    <form action="${abs}/controller" method="post">
        <label class="form-label"><fmt:message key="registration.name"/></label>
        <br><input type="text" class="form-control" name="name" pattern="[\w, '-]{1,100}">
        <br><label class="form-label"><fmt:message key="registration.login"/></label>
        <br><input type="text" class="form-control" name="login" pattern="[\w]{8,100}">
        <br><label class="form-label"><fmt:message key="registration.password"/></label>
        <br><input type="password" class="form-control" name="password"
                   pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,100}$" aria-describedby="passwordHelpBlock">
        <div id="passwordHelpBlock" class="form-text">
            Your password must be at least 8 characters long.
        </div>
        <br><label class="form-label"><fmt:message key="registration.confirm_password"/></label>
        <br><input type="password" class="form-control" name="confirm_password"
                   pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,100}$">
        <br><label class="form-label"><fmt:message key="registration.email"/></label>
        <br><input type="email" class="form-control" name="email" pattern="[\S]+\@[\S]+\.[\a-z]+">
        <br><label class="form-label"><fmt:message key="registration.phone"/></label>
        <br><input type="text" class="form-control" name="phone" pattern="\+[\d]{4,12}">
        <div class="mx-auto">
            <br>
            <div class="d-flex justify-content-center">
                <input type="hidden" name="command" value="register">
                <br>
                <button class="btn btn-primary" type="submit"><fmt:message key="registration.register"/></button>
            </div>
            <div class="d-flex justify-content-center">
                <br><label class="form-label">${registration_result}</label>
            </div>
        </div>
    </form>
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
