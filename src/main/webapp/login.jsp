<%--
  Created by IntelliJ IDEA.
  User: 201540
  Date: 4/11/2023
  Time: 10:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<%@include file="included/nav.jsp"%>
    <div class="container">
        <div class="card w-50 mx-auto my-5">
            <%
                Boolean result = (Boolean) session.getAttribute("error");
                if (result != null && result)
                {
            %>
            <div class="alert alert-danger" role="alert">
                Login Failed!
            </div>

            <% }
            %>
            <div class="card-header text-center">User Login</div>
            <div class="card-body">
                <form action="/login-servlet" method="post">

                    <div class="form-group">
                        <label>Email Address</label>
                        <input type="email" class="form-control" name="email" required placeholder="Enter Email" >
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
