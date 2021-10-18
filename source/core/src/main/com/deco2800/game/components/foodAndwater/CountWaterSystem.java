package com.deco2800.game.components.foodAndwater;

/**
 * this class handles the water update
 */
public class CountWaterSystem {
    private int timer = 0;
    private int difference=0;

    /**
     * set a time
     * @param value a integer
     */
    public void setTimer(int value){ this.timer = value; }

    /**
     * set the time difference
     * @param difference a integer
     */
    public void setDifference(int difference){ this.difference = difference; }

    /**
     * return the time
     */
    public int getTimer(){
        return this.timer;
    }

    /**
     * return the time difference
     */
    public int getDifference(){
        return this.difference;
    }

}
