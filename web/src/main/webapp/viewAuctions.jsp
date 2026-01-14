<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 05/29/25
  Time: 7:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, lk.jiat.ee.core.model.Auction" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>View Auctions</title>
    <link rel="stylesheet" href="css/viewAuctions.css">
</head>
<body>
<h2>Available Auctions</h2>

<%
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");

  List<Auction> auctions = (List<Auction>) request.getAttribute("auctions");
  if (auctions == null || auctions.isEmpty()) {
%>
<p style="text-align: center;">No auctions available at the moment.</p>
<%
} else {
%>
<div class="auction-grid">
  <%
    for (Auction auction : auctions) {
  %>
  <div class="auction-card">
    <h3><%= auction.getTitle() %></h3>
    <p><strong>Min Bid:</strong> Rs. <%= auction.getMinBid() %></p>
    <p><strong>Increment:</strong> Rs. <%= auction.getBidIncrement() %></p>
    <p><strong>Ends:</strong> <%= auction.getEndTime().format(formatter) %></p>
    <p><strong>Status:</strong> <%= "Inactive".equals(auction.getStatus()) ? "Ended" : "Active"  %></p>
    <a class="view-btn" href="singleAuction?id=<%= auction.getId() %>" ><%= "Inactive".equals(auction.getStatus()) ? "View Bid History" : "Bid Now" %></a>
  </div>
  <%
    }
  %>
</div>
<%
  }
%>
</body>
</html>
