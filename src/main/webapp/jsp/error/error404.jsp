<%--
  Created by IntelliJ IDEA.
  User: Алексей
  Date: 30.01.2022
  Time: 14:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>404 Not Found</title>
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
</body>
</html>
