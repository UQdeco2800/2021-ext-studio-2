package com.deco2800.game.components.score;

import com.deco2800.game.entities.Entity;
import com.deco2800.game.services.GameTime;

import java.util.Timer;
import java.util.TimerTask;

/**This class is written by team 9.
 * This class handles the scoring system*/
//decide to keep this name otherwise need to change lots of things.
public class ScoringSystemV1 {

    //indicate the gaming time in seconds, minutes and hours.
    //They start from zero.
    private static int seconds = 0;
    private static int minutes = 0;
    private static int hours = 0;

    private static int score = 0;
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
            score++;
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
                score++;
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
     * Return the total score
     * @return int scores
     */
    public int getScore() {
        return score;
    }

    public void addToScore(int bonus) {
        score = score + bonus;
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

}
