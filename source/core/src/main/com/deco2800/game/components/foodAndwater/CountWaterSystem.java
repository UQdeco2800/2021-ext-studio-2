package com.deco2800.game.components.foodAndwater;

public class CountWaterSystem {

    private int timer=0;
    private int diffrence=0;

    public void setTimer(int value){
        this.timer=value;
    }

    public void setDiffrence(int diffrence){
        this.diffrence=diffrence;
    }

    public int getTimer(){
        return this.timer;
    }

    public int getDiffrence(){
        return this.diffrence;
    }

}
