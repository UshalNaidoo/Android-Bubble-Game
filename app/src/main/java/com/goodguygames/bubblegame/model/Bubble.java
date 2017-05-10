package com.goodguygames.bubblegame.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by ushal.naidoo on 10/05/17.
 */

public abstract class Bubble {
  private Bitmap bitmap;        // the actual bitmap
  private int x;                        // the X coordinate
  private int y;                        // the Y coordinate
  private boolean touched;        // if droid is touched/picked up
  private Speed speed;        // the speed with its directions


  public Bubble(int velocity, int x, int y) {
    this.x = x;
    this.y = y;
    this.speed = new Speed(velocity, Speed.Direction.UP);
  }

  public Speed getSpeed() {
    return speed;
  }

  public void setSpeed(Speed speed) {
    this.speed = speed;
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

  public boolean isTouched() {
    return touched;
  }

  public void setTouched(boolean touched) {
    this.touched = touched;
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
        setTouched(true);
      } else {
        setTouched(false);
      }
    } else {
      setTouched(false);
    }
  }

  /**
   * Method which updates the droid's internal state every tick
   */
  public void update() {
    if (!touched) {
      y += (speed.getYv() * speed.getyDirection());
    }
  }

  public void draw(Canvas canvas) {
    canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
  }

}
