<%@ page import="lk.jiat.ee.core.model.Bid" %>
<%@ page import="lk.jiat.ee.core.model.Auction" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.time.Duration" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="com.dilanhansaja.ee.ejb.bean.BidMapBean" %>
<%@ page import="com.dilanhansaja.ee.ejb.bean.BidManagerBean" %>
<%@ page import="jakarta.ejb.EJB" %>
<%@ page import="jakarta.inject.Inject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
    Auction auction = (Auction) request.getAttribute("auction");
    List<Bid> bids = (List<Bid>) request.getAttribute("bids");

    String latestBid = "No bids yet.";
    double min = 0;
    Duration remaining = Duration.between(LocalDateTime.now(), auction.getEndTime());
    long secondsLeft = remaining.getSeconds();

    if (auction != null) {
        if (auction.getHighestBid() != auction.getMinBid()) {
            latestBid = "Rs. " + String.valueOf(auction.getHighestBid());
        }
        min = auction.getHighestBid() + auction.getBidIncrement();
    } else {
        response.sendRedirect("viewAuctions.jsp");
    }
%>
<html>
<head>
    <title>Bid Now</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <link rel="stylesheet" href="css/bidNow.css">
</head>
<body style="background-color: #f3f3f3;">

<div class="container-fluid col-12">
    <div class="row justify-content-center">

        <!-- Navbar -->
        <div class="col-12 sticky-top bg-light pb-2 pt-2">
            <div class="row align-items-center">
                <div class="col-5">
                    <span class="fw-semibold fs-5 aptos">Bid Now</span>
                </div>

                <div class="col-2 text-center">
                    <div class="row">
                        <div class="col-12">
                            <span id="timer" class="aptos fw-light"
                                  data-timeleft="<%=secondsLeft%>">00H : 00M : 00S</span>
                        </div>
                        <div class="col-12 aptos <%= "Inactive".equals(auction.getStatus()) ? "d-none" : "d-block" %>"
                             id="remainingText" style="font-size: 13px;">Remaining
                        </div>
                    </div>
                </div>

                <div class="col-5">
                    <div class="row justify-content-end">
                        <div class="col-4">
                            <div class="row justify-content-end">
                                <div class="col-3 text-end tooltipWrapper">
                                    <img src="assets/home.png" class="handCursor adminIcon"/>
                                </div>
                                <div class="col-3 text-end tooltipWrapper">
                                    <img src="assets/add_time.png" class="handCursor adminIcon"/>
                                </div>
                                <div class="col-3 text-end tooltipWrapper">
                                    <img src="assets/history.png" class="handCursor adminIcon"/>
                                </div>
                                <div class="col-3 text-end tooltipWrapper">
                                    <img src="assets/logout.png" class="handCursor adminIcon"
                                         data-bs-toggle="modal" data-bs-target="#staticBackdrop"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <!-- End Navbar -->

        <div class="col-12 pt-5">
            <div class="row">

                <%
                    if ("Inactive".equals(auction.getStatus())) {
                %>
                <div class="col-12 pb-5">
                    <div class="row justify-content-center">
                        <div class="col-6 text-center aptos fs-4 winBox p-3 fw-semibold">
                            🎉 '<%=bids.get(bids.size() - 1).getUserName()%>' just won the auction 🎉
                        </div>
                    </div>
                </div>
                <%
                    }
                %>

                <!-- Left: Auction & Bid -->
                <div class="col-6 px-5">
                    <div class="row">
                        <div class="col-12 mb-3">
                            <div class="aptos fs-2 fw-semibold"><%= auction.getTitle() %>
                            </div>
                        </div>

                        <div class="col-12 py-2 bidInfoBox">
                            <div class="row align-items-center">
                                <div class="col-4 border-end text-center">
                                    <div class="fw-semibold aptos fs-5" id="latestBid"><%= latestBid %>
                                    </div>
                                    <div class="infoTopics">Latest Bid</div>
                                </div>
                                <div class="col-4 border-end text-center">
                                    <div class="fw-semibold aptos fs-5">Rs. <%= auction.getBidIncrement() %>
                                    </div>
                                    <div class="infoTopics">Bid Increment</div>
                                </div>
                                <div class="col-4 text-center">
                                    <div class="fw-semibold aptos fs-5" id="statusText"
                                         style="color:<%= "Inactive".equals(auction.getStatus()) ? "#ed4d5d" : "#3bd36e" %>"><%= auction.getStatus() %>
                                    </div>
                                    <div class="infoTopics">Status</div>
                                </div>
                            </div>
                        </div>

                        <div class="col-12 info-card mt-4">
                            <div class="info-row">
                                <div class="label">Title</div>
                                <div class="value"><%= auction.getTitle() %>
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="label">Starting Bid</div>
                                <div class="value">Rs. <%= auction.getMinBid() %>
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="label">Start Time</div>
                                <div class="value"><%= auction.getStartTime().format(formatter) %>
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="label">End Time</div>
                                <div class="value"><%= auction.getEndTime().format(formatter) %>
                                </div>
                            </div>

                            <form method="post" action="bidNow">
                                <div class="col-12 py-3">
                                    <div class="row">
                                        <div class="col-10">
                                            <input type="hidden" name="auctionId" value="<%= auction.getId() %>">
                                            <input class="form-control aptos" type="number" name="bidAmount"
                                                   id="bidAmount"
                                                   required min="<%= min %>" value="<%= min %>"
                                                   step="<%= auction.getBidIncrement() %>">
                                        </div>
                                        <div class="col-2">
                                            <button type="submit" id="submitBtn"
                                                    class="<%= "Inactive".equals(auction.getStatus()) ? "bidNowBtn2" : "bidNowBtn" %>"
                                                    <%= "Inactive".equals(auction.getStatus()) ? "disabled" : "" %>>
                                                <%= "Inactive".equals(auction.getStatus()) ? "Closed" : "Place Bid" %>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>

                    </div>
                </div>

                <!-- Right: Recent Bids -->
                <div class="col-6 px-5">
                    <div class="col-12 mb-3">
                        <div class="aptos fs-2 fw-semibold">Recent Bids</div>
                    </div>
                    <div class="col-12" style="max-height: 350px; overflow-y: auto;" id="bidsContainer">
                        <div class="col-12 px-3" id="bidsDiv">
                            <div class="row">
                                <% if (bids != null && !bids.isEmpty()) {
                                    bids.sort(Comparator.comparing(Bid::getBidTime).reversed());
                                    for (Bid bid : bids) { %>
                                <div class="col-12 py-2  mb-2 recentBidsBox">
                                    <div class="row align-items-center">
                                        <div class="col-6">
                                            <div class="fw-semibold aptos fs-5 bid-amount">Rs. <%= bid.getBidAmount() %>
                                            </div>
                                            <div class="infoTopics bid-meta">By: <%= bid.getUserName() %>
                                            </div>
                                        </div>
                                        <div class="col-6 text-end">
                                            <div class="infoTopics mt-1"><%= bid.getBidTime().format(DateTimeFormatter.ofPattern("hh:mm:ss a")) %>
                                            </div>
                                            <div class="infoTopics"><%= bid.getBidTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) %>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <% }
                                } else { %>
                                <div class="text-muted" id="noBids"> No bids yet.</div>
                                <% } %>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>

    </div>
