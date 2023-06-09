<%@ page import="com.example.webappdemo.servlet.ServletPath" %>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
  <div class="container-fluid">
    <a class="navbar-brand" href=<%=ServletPath.INDEX%> >Shopping Cart</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>


    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href=<%=pageContext.getServletContext().getContextPath().concat(ServletPath.INDEX)%> >Home</a>
        </li>


        <%
          if (session.getAttribute("auth") != null){
            %>
          <li class="nav-item">
            <a class="nav-link" href= <%=pageContext.getServletContext().getContextPath().concat(ServletPath.CART)%> >Cart</a>
          </li>

          <li class="nav-item">
            <a class="nav-link" href= <%=pageContext.getServletContext().getContextPath().concat(ServletPath.ORDERS)%> >Orders</a>
          </li>

          <li class="nav-item">
            <a class="nav-link" href= <%=pageContext.getServletContext().getContextPath().concat(ServletPath.AUTH_LOGOUT)%>>Logout</a>
          </li>
        <%
          }
          else {
        %>
        <li class="nav-item">
          <a class="nav-link" href=<%=pageContext.getServletContext().getContextPath().concat(ServletPath.LOGIN)%> >Login</a>
        </li>
        <%
          }
        %>



      </ul>
      <form class="d-flex" role="search">
        <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
        <button class="btn btn-outline-success" type="submit">Search</button>
      </form>
    </div>
  </div>
</nav>