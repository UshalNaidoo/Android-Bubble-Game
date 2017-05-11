package com.goodguygames.bubblegame.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import com.goodguygames.bubblegame.full.MainGamePanel;

public abstract class Bubble {

  private Bitmap bitmap;        // the actual bitmap
  private int x;                        // the X coordinate
  private int y;                        // the Y coordinate
  private Velocity velocity;        // the velocity with its directions
  private int points;


  public Bubble() {
  }

  public Bubble(int x, int y) {
    this.setX(x);
    this.setY(y);
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

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
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
    if (eventY >= (y - bitmap.getHeight() / 2) && (eventY <= (y + bitmap.getHeight() / 2) + 30)) {
      if (eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth() / 2))) {
        // droid touched
        setTouched();
      }
    }
  }

  public boolean isBubbleOnScreen() {
    if (Direction.UP.equals(this.getVelocity().getDirection())
        && this.getY() - this.getBitmap().getHeight() / 2 <= 0) {
      return false;
    }
    else if (Direction.DOWN.equals(this.getVelocity().getDirection())
        && this.getY() - this.getBitmap().getHeight() / 2 >= MainGamePanel.screenHeight) {
      return false;
    }
    return true;
  }

  /**
   * Method which updates the droid's internal state every tick
   */
  public void moveBubble() {
    if (Direction.DOWN.equals(velocity.getDirection()) || Direction.UP.equals(velocity.getDirection())) {
      y += (velocity.getVerticalVelocity() * velocity.getDirection().value);
    }
    else if (Direction.LEFT.equals(velocity.getDirection()) || Direction.RIGHT.equals(velocity.getDirection())) {
      x += (velocity.getHorizontalVelocity() * velocity.getDirection().value);
    }

    if (!isBubbleOnScreen()) {
      bubbleOutOfView();
    }

  }

  public void bubbleOutOfView() {

  }

  public void resetBubblePosition() {
    x = 0;
  }

  public void draw(Canvas canvas) {
    canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
  }

}
