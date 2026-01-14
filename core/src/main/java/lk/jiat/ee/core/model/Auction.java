package lk.jiat.ee.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Auction implements Serializable {

    private int id;
    private String title;
    private String description;
    private double minBid;
    private double bidIncrement;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double highestBid;
    private String status;

    public Auction() {
    }

    public Auction(int id, String title, String description, double minBid, double bidIncrement, LocalDateTime startTime, LocalDateTime endTime, double highestBid, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.minBid = minBid;
        this.bidIncrement = bidIncrement;
        this.startTime = startTime;
        this.endTime = endTime;
        this.highestBid = highestBid;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getMinBid() {
        return minBid;
    }

    public void setMinBid(double minBid) {
        this.minBid = minBid;
    }

    public double getBidIncrement() {
        return bidIncrement;
    }

    public void setBidIncrement(double bidIncrement) {
        this.bidIncrement = bidIncrement;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public double getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(double highestBid) {
        this.highestBid = highestBid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
