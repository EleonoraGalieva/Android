package com.example.tabataapplication;

import android.graphics.drawable.Drawable;

public class Phase {
    private Action actionName;
    private int time;
    private String description;
    private Drawable actionImage;

    public Phase(Action actionName, int time, String description, Drawable actionImage) {
        this.actionName = actionName;
        this.time = time;
        this.description = description;
        this.actionImage = actionImage;
    }

    public Phase(Action actionName, int time, String description) {
        this.actionName = actionName;
        this.time = time;
        this.description = description;
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
        this.time = time;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Action getActionName() {
        return actionName;
    }

    public int getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }
}
