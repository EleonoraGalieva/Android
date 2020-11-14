package com.example.tabataapplication;

import android.graphics.drawable.Drawable;

public class Phase {
    private Action actionName;
    private int time;
    private String description;
    private Drawable actionImage;
    private int setsAmount;

    public void setSetsAmount(int setsAmount) {
        this.setsAmount = setsAmount;
    }

    public int getSetsAmount() {
        return setsAmount;
    }

    public Phase(Action actionName, int time, Drawable actionImage) {
        this.actionName = actionName;
        this.time = time;
        this.actionImage = actionImage;
    }

    public void setActionImage(Drawable actionImage) {
        this.actionImage = actionImage;
    }

    public Drawable getActionImage() {
        return actionImage;
    }

    public void setActionName(Action actionName) {
        this.actionName = actionName;
    }

    public void setTime(int time) {
        this.time = Math.max(time, 0);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActionName() {
        String res;
        switch (actionName) {
            case PREPARATION:
                res = "Preparation";
                break;
            case WORK:
                res = "Work";
                break;
            case RELAX:
                res = "Relax";
                break;
            case RELAX_BETWEEN_SETS:
                res = "Relax between sets";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + actionName);
        }
        return res;
    }

    public int getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }
}
