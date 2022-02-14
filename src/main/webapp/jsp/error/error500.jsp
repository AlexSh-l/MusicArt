<%--
  Created by IntelliJ IDEA.
  User: Алексей
  Date: 30.01.2022
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="ctg" uri="customtags" %>
<html>
<head>
    <title>500 Internal Server Error</title>
</head>
<body>
Request from = ${pageContext.errorData.requestURI} failed
<hr/>
Status = ${pageContext.errorData.statusCode}
<hr/>
Exception = ${pageContext.exception}
<hr/>
Servlet name = ${pageContext.errorData.servletName}
<hr/>
<a href="index.jsp">ToIndex</a>
<ctg:footertag/>
</body>
</html>
