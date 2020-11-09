package com.example.tabataapplication;

public class Sequence {
    private String title;
    private int colour;

    public Sequence(String title, int colour) {
        this.title = title;
        this.colour = colour;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    public String getTitle() {
        return title;
    }

    public int getColour() {
        return colour;
    }
}
