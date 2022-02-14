<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="ctg" uri="customtags" %>
<html>
<head>
    <title>404 Not Found</title>
</head>
<body>
Request from = ${pageContext.errorData.requestURI} failed
<br>Status = ${pageContext.errorData.statusCode}
<br>Exception = ${pageContext.exception}
<br>Servlet name = ${pageContext.errorData.servletName}
<br><a href="index.jsp">To main page</a>
<ctg:footertag/>
</body>
</html>
