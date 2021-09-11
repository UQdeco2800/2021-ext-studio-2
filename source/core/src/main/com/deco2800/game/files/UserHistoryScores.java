package com.deco2800.game.files;

import com.deco2800.game.files.FileLoader.Location;

import java.io.File;

/**
 * Reading, Writing, and applying history scores in the game.
 */
public class UserHistoryScores {
  private static final String ROOT_DIR = "data";
  private static final String HISTORY_SCORES_FILE = "history_scores.json";

  /**
   * Get the stored history scores
   * @return Copy of the current history scores
   */
  public static HistoryScores get() {
    String path = ROOT_DIR + File.separator + HISTORY_SCORES_FILE;
    HistoryScores historyScores = FileLoader.readClass(HistoryScores.class, path, Location.LOCAL);

    // Use default values if file doesn't exist
    if (historyScores != null) return historyScores;

    // set the file if it doesn't exist
    HistoryScores newHistoryScores = new HistoryScores();
    UserHistoryScores.set(newHistoryScores);
    return newHistoryScores;
  }

  /**
   * Set the stored history scores
   * @param historyScores New scores to store
   */
  public static void set(HistoryScores historyScores) {
    String path = ROOT_DIR + File.separator + HISTORY_SCORES_FILE;
    FileLoader.writeClass(historyScores, path, Location.LOCAL);
  }

  /**
   * Stores history scores, can be serialised/deserialized.
   */
  public static class HistoryScores {
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

//  private UserHistoryScores() {
//    throw new IllegalStateException("Instantiating static util class");
//  }
}
