package com.deco2800.game.files;

import com.deco2800.game.files.FileLoader.Location;

import java.io.File;

/**
 * Reading, Writing, and applying history scores in the game.
 */
public class UserScoreHistory {
  private static final String ROOT_DIR = "data";
  private static final String SCORE_HISTORY_FILE = "history_scores.json";

  /**
   * Get the stored history scores
   * @return Copy of the current history scores
   */
  public static ScoreHistory get() {
    String path = ROOT_DIR + File.separator + SCORE_HISTORY_FILE;
    ScoreHistory scoreHistory = FileLoader.readClass(ScoreHistory.class, path, Location.LOCAL);

    // Use default values if file doesn't exist
    if (scoreHistory != null) return scoreHistory;

    // set the file if it doesn't exist
    ScoreHistory newScoreHistory = new ScoreHistory();
    UserScoreHistory.set(newScoreHistory);
    return newScoreHistory;
  }

  /**
   * Set the stored history scores
   * @param scoreHistory New scores to store
   */
  public static void set(ScoreHistory scoreHistory) {
    String path = ROOT_DIR + File.separator + SCORE_HISTORY_FILE;
    FileLoader.writeClass(scoreHistory, path, Location.LOCAL);
  }

  /**
   * Stores history scores, can be serialised/deserialized.
   */
  public static class ScoreHistory {
    /**
     * List of Player's scores
     */
//    public ArrayList<Score> scores;
    public int testInt = 4;
    public boolean testBool = false;

    public void setTestInt(int testInt) {
      this.testInt = testInt;
    }

    public void setTestBool(boolean testBool) {
      this.testBool = testBool;
    }
  }

  /**
   * stores a single score object
   * { score: 50, time: 150 seconds, date: 2016-11-16 }
   */
  public static class Score {
    public int score;
    public int time;

    public Score(int score, int time) {
      this.score = score;
      this.time = time;
    }

    public int getScore() {
      return score;
    }

    public void setScore(int score) {
      this.score = score;
    }

    public int getTime() {
      return time;
    }

    public void setTime(int time) {
      this.time = time;
    }
  }

//  private UserScoreHistory() {
//    throw new IllegalStateException("Instantiating static util class");
//  }
}
