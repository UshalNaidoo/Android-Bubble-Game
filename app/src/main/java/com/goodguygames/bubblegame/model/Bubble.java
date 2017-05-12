package com.goodguygames.bubblegame.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import com.goodguygames.bubblegame.demo.GameScene;
import com.goodguygames.bubblegame.physics.Direction;
import com.goodguygames.bubblegame.physics.Position;
import com.goodguygames.bubblegame.physics.Velocity;

public abstract class Bubble {

  private Bitmap bitmap;
  private Position currentPosition;
  private Velocity velocity;

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

  public Position getCurrentPosition() {
    return currentPosition;
  }

  public void setCurrentPosition(Position currentPosition) {
    this.currentPosition = currentPosition;
  }

  /**
   * The bubble was touched by the user
   */
  public abstract void setTouched();

  /**
   * Handles the Action Down event. If the event happens on the bitmap surface
   * then the touched state is set to <code>true</code> otherwise to <code>false</code>
   *
   * @param eventX - the event's X coordinate
   * @param eventY - the event's Y coordinate
   */
  public void handleActionDown(int eventX, int eventY) {
    int yCoordinate = this.getCurrentPosition().getyCoordinate();
    int xCoordinate = this.getCurrentPosition().getxCoordinate();
    if (eventY >= (yCoordinate - bitmap.getHeight() / 2) && (eventY <= (yCoordinate + bitmap.getHeight() / 2) + 30) &&
        eventX >= (xCoordinate - bitmap.getWidth() / 2) && (eventX <= (xCoordinate + bitmap.getWidth() / 2))) {
        setTouched();
    }
  }

  public boolean isBubbleOnScreen() {
    if (Direction.UP.equals(this.getVelocity().getDirection())
        && this.getCurrentPosition().getyCoordinate() - this.getBitmap().getHeight() / 2 <= 0) {
      return false;
    }
    else if (Direction.DOWN.equals(this.getVelocity().getDirection())
        && this.getCurrentPosition().getyCoordinate() - this.getBitmap().getHeight() / 2 >= GameScene.screenHeight) {
      return false;
    }
    return true;
  }

  /**
   * Method which updates the droid's internal state every tick
   */
  public void moveBubble() {
    if (Direction.DOWN.equals(velocity.getDirection()) || Direction.UP.equals(velocity.getDirection())) {
      this.getCurrentPosition().setyCoordinate(this.getCurrentPosition().getyCoordinate() + (velocity.getSpeed().value * velocity.getDirection().value));
    }
    else if (Direction.LEFT.equals(velocity.getDirection()) || Direction.RIGHT.equals(velocity.getDirection())) {
      this.getCurrentPosition().setxCoordinate(this.getCurrentPosition().getxCoordinate() + (velocity.getSpeed().value * velocity.getDirection().value));
    }

    if (!isBubbleOnScreen()) {
      bubbleOutOfView();
    }

  }

  public abstract void bubbleOutOfView();

  public abstract void resetBubblePosition();

  public void draw(Canvas canvas) {
    canvas.drawBitmap(bitmap, this.getCurrentPosition().getxCoordinate() - (bitmap.getWidth() / 2), this.getCurrentPosition().getyCoordinate() - (bitmap.getHeight() / 2), null);
  }

}
