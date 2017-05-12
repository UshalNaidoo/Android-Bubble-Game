package com.goodguygames.bubblegame.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import com.goodguygames.bubblegame.full.GameScene;

public abstract class Bubble {

  private Bitmap bitmap;
  private int xCoordinate;
  private int yCoordinate;
  private Velocity velocity;
  private int points;

  public Bubble() {
  }

  public Velocity getVelocity() {
    return velocity;
  }

  public void setVelocity(Velocity velocity) {
    this.velocity = velocity;
  }

  public Bitmap getBitmap() {
    return bitmap;
  }

  public void setBitmap(Bitmap bitmap) {
    this.bitmap = bitmap;
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

  public int getPoints() {
    return points;
  }

  public void setPoints(int points) {
    this.points = points;
  }

  /**
   * The bubble was touched by the user
   */
  public void setTouched() {

  }

  /**
   * Handles the Action Down event. If the event happens on the bitmap surface
   * then the touched state is set to <code>true</code> otherwise to <code>false</code>
   *
   * @param eventX - the event's X coordinate
   * @param eventY - the event's Y coordinate
   */
  public void handleActionDown(int eventX, int eventY) {
    if (eventY >= (yCoordinate - bitmap.getHeight() / 2) && (eventY <= (yCoordinate + bitmap.getHeight() / 2) + 30)) {
      if (eventX >= (xCoordinate - bitmap.getWidth() / 2) && (eventX <= (xCoordinate + bitmap.getWidth() / 2))) {
        // droid touched
        setTouched();
      }
    }
  }

  public boolean isBubbleOnScreen() {
    if (Direction.UP.equals(this.getVelocity().getDirection())
        && this.getyCoordinate() - this.getBitmap().getHeight() / 2 <= 0) {
      return false;
    }
    else if (Direction.DOWN.equals(this.getVelocity().getDirection())
        && this.getyCoordinate() - this.getBitmap().getHeight() / 2 >= GameScene.screenHeight) {
      return false;
    }
    return true;
  }

  /**
   * Method which updates the droid's internal state every tick
   */
  public void moveBubble() {
    if (Direction.DOWN.equals(velocity.getDirection()) || Direction.UP.equals(velocity.getDirection())) {
      yCoordinate += (velocity.getVerticalVelocity() * velocity.getDirection().value);
    }
    else if (Direction.LEFT.equals(velocity.getDirection()) || Direction.RIGHT.equals(velocity.getDirection())) {
      xCoordinate += (velocity.getHorizontalVelocity() * velocity.getDirection().value);
    }

    if (!isBubbleOnScreen()) {
      bubbleOutOfView();
    }

  }

  public void bubbleOutOfView() {

  }

  public void resetBubblePosition() {
    xCoordinate = 0;
  }

  public void draw(Canvas canvas) {
    canvas.drawBitmap(bitmap, xCoordinate - (bitmap.getWidth() / 2), yCoordinate - (bitmap.getHeight() / 2), null);
  }

}
