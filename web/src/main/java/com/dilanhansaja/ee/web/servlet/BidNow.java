package com.dilanhansaja.ee.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.dilanhansaja.ee.ejb.remote.BidService;

import java.io.IOException;

@WebServlet("/bidNow")
public class BidNow extends HttpServlet {

    @EJB
    private BidService bidService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String auctionID = request.getParameter("auctionId");
        String bidAmount = request.getParameter("bidAmount");

        if(request.getSession().getAttribute("user")!=null) {

                String userEmail = (String) request.getSession().getAttribute("user");
                String msg = bidService.placeBid(auctionID, bidAmount, userEmail);

                if (msg.equals("Success")) {
                    response.sendRedirect("singleAuction?id=" + auctionID);
                } else {
                    response.getWriter().write(msg);
                }

        }else{
            response.sendRedirect("login.jsp");
        }

    }

}
