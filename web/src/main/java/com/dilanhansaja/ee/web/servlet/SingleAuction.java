package com.dilanhansaja.ee.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.dilanhansaja.ee.ejb.remote.AuctionService;
import com.dilanhansaja.ee.ejb.remote.BidMapService;

import java.io.IOException;

@WebServlet("/singleAuction")
public class SingleAuction extends HttpServlet {

    @EJB
    AuctionService auctionService;

    @EJB
    BidMapService bidMapService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

        String auctionId = request.getParameter("id");

        if(auctionId != null) {

           try {
               int id =  Integer.parseInt(auctionId);

               request.setAttribute("auction",auctionService.getAuctionById(id));
               request.setAttribute("bids",bidMapService.getBidsForAuction(id));

               request.getRequestDispatcher("bidNow.jsp").forward(request, response);

           }catch (NumberFormatException e) {
               e.printStackTrace();
           }

        }else{
            response.getWriter().write("No Auction ID");
        }

    }
}
