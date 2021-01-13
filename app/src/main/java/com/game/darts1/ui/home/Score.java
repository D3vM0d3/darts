package com.game.darts1.ui.home;

public class Score {
    private int multiplier;
    private int value;

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

    public Score(int multiplier, int value) {
        this.multiplier = multiplier;
        this.value = value;
    }
}
