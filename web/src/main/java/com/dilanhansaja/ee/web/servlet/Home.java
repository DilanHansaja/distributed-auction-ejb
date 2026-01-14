package com.dilanhansaja.ee.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.ee.core.model.Auction;
import com.dilanhansaja.ee.ejb.remote.AuctionService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/home")
public class Home extends HttpServlet {

    @EJB
    AuctionService auctionService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Auction> auctionList = new ArrayList<>(auctionService.getAuctionMap().values());
        request.setAttribute("auctions", auctionList);
        request.getRequestDispatcher("viewAuctions.jsp").forward(request, response);
    }
}
