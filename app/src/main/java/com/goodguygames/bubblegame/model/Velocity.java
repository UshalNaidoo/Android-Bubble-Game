/**
 *
 */
package com.goodguygames.bubblegame.model;

import java.util.Random;

public class Velocity {

  private float yv = 1;
  private float xv = 1;
  private int yDirection;

  public Velocity(int speed, Direction direction) {
    Random ran = new Random();
    int spd = ran.nextInt(2) + 3 + speed;
    this.setyDirection(direction.value);
    this.yv = spd;
  }

  public Velocity(float xv, float yv) {
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
