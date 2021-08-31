package com.deco2800.game.components.score;

import com.deco2800.game.entities.Entity;
import com.deco2800.game.services.GameTime;

import java.util.Timer;
import java.util.TimerTask;

/**This class is written by team 9.
 * This class handles the scoring system*/
public class ScoringSystemV1 implements ScoringSystem {

    //indicate the gaming time in seconds, minutes and hours.
    //They start from zero.
    private static int seconds = 0;
    private static int minutes = 0;
    private static int hours = 0;

    public GameTime gameTime;

    //scoreSeconds is just seconds
    private static int scoreSeconds = 0;

    //Help us to count the time.
    private static Timer clock = new Timer();

    public ScoringSystemV1() {
        gameTime = new GameTime();
    }

    public static TimerTask task = new TimerTask() {
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
        }
    };
    //I think we don't need a scoringSystem constructor.

    /**
     * Record the gaming time in seconds.*/
    public void startGameClock() {
        //everytime the game restarts, all these values should be zero
        seconds = 0;
        minutes = 0;
        hours = 0;
        scoreSeconds = 0;
        //Timer and TimerTask must be recreated after they were cancelled.
        clock = new Timer();
        task = new TimerTask() {
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

//                new Entity().getEvents().trigger("updateScore", scoreSeconds);
            }
        };
        //delay:0 means this timer starts as soon as the game starts, period:1000 simply means plus 1 second.
        clock.scheduleAtFixedRate(task, 1000, 1000);
    }

    /**
     * Stop the timer in the scoringSystem class.
     */
    public static void stopTimer() {
        clock.cancel();
    }

    /**
     * Instead of stop the timer, just stop the task
     */
    public static void stopTimerTask() {
        task.cancel();
    }

    /**
     * Stop the timer in the scoringSystem class.
     */
    public void stopTimerForTest() {
        clock.cancel();
    }

    /**
     * Instead of stop the timer, just stop the task
     */
    public void stopTimerTaskForTest() {
        task.cancel();
    }

    /**
     * Return the total score
     * @return int scores
     */
    @Override
    public int getScore(int... args) {
        //At this moment, the seconds is the final score;
//        return scoreSeconds;
        return (args[0]);
    }
    /**
     * Return the current seconds of the timer.
     * @return int
     */
    public int getScoreSeconds() {
        return scoreSeconds;
    }

    /**
     * Return the current seconds of the timer.
     * @return int
     */
    public int getSeconds() {
        return seconds;
    }
    /**
     * Return the current minutes of the timer.
     * @return int
     */
    public int getMinutes() {
        return minutes;
    }

    //hour may too long for this game. But keep it anyway.
    public static int getHours() {
        return hours;
    }
}
