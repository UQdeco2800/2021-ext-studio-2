package com.deco2800.game.services;

/** controls the game score */
public class ScoreService {
    private int bonus;
    private final GameTime gametime;

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

    /** give score in seconds
     * also count distance (sprint3)*/
    private int getBaseScore() {
        return (int) (gametime.getTime()/1000 + ServiceLocator.getDistanceService().getDistance());
    }

}
