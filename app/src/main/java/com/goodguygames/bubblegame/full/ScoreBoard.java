package com.goodguygames.bubblegame.full;

import java.util.Comparator;

public class ScoreBoard implements Comparable<ScoreBoard> {

  public String name;
  public int score;
  public String url;
  public String isMe;

  public ScoreBoard() {
    super();
  }

  public ScoreBoard(String name, int score, String url, String isMe) {
    super();
    this.name = name;
    this.score = score;
    this.url = url;
    this.isMe = isMe;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public String getURL() {
    return url;
  }

  public void setURL(String url) {
    this.url = url;
  }

  public int compareTo(ScoreBoard compareFruit) {

    int compareQuantity = ((ScoreBoard) compareFruit).getScore();

    //ascending order
    return this.score - compareQuantity;

    //descending order
    //return compareQuantity - this.quantity;

  }

  public static Comparator<ScoreBoard> ScoreComparator = new Comparator<ScoreBoard>() {

    public int compare(ScoreBoard ScoreBoard1, ScoreBoard ScoreBoard2) {

      Integer score1 = ScoreBoard1.getScore();
      Integer score2 = ScoreBoard2.getScore();

      //ascending order
      // return score1.compareTo(score2);

      //descending order
      return score2.compareTo(score1);
    }

  };
}