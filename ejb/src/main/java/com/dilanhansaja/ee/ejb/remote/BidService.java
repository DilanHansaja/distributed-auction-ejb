package com.dilanhansaja.ee.ejb.remote;

import jakarta.ejb.Remote;
import lk.jiat.ee.core.model.Bid;

import java.util.List;

@Remote
public interface BidService {
    String placeBid(String auctionId,String bidAmount,String userEmail);
}
