<%--
  Created by IntelliJ IDEA.
  User: 201540
  Date: 4/11/2023
  Time: 10:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    if (session.getAttribute("auth") == null){
        RequestDispatcher dispatcher = pageContext.getServletContext().getRequestDispatcher(ServletPath.LOGIN);
        dispatcher.forward(request,response);
    }
%>
<head>
    <title>Cart</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<%@include file="included/nav.jsp"%>
<h1>Cart Page</h1>
</body>
</html>
