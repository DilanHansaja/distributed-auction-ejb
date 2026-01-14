<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 06/02/25
  Time: 3:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register Here</title>
    <link rel="stylesheet" href="css/register.css">
</head>
<body>
<div class="register-container">
    <h2>User Registration</h2>
    <form method="post" action="userRegistration">
        <label for="name">Full Name</label>
        <input type="text" name="name" id="name" required>

        <label for="email">Email</label>
        <input type="email" name="email" id="email" required>

        <label for="password">Password</label>
        <input type="password" name="password" id="password" required>

        <label for="confirm">Confirm Password</label>
        <input type="password" name="confirm" id="confirm" required>

        <input type="submit" value="Register">
    </form>

        <% String error = (String) request.getAttribute("error");
       if (error != null) { %>
    <div class="error"><%= error %></div>
        <% } %>
</body>
</html>
