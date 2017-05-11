package com.goodguygames.bubblegame.model;

import java.util.Random;

import android.graphics.BitmapFactory;

import com.goodguygames.bubblegame.full.MainGamePanel;
import com.goodguygames.bubblegame.full.QuickPlay;
import com.goodguygames.bubblegame.full.R;

public class Heart extends Bubble {
  private Velocity velocity;
  private Random random;

  public Heart() {
    super();
    this.setBitmap(BitmapFactory.decodeResource(QuickPlay.getAppContext().getResources(), R.drawable.heart_bub));
    velocity = new Velocity(1, Direction.DOWN);
    this.setVelocity(velocity);
    resetBubblePosition();
  }

  @Override
  public void setTouched() {
    QuickPlay.chimeSound();
    QuickPlay.gainALife();
    resetBubblePosition();
  }

  @Override
  //TODO Wait a while before resetting heart - it could be determined by mechanics
  public void resetBubblePosition() {
    this.setY(0);
    random = new Random();
    this.setX(random.nextInt(MainGamePanel.screenWidth));
  }

  @Override
  public void bubbleOutOfView() {
    resetBubblePosition();
  }

}
