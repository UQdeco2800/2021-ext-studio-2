package com.deco2800.game.services;

import com.deco2800.game.files.UserScoreHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** controls the game score */
public class ScoreService {
    private static Logger logger = LoggerFactory.getLogger(ScoreService.class);
    private int bonus;
    private GameTime gametime;

    public ScoreService() {
        gametime = new GameTime();
        bonus = 0;
    }

    public void addToScore(int bonus) {
        this.bonus += bonus;
    }

    /**
     * returns base score + bonus
     * @return score
     */
    public int getScore() {
        return getBaseScore() + bonus;
    }

    /** give score in seconds */
    private int getBaseScore() {
        return (int) gametime.getTime()/1000;
    }

    /** save the current score in the score history */
    public void saveCurrentScoreToHistory() {

    }

    /** retrieve the score history */
    public UserScoreHistory.ScoreHistory getScoreHistory() {
        return UserScoreHistory.get();
    }
}
