package com.deco2800.game.services;

import com.deco2800.game.files.UserScoreHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.deco2800.game.files.UserScoreHistory.*;

/** controls the game score
 * */
public class ScoreService {
    private static Logger logger = LoggerFactory.getLogger(ScoreService.class);
    private int bonus;
    private GameTime gametime;

    /**
     * Constructor of score service
     * gametime is the basic score calculation parameter.
     * bonus is other features bonus scores.
     */
    public ScoreService() {
        gametime = new GameTime();
        bonus = 0;
    }

    /**
     * Add bonus points to the score
     * @param bonus bonus points
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

    /** save the current score in the score history */
    public void saveCurrentScoreToHistory() {

    }

    /** retrieve the score history */
    public ScoreHistory getScoreHistory() {
        return UserScoreHistory.get();
    }
}
