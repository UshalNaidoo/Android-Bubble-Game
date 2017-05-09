/**
 *
 */
package com.goodguygames.bubblegame.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;

import com.goodguygames.bubblegame.components.HeartSpeed;
import com.goodguygames.bubblegame.components.Speed;

/**
 * This is a test droid that is dragged, dropped, moved, smashed against the wall and done other
 * terrible things with. Wait till it gets a weapon!
 *
 * @author impaler
 */
public class Heart {

  private Bitmap bitmap;        // the actual bitmap
  private int x;                        // the X coordinate
  private int y;                        // the Y coordinate
  private boolean touched;        // if droid is touched/picked up
  private HeartSpeed speed;        // the speed with its directions

  public Heart(Bitmap bitmap, int i, int x, int y) {
    this.bitmap = bitmap;
    this.x = x;
    this.y = y;
    this.speed = new HeartSpeed(i);
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

  public HeartSpeed getSpeed() {
    return speed;
  }

  public void setSpeed(HeartSpeed speed) {
    this.speed = speed;
  }

  public void draw(Canvas canvas) {
    canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);

  }

  /**
   * Method which updates the droid's internal state every tick
   */
  public void update() {
    if (!touched) {
      //		x += (speed.getXv() * speed.getxDirection());
      y += (speed.getYv() * speed.getyDirection());
    }
  }


  /**
   * Handles the {@link MotionEvent.ACTION_DOWN} event. If the event happens on the bitmap surface
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
}
