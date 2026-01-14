package com.dilanhansaja.ee.ejb.bean;

import com.dilanhansaja.ee.ejb.remote.AuctionService;
import com.dilanhansaja.ee.ejb.remote.BidMapService;
import com.dilanhansaja.ee.ejb.remote.BidService;
import com.dilanhansaja.ee.ejb.remote.UserAccountService;
import jakarta.ejb.*;
import lk.jiat.ee.core.model.Auction;
import lk.jiat.ee.core.model.Bid;
import com.dilanhansaja.ee.ejb.messages.MessageSender;
import com.dilanhansaja.ee.ejb.remote.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Stateless
public class BidManagerBean implements BidService {

    @EJB
    AuctionService auctionService;
    @EJB
    UserAccountService userAccountService;
    @EJB
    BidMapService bidMapService;
    @EJB
    MessageSender messageSender;

    @Override
    public String placeBid(String auctionId, String bidAmount, String userEmail) {

        if(auctionId==null){
            return "Invalid Auction ID";
        }else if(userAccountService.getUserByEmail(userEmail)==null){
            return "User Session Expired";
        }else{

           try {
               int id = Integer.parseInt(auctionId);
               double amount = Double.parseDouble(bidAmount);
               Auction auction = auctionService.getAuctionById(id);

               if(auction==null){
                   return "Auction not found";
               }else if(!auction.getStatus().equals("Active")){
                  return "Auction has ended";
               }else {

                   Bid bid = new Bid(
                           userEmail,
                           userAccountService.getUserByEmail(userEmail).getName(),
                           LocalDateTime.now(),
                           amount,
                           id
                   );

                   String response = bidMapService.saveBid(bid);

                   if("Success".equals(response)){
                       messageSender.send("Rs. " + bid.getBidAmount() + " | By: " + bid.getUserName() + " | " + bid.getBidTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a")));
                       return "Success";
                   }else{
                       return response;
                   }

               }

           }catch (NumberFormatException e){
               return "Invalid auction id or bid amount";
           }
        }

    }
}
