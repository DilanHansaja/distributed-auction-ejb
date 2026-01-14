package lk.jiat.ee.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Bid implements Serializable {
    private int id;
    private String userEmail;
    private String userName;
    private LocalDateTime bidTime;
    private double bidAmount;
    private int auctionId;

    public Bid() {
    }

    public Bid(String userEmail, String userName, LocalDateTime bidTime, double bidAmount, int auctionId) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.bidTime = bidTime;
        this.bidAmount = bidAmount;
        this.auctionId = auctionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getBidTime() {
        return bidTime;
    }

    public void setBidTime(LocalDateTime bidTime) {
        this.bidTime = bidTime;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }
}
