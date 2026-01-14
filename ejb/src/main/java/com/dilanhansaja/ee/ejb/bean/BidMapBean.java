package com.dilanhansaja.ee.ejb.bean;

import jakarta.ejb.EJB;
import jakarta.ejb.Lock;
import jakarta.ejb.LockType;
import jakarta.ejb.Singleton;
import lk.jiat.ee.core.model.Auction;
import lk.jiat.ee.core.model.Bid;
import com.dilanhansaja.ee.ejb.remote.AuctionService;
import com.dilanhansaja.ee.ejb.remote.BidMapService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class BidMapBean implements BidMapService {

    @EJB
    AuctionService auctionService;
    private ConcurrentHashMap<Integer, List<Bid>> bidMap = new ConcurrentHashMap<>();

    @Lock(LockType.READ)
    @Override
    public List<Bid> getBidsForAuction(int auctionId) {

        if(bidMap.containsKey(auctionId)){
            return bidMap.get(auctionId);
        }

        return new ArrayList<>();
    }

    @Override
    @Lock(LockType.WRITE)
    public String saveBid(Bid bid) {

        Auction auction =  auctionService.getAuctionById(bid.getAuctionId());

        if ("Active".equals(auction.getStatus()) && auction.getEndTime().isBefore(LocalDateTime.now())) {
            return "Auction has ended";
        }else{
            if(auction.getHighestBid()==auction.getMinBid()){

                if(bid.getBidAmount() < (auction.getMinBid()+auction.getBidIncrement())){
                    return "Min Bid should be at least "+(auction.getMinBid()+auction.getBidIncrement());
                }

            }else{
                if(bid.getBidAmount() < (auction.getHighestBid()+auction.getBidIncrement())){
                    return "Min Bid should be at least "+(auction.getHighestBid()+auction.getBidIncrement());
                }
            }
            auction.setHighestBid(bid.getBidAmount());

            if(bidMap.containsKey(bid.getAuctionId())){
                List<Bid> bidList = bidMap.get(bid.getAuctionId());
                int id =  bidList.size()+1;

                bid.setId(id);
                bidList.add(bid);

                if(auctionService.updateAuction(auction)){
                    return "Success";
                }

            }else{
                List<Bid> bidList = new ArrayList<>();
                bid.setId(1);
                bidList.add(bid);
                bidMap.put(bid.getAuctionId(), bidList);

                if(auctionService.updateAuction(auction)){
                    return "Success";
                }
            }
        }

        return "Something went wrong";
    }
}

