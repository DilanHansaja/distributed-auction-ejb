package com.dilanhansaja.ee.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.dilanhansaja.ee.ejb.remote.AuctionService;

import java.io.IOException;

@WebServlet("/addAuction")
public class AddAuction extends HttpServlet {

    @EJB
    AuctionService auctionService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("title");
        String description = request.getParameter("description");
        String minBid = request.getParameter("minBid");
        String bidIncrement = request.getParameter("bidIncrement");
        String endTime = request.getParameter("endTime");

        String msg = auctionService.addAuction(name, description, minBid, bidIncrement, endTime);

        if(request.getSession().getAttribute("admin")!=null) {
            if (msg != null) {
                if (msg.equals("Success")) {
                    response.getWriter().write("Auction created successfully!");
                } else {
                    response.getWriter().write(msg);
                }
            } else {
                response.getWriter().write("Something Went Wrong!");
            }
        }else{
            request.setAttribute("error", "Session Expired");
            request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
        }


    }
}
