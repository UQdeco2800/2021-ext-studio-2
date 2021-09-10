package com.deco2800.game.components.foodAndwater;

/**This class is written by team 9.
 * This class handles the scoring system*/
public class WaterSystemV1 {

    private int timer=0;
    private int ttimer = 15;
    private int diffrence=0;

    public void setTimer(int value){
        this.timer=value;
    }

    public void setTtimer(int ttimer){
        this.ttimer=ttimer;
    }

    public void setDiffrence(int diffrence){
        this.diffrence=diffrence;
    }

    public int getTimer(){
        return this.timer;
    }

    public int getTtimer(){
        return this.ttimer;
    }

    public int getDiffrence(){
        return this.diffrence;
    }

}
