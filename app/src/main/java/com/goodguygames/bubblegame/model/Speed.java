/**
 *
 */
package com.goodguygames.bubblegame.model;

import java.util.Random;

public class Speed {

  private float yv = 1;
  private float xv = 1;
  private int yDirection;

  public enum Direction {
    UP (-1),
    DOWN (1),
    LEFT (-10),
    RIGHT (10);

    public int value;
    Direction (int i) {
      this.value = i;
    }
  }

  public Speed(int i, Direction direction) {
    Random ran = new Random();
    int spd = ran.nextInt(2) + 3 + i;
    this.setyDirection(direction.value);
    this.yv = spd;
  }

  public Speed(float xv, float yv) {
    this.yv = yv;
  }

  public void setXv(float xv) {
    this.xv = xv;
  }

  public float getYv() {
    return yv;
  }

  public void setYv(float yv) {
    this.yv = yv;
  }

  public int getyDirection() {
    return yDirection;
  }

  public void setyDirection(int yDirection) {
    this.yDirection = yDirection;
  }

  public void toggleYDirection() {
    yDirection = yDirection * -1;
  }

}
