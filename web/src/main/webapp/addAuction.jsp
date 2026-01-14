<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 05/27/25
  Time: 5:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("admin") == null) {
        response.sendRedirect("adminLogin.jsp");
        return;
    }
%>
<html>
<head>
    <title>Create New Auction</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="form-container">
    <h2>Create New Auction</h2>
    <form action="${pageContext.request.contextPath}/addAuction" method="post">
        <label for="title">Auction Title</label>
        <input type="text" id="title" name="title" required value="Iphone 14 Pro">

        <label for="description">Description</label>
        <textarea id="description" name="description" rows="4" required>Brand New - Iphone 14 Pro | 256GB | 8GB</textarea>

        <label for="minBid">Minimum Bid</label>
        <input type="number" min="100" id="minBid" name="minBid" step="1" required value="150000">

        <label for="bidIncrement">Bid Increment</label>
        <input type="number" min="100" id="bidIncrement" name="bidIncrement" step="1" required value="2000">

        <label for="endTime">End Time</label>
        <input type="datetime-local" id="endTime" name="endTime" required>

        <input type="submit" value="Add New Auction">
    </form>
</div>

</body>
</html>
