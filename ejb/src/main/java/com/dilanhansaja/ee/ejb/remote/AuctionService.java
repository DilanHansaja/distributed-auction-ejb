package com.dilanhansaja.ee.ejb.remote;

import jakarta.ejb.Remote;
import lk.jiat.ee.core.model.Auction;

import java.util.Map;

@Remote
public interface AuctionService{

    Auction getAuctionById(int id);
    String addAuction(String title, String description,String minBid,
                      String bidIncrement,String endTime);
    Map<Integer,Auction> getAuctionMap();
    boolean updateAuction(Auction auction);

}
