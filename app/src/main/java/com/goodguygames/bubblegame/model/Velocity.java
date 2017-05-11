/**
 *
 */
package com.goodguygames.bubblegame.model;

import java.util.Random;

public class Velocity {

  private float verticalVelocity = 1;
  private float horizontalVelocity = 1;
  private Direction direction;

  //TODO how will horizontal bubbles work
  public Velocity(int speed, Direction direction) {
    Random ran = new Random();
    this.setDirection(direction);
    this.setVerticalVelocity(ran.nextInt(2) + 3 + speed);
  }

  public float getHorizontalVelocity() {
    return horizontalVelocity;
  }

  public void setHorizontalVelocity(float horizontalVelocity) {
    this.horizontalVelocity = horizontalVelocity;
  }

  public float getVerticalVelocity() {
    return verticalVelocity;
  }

  public void setVerticalVelocity(float verticalVelocity) {
    this.verticalVelocity = verticalVelocity;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public void toggleVerticalDirection() {
    direction = Direction.DOWN.equals(direction)? Direction.UP : Direction.DOWN;
  }

}
