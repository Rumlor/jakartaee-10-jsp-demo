<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.example.webappdemo.model.CartModel" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Optional" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    if (session.getAttribute("auth") == null){
        response.sendRedirect(ServletPath.ROOT + request.getContextPath().concat(ServletPath.LOGIN));
    }
    CartModel cartModel = (CartModel) session.getAttribute("cart");
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
    <%
        String infoAttr = (String) session.getAttribute("info");
        String errorAttr = (String) session.getAttribute("error");
        if ( infoAttr != null && !infoAttr.isEmpty()) {
    %>

    <div class="alert alert-info alert-dismissible fade show" role="alert">
        <strong><%=infoAttr%></strong>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>

    <%
        }
        if(errorAttr != null && !errorAttr.isEmpty()){
    %>
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <strong><%=errorAttr%></strong>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <%
        }
    %>
    <div class="d-flex py-3">
        <h3>Total Price: $<%=getPrice(cartModel)%></h3>
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

        <c:forEach items="${cart.productModelList}" var="item">
            <tr>
                <td><c:out value="${item.name}"/></td>
                <td><c:out value="${item.category}"/></td>
                <td>
                    <c:out value="\$${item.totalPrice}"/>
                </td>
                <td>
                    <input type="button" value="-" class="button-minus border rounded-circle  icon-shape icon-sm mx-1 " data-field="quantity">
                    <input type="text"  class= "amount-input" value="${item.count}">
                    <input type="button" value="+" class="button-minus border rounded-circle  icon-shape icon-sm mx-1 " data-field="quantity">
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/cart/delete?id=${item.productId}" class="btn btn-close"></a>
                </td>
            </tr>
        </c:forEach>

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
<%! private static BigDecimal getPrice(CartModel cartModel) {
    return Optional.ofNullable(cartModel)
            .map(cart->
            cart.getProductModelList().stream()
            .map(p->p.getPrice().multiply(BigDecimal.valueOf(p.getCount())))
            .reduce(BigDecimal.ZERO,BigDecimal::add)).orElse(BigDecimal.ZERO);
}
%>