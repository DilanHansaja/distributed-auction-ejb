package com.dilanhansaja.ee.ejb.bean;

import jakarta.ejb.*;
import lk.jiat.ee.core.model.Auction;
import com.dilanhansaja.ee.ejb.remote.AuctionService;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Singleton
public class AuctionManagerBean implements AuctionService {

    private final Map<Integer, Auction> auctionMap =new ConcurrentHashMap<>();

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public AuctionManagerBean() {
        scheduler.scheduleAtFixedRate(this::updateExpiredAuctions, 0, 5, TimeUnit.SECONDS);
    }

    @Lock(LockType.READ)
    @Override
    public Auction getAuctionById(int id) {

        if(auctionMap.containsKey(id)) {
            return auctionMap.get(id);
        }
        return null;
    }

    @Override
    @Lock(LockType.WRITE)
    public String addAuction(String title, String description,
                             String minBidStr, String bidIncrementStr, String endTimeStr) {

        double minBid;
        double bidIncrement;
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime;

        if (title == null || title.trim().isEmpty()) {
            return "Auction title is required.";
        }

        if (description == null || description.trim().isEmpty()) {
            return "Auction description is required.";
        }

        try {
            minBid = Double.parseDouble(minBidStr);
            if (minBid <= 0) {
                return "Minimum bid must be greater than 0.";
            }
        } catch (NumberFormatException e) {
            return "Minimum bid must be a valid number.";
        }

        try {
            bidIncrement = Double.parseDouble(bidIncrementStr);
            if (bidIncrement <= 0) {
                return "Bid increment must be greater than 0.";
            }
        } catch (NumberFormatException e) {
            return "Bid increment must be a valid number.";
        }

        try {
            endTime = LocalDateTime.parse(endTimeStr);
            if (endTime.isBefore(startTime)) {
                return "End time must be in the future.";
            }
        } catch (DateTimeParseException e) {
            return "End time is not in a valid format.";
        }

        //save auction
        int auctionId = auctionMap.size() + 1;
        Auction newAuction = new Auction(
                                auctionId,
                                title,
                                description,
                                minBid,
                                bidIncrement,
                                startTime,
                                endTime,
                                minBid,
                                "Active"
        );

        auctionMap.put(auctionId, newAuction);

        return "Success";
    }

    @Lock(LockType.READ)
    @Override
    public Map<Integer, Auction> getAuctionMap() {
        return auctionMap;
    }

    @Override
    @Lock(LockType.WRITE)
    public boolean updateAuction(Auction auction) {

        if(auctionMap.containsKey(auction.getId())) {
            auctionMap.put(auction.getId(), auction);
            return true;
        }
        return false;
    }

    private void updateExpiredAuctions() {

        for (Auction auction : auctionMap.values()) {
            if ("Active".equals(auction.getStatus()) && auction.getEndTime().isBefore(LocalDateTime.now())) {
                auction.setStatus("Inactive");
                System.out.println("Auction " + auction.getId() + " marked as inactive.");
            }
        }
    }

}
