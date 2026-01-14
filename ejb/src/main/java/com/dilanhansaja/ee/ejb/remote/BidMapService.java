package com.dilanhansaja.ee.ejb.remote;

import jakarta.ejb.Remote;
import lk.jiat.ee.core.model.Bid;

import java.util.List;

@Remote
public interface BidMapService {
   List<Bid> getBidsForAuction(int auctionId);
   String saveBid(Bid bid);
}
