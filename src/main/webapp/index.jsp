
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
                            <h5 class="card-title">Card title</h5>
                            <h6>Name: <c:out value="${product.name}"/></h6>
                            <h6>Price: $<c:out value="${product.price}"/></h6>
                            <h6>Category: <c:out value="${product.category}"/></h6>
                            <div class="mt-3 d-flex justify-content-between">
                                <a href="#" class="btn btn-primary">Add Cart</a>
                                <a href="#" class="btn btn-primary">Buy</a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

</div>
</body>
</html>