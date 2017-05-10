/**
 *
 */
package com.goodguygames.bubblegame.model;

import android.graphics.BitmapFactory;
import com.goodguygames.bubblegame.full.MainGamePanel;
import com.goodguygames.bubblegame.full.R;

public class GoodBubble extends Bubble {

  public GoodBubble(int speed, int x, int y) {
    super(x, y);
    this.setBitmap(BitmapFactory.decodeResource(MainGamePanel.getGameContext().getResources(), R.drawable.bubble));
    this.setVelocity(new Velocity(speed, Direction.UP));
  }

}
