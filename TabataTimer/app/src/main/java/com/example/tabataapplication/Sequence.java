package com.example.tabataapplication;

public class Sequence {
    private String title;
    private int loops;
    private int sets;
    private int timeBetweenSets;

    public Sequence(String title, int loops, int sets, int timeBetweenSets, int colour) {
        this.title = title;
        this.loops = loops;
        this.sets = sets;
        this.timeBetweenSets = timeBetweenSets;
        this.colour = colour;
    }

    private int colour;

    public void setLoops(int loops) {
        this.loops = loops;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public void setTimeBetweenSets(int timeBetweenSets) {
        this.timeBetweenSets = timeBetweenSets;
    }

    public int getLoops() {
        return loops;
    }

    public int getSets() {
        return sets;
    }

    public int getTimeBetweenSets() {
        return timeBetweenSets;
    }

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
