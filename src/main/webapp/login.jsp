
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/bootstrap.min.js"></script>
</head>
<%
    if (session.getAttribute("auth") != null) {
        RequestDispatcher dispatcher = pageContext.getServletContext().getRequestDispatcher(ServletPath.INDEX);
        dispatcher.forward(request,response);
    }
%>
<body>
<%@include file="included/nav.jsp"%>
    <div class="container">
        <div class="card w-50 mx-auto my-5">
            <%
                Boolean result = (Boolean) session.getAttribute("error");
                if (result != null && result)
                {
                    %>
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <strong>Login Failed!</strong>
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    <%
                }
            %>
            <div class="card-header text-center">User Login</div>
            <div class="card-body">
                <form action = "${pageContext.servletContext.contextPath.concat("/auth/login")}" method="post">

                    <div class="form-group">
                        <label>Username</label>
                            <input type="text" class="form-control" name="username" required placeholder="Enter username" >
                    </div>

                    <div class="form-group">
                        <label>Password</label>
                        <input type="password" class="form-control" name="password" required placeholder="Enter Password" >
                    </div>

                    <div class="text-center">
                        <button type="submit" class="btn btn-primary">Login</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