</div>

<!-- Scripts -->
<script>
    // Timer logic
    document.addEventListener("DOMContentLoaded", () => {
        const timer = document.getElementById("timer");
        let seconds = parseInt(timer.getAttribute("data-timeleft"));

        const interval = setInterval(() => {
            if (seconds <= 0) {
                timer.innerText = "Auction Ended";
                document.getElementById("submitBtn").innerText = "Closed";
                document.getElementById("submitBtn").disabled = true;
                document.getElementById("submitBtn").classList = "bidNowBtn2";
                if (!document.getElementById("remainingText").classList.contains("d-none")) {
                    document.getElementById("remainingText").classList.add("d-none");
                }
                document.getElementById("statusText").innerHTML = "Inactive";
                document.getElementById("statusText").style.color = "#ed4d5d";
                clearInterval(interval);
            } else {
                const h = Math.floor(seconds / 3600);
                const m = Math.floor((seconds % 3600) / 60);
                const s = seconds % 60;
                timer.innerText = h.toString().padStart(2, '0') + "H : " + m.toString().padStart(2, '0') + "M : " + s.toString().padStart(2, '0') + "S";
                seconds--;
            }
        }, 1000);
    });

    const ws = new WebSocket("ws://localhost:8080/online-auction/bidSocket");

    ws.onmessage = e => {
        const parts = e.data.split("|");
        if (parts.length === 3) {
            const amount = parts[0].trim();
            const name = parts[1].trim();
            const time = parts[2].trim();

            const bidDiv = document.createElement("div");
            bidDiv.classList.add("row", "align-items-center", "mb-2");

            bidDiv.innerHTML = '<div class="col-12 py-2  mb-2 recentBidsBox">' +
                '<div class="row align-items-center">' +
                '<div class="col-6">' +
                '<div class="fw-semibold aptos fs-5 bid-amount">' + amount + '</div>' +
                '<div class="infoTopics bid-meta">' + name + '</div>' +
                '</div>' +
                '<div class="col-6 text-end">' +
                '<div class="infoTopics mt-1">' + time.split(" ")[1] + '</div>' +
                '<div class="infoTopics">' + time.split(" ")[0] + '</div>' +
                '</div></div></div>';

            const bidsContainer = document.getElementById("bidsDiv");
            bidsContainer.prepend(bidDiv);

            const noBidsEl = document.getElementById("noBids");
            if (noBidsEl) {
                noBidsEl.classList.add("d-none");
            }

            document.getElementById("latestBid").innerHTML = amount;

            const nextMin = parseFloat(amount.split(" ")[1]) + <%= auction.getBidIncrement() %>;
            document.getElementById("bidAmount").value = nextMin;
            document.getElementById("bidAmount").min = nextMin;
        }
    };
</script>

</body>
</html>
