/**
 *
 */
package com.goodguygames.bubblegame.physics;

public class Position {

  private int xCoordinate;
  private int yCoordinate;

  public Position(int xCoordinate, int yCoordinate) {
    setxCoordinate(xCoordinate);
    setyCoordinate(yCoordinate);
  }

  public int getxCoordinate() {
    return xCoordinate;
  }

  public void setxCoordinate(int xCoordinate) {
    this.xCoordinate = xCoordinate;
  }

  public int getyCoordinate() {
    return yCoordinate;
  }

  public void setyCoordinate(int yCoordinate) {
    this.yCoordinate = yCoordinate;
  }

}
