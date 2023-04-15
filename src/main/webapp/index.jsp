
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html>
<head>
    <title>Index</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<%@include file="included/nav.jsp"%>
    <div class="container">
        <div class="card-header my-3">All Products</div>
        <div class="row">
                <c:forEach items="${productBean.allProducts}" var="product">
                    <div class="col-md-3">
                        <div class="card w-100" style="width: 18rem;">
                            <img src="${pageContext.servletContext.contextPath.concat(product.imagePath)}" class="card-img-top">
                            <div class="card-body">
                                <h5 class="card-title"><c:out value="${product.name}"/> </h5>
                                <h6>Price: $<c:out value="${product.price}"/></h6>
                                <h6>Category: <c:out value="${product.category}"/></h6>
                                <h6>Stock: <c:out value="${product.count}"/></h6>
                                <%
                                    if (session.getAttribute("auth") != null){
                                %>
                                <div class="mt-3 d-flex justify-content-between">
                                    <a href=<%=ServletPath.ROOT + request.getServletPath().concat(ServletPath.CART_ADD).concat("?id=")%>${product.id} class="btn btn-dark ">Add To Cart</a>
                                    <a href="#" class="btn btn-primary ">Buy Now</a>
                                </div>
                                <% }
                                %>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
    </div>
</body>
</html>