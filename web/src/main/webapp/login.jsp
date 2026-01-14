<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 06/02/25
  Time: 2:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String url1 = "userLogin";

    if(request.getParameter("id")!=null){
        url1="userLogin?id="+request.getParameter("id");
    }
%>
<html>
<head>
    <link rel="stylesheet" href="css/login.css">
    <title>Login</title>
</head>
<body>
<div class="login-container">
    <h2>User Login</h2>
    <form method="post" action="<%=url1%>">
        <label for="email">Email</label>
        <input type="email" name="email" id="email" required>

        <label for="password">Password</label>
        <input type="password" name="password" id="password" required>

        <input type="submit" value="Login">
    </form>
    <a href="register.jsp">Create New</a>
    <% String error = (String) request.getAttribute("error");
        if (error != null) { %>
    <div class="error"><%= error %></div>
    <% } %>
</div>
</body>
</html>
