<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 05/28/25
  Time: 9:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, lk.jiat.ee.core.model.Auction" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    if (session.getAttribute("admin") == null) {
        response.sendRedirect("adminLogin.jsp");
        return;
    }
%>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="css/adminDashboard.css">
</head>
<body style="padding: 20px">
<div>
    <div class="heading">All Auctions <a class="addNewBtn" href="addAuction.jsp" target="_blank">Add New Auction</a></div>
</div>

<%
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
    List<Auction> auctions = (List<Auction>) request.getAttribute("auctions");

    if (auctions == null || auctions.isEmpty()) {
%>
<p>No auctions available.</p>
<%
} else {
%>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Description</th>
        <th>Min Bid</th>
        <th>Increment</th>
        <th>Start</th>
        <th>End</th>
        <th>Status</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (Auction auction : auctions) {
    %>
    <tr>
        <td><%= auction.getId() %></td>
        <td><%= auction.getTitle() %></td>
        <td><%= auction.getDescription() %></td>
        <td><%= auction.getMinBid() %></td>
        <td><%= auction.getBidIncrement() %></td>
        <td><%= auction.getStartTime().format(formatter) %></td>
        <td><%= auction.getEndTime().format(formatter) %></td>
        <td><%= auction.getStatus() %></td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
<%
    }
%>

</body>
</html>
