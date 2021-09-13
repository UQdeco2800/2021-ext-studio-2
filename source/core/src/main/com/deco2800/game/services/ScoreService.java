package com.deco2800.game.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** controls the game score */
public class ScoreService {
    private static Logger logger = LoggerFactory.getLogger(ScoreService.class);
    private int bonus;
    private GameTime gametime;

    /**
     * Constructor of ScoreService. Initialise a new gametime
     */
    public ScoreService() {
        gametime = new GameTime();
        bonus = 0;
    }

    /**
     * add bonus score to scores
     * @param bonus the bonus score from other feature
     */
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
}
