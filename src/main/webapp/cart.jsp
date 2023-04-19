<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

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


    <c:if test="${cartBean.hasInfo}">
        <div class="alert alert-info alert-dismissible fade show" role="alert">
            <strong><c:out value="${cartBean.info}"/></strong>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <c:if test="${cartBean.hasError}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <strong><c:out value="${cartBean.error}"/></strong>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <div class="d-flex py-3">
        <h3>Total Price: $<c:out value="${cartBean.cartTotalPrice}"/></h3>
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

        <c:forEach items="${cartBean.products}" var="item">
            <tr>
                <td><c:out value="${item.name}"/></td>
                <td><c:out value="${item.category}"/></td>
                <td><c:out value="\$${item.totalPrice}"/></td>
                <td>
                    <a class="mx-3 btn btn-primary" href="${pageContext.request.contextPath}/cart/remove?id=${item.productId}">-</a>
                    <input type="text"  class= "amount-input" value="${item.count}"/>
                    <a class="mx-3 btn btn-primary" href="${pageContext.request.contextPath}/cart/add?id=${item.productId}&inCart=true">+</a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/cart/delete?id=${item.productId}" class="btn btn-close"></a>
                </td>
            </tr>
        </c:forEach>
        <c:out value="${cartBean.resetInfoAndError()}"/>
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
