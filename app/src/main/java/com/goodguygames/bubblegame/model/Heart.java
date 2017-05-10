/**
 *
 */
package com.goodguygames.bubblegame.model;

import android.content.Context;
import android.graphics.BitmapFactory;
import com.goodguygames.bubblegame.full.R;

public class Heart extends Bubble {

  public Heart(Context context, int speed, int x, int y) {
    super(x, y);
    this.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.heart_bub));
    this.setVelocity(new Velocity(speed, Direction.UP));
  }

}
