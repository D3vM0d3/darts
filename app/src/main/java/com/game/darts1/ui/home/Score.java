package com.game.darts1.ui.home;

import android.graphics.Point;

public class Score {
    private int multiplier;
    private int value;
    private Point position;
    private int ring;

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getTotalScore(){
        return value * multiplier;
    }

    public int getRing() {
        return ring;
    }

    public void setRing(int ring) {
        this.ring = ring;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Score(int multiplier, int value, int ring) {
        this.multiplier = multiplier;
        this.value = value;
        this.ring = ring;
    }
}
