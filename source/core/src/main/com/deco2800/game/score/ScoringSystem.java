package com.deco2800.game.score;

import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.entities.Entity;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**This class is written by team 9.
 * This class handles the scoring system*/
public class ScoringSystem {

    //A global variable, indicate the gaming time in seconds, minutes and hours.
    //They start from zero.
    private long seconds = 0;
    private long minutes = 0;
    private long hours = 0;

    //scoreSeconds is just seconds
    private long scoreSeconds = 0;

    //Calender can help us to use the current time.
    //Maybe I don't need this one haha.
    private Calendar calender;

    //Help us to count the time.
    public Timer clock = new Timer();

    //Use Entity class to find health value.
    //Entity entity = new Entity();

    /**
     * create a TimerTask for the schedule method
     * @return TimerTask
     */
    public TimerTask clockPrepare() {
        return new TimerTask() {
            @Override
            public void run() {
                seconds++;
                scoreSeconds++;
                if (seconds == 60) {
                    seconds = 0;
                    minutes++;
                }
                if (minutes == 60) {
                    minutes = 0;
                    hours++;
                }
                System.out.println(seconds + "seconds");
                System.out.println(minutes + "minutes");
            }
        };
    }

    /**
     * Record the gaming time in seconds.*/
    public void startGameClock() {
        TimerTask task = clockPrepare();
        //delay:1000 means this timer starts after 1sec, period:1000 simply means plus 1 second.
        clock.scheduleAtFixedRate(task, 1000, 1000);
    }

    public long calculateFinalScore() {
        //At this moment, the seconds is the final score;
        return scoreSeconds;
    }
}
