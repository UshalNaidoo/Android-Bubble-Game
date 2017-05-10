/**
 *
 */
package com.goodguygames.bubblegame.model;

import android.content.Context;
import android.graphics.BitmapFactory;
import com.goodguygames.bubblegame.full.R;

public class GoodBubble extends Bubble {

  public GoodBubble(Context context, int speed, int x, int y) {
    super(x, y);
    this.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.bubble));
    this.setVelocity(new Velocity(speed, Direction.UP));
  }

}
