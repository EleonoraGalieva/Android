package com.example.game.Models;

public class User {
    private String id;
    private String username;
    private int winnings;
    private int losses;
    //add image handling


    public User() {
    }

    public User(String id, String username, int winnings, int losses) {
        this.id = id;
        this.username = username;
        this.winnings = winnings;
        this.losses = losses;
    }

    public String getId() {
        return id;
    }

    public int getWinnings() {
        return winnings;
    }

    public void setWinnings(int winnings) {
        this.winnings = winnings;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}