<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 05/28/25
  Time: 10:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Login</title>
    <link rel="stylesheet" href="css/adminLogin.css">
</head>
<body>

<div class="login-container">
    <h2>Admin Login</h2>
    <%
        if (request.getAttribute("error") != null) {
            out.println("<div style='color:red;'>Invalid Credentials</div>");
        }
    %>
    <form action="${pageContext.request.contextPath}/adminLogin" method="post">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required placeholder="Enter admin username">
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required placeholder="Enter password">
        </div>
        <button type="submit">Login</button>
    </form>

</div>
</body>
</html>
