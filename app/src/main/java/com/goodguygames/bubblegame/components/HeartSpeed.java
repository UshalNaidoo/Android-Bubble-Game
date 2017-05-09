/**
 *
 */
package com.goodguygames.bubblegame.components;

import java.util.Random;

public class HeartSpeed {

  public static final int DIRECTION_DOWN = 1;
  private float yv = 1;        // velocity value on the Y axis
  private float xv = 1;

  private int yDirection = DIRECTION_DOWN;

  public HeartSpeed(int i) {
    Random ran = new Random();
    int spd;
    if (i > 10) {
      i = 10;
    }
    spd = ran.nextInt(15) + 1 + i;
    this.yv = spd;
  }

  public HeartSpeed(float xv, float yv) {
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
