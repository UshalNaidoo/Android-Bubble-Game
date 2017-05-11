/**
 *
 */
package com.goodguygames.bubblegame.model;

import java.util.Random;

import android.graphics.BitmapFactory;

import com.goodguygames.bubblegame.full.MainGamePanel;
import com.goodguygames.bubblegame.full.QuickPlay;
import com.goodguygames.bubblegame.full.R;

public class GoodBubble extends Bubble {

  private Velocity velocity;
  Random random;

  public GoodBubble() {
    super();
    this.setBitmap(BitmapFactory.decodeResource(QuickPlay.getAppContext().getResources(), R.drawable.bubble));

    velocity = new Velocity(1, Direction.UP);
    this.setVelocity(velocity);
    resetBubblePosition();
  }

  @Override
  public void setTouched() {
//    QuickPlay.popSound();
//    QuickPlay.setPoints(Integer.toString(MainGamePanel.score++));
    resetBubblePosition();
  }

  @Override
  public void resetBubblePosition() {
    this.setY(MainGamePanel.screenHeight);
    random = new Random();
    this.setX(random.nextInt(MainGamePanel.screenWidth));
  }

}
