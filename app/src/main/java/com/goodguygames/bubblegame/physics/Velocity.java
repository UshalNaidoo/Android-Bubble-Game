/**
 *
 */
package com.goodguygames.bubblegame.physics;

public class Velocity {

  private Speed speed;
  private Direction direction;

  public Velocity(Direction direction) {
    this.setDirection(direction);
    this.setSpeed(Speed.NORMAL);
  }

  public Speed getSpeed() {
    return speed;
  }

  public void setSpeed(Speed speed) {
    this.speed = speed;
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
