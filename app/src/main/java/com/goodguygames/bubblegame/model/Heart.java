/**
 *
 */
package com.goodguygames.bubblegame.model;

import android.graphics.BitmapFactory;

import com.goodguygames.bubblegame.full.QuickPlay;
import com.goodguygames.bubblegame.full.R;

public class Heart extends Bubble {

  public Heart(int speed, int x, int y) {
    super(x, y);
    this.setBitmap(BitmapFactory.decodeResource(QuickPlay.getAppContext().getResources(), R.drawable.heart_bub));
    this.setVelocity(new Velocity(speed, Direction.UP));
  }

  @Override
  public void setTouched() {
//    ((QuickPlay.getContext()).chimeSound();
//    lives++;
//    if (lives > maxlives) {
//      lives = maxlives;
//    }
//    ((QuickPlay) getContext()).setLives(lives);
//    heart1 = null;

  }
}
