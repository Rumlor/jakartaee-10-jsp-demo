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
        response.sendRedirect(ServletPath.ROOT + request.getContextPath().concat(ServletPath.LOGIN));
    }
%>
<head>
    <title>Cart</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bootstrap.css">
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery-3.6.4.min.js"></script>
</head>
<body>
<%@include file="included/nav.jsp"%>
<h1>Cart Page</h1>
<div class="container">
    <div class="d-flex py-3">
        <h3>Total Price: $0</h3>
        <a class="mx-3 btn btn-primary" href="#">Check Out</a>
</div>

<table class="table table-light">
    <thead>
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Category</th>
            <th scope="col">Price</th>
            <th scope="col">Number</th>
            <th scope="col">Cancel</th>
        </tr>
    </thead>
    <tbody>
    <tr>
        <td>Product 1</td>
        <td>Shoes</td>
        <td>$451</td>
        <td>
            <input type="button" value="-" class="button-minus border rounded-circle  icon-shape icon-sm mx-1" data-field="quantity" onclick="decrement()">
            <input type="text" value="1" class="amount-input"/>
            <input type="button" value="+" class="button-minus border rounded-circle  icon-shape icon-sm mx-1" data-field="quantity" onclick="increment()">
        </td>
        <td>
            <a class="btn btn-sm btn-danger" href="#">Cancel</a>
        </td>
    </tr>
    </tbody>
</table>

</div>
</body>
<script>
    function increment(){
        console.log('inc')
        const element = $('input.amount-input');
        let value = element.val()
        value = Number(value) + 1;
        element.val(value)
    }
    function decrement(){
        console.log('dec')
        const element = $('input.amount-input');
        let value = element.val()
        const num = Number(value);
        if (num > 0)
            value = Number(value) - 1;
        element.val(value)
    }
</script>
</html>
