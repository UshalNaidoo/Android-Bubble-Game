/**
 *
 */
package com.goodguygames.bubblegame.model;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;

import com.goodguygames.bubblegame.full.GameOver;
import com.goodguygames.bubblegame.full.MainGamePanel;
import com.goodguygames.bubblegame.full.QuickPlay;
import com.goodguygames.bubblegame.full.R;

import java.util.Random;

public class PoisonBubble extends Bubble {

  private Velocity velocity;
  private Random random;

  public PoisonBubble() {
    super();
    this.setBitmap(BitmapFactory.decodeResource(QuickPlay.getAppContext().getResources(), R.drawable.skull_bub));
    velocity = new Velocity(1, Direction.UP);
    this.setVelocity(velocity);
    this.setY(MainGamePanel.screenHeight);
    random = new Random();
    this.setX(random.nextInt(MainGamePanel.screenWidth));
  }

  @Override
  public void setTouched() {
    Intent intent = new Intent(QuickPlay.getAppContext(), GameOver.class);
    intent.putExtra("score", Integer.toString(MainGamePanel.score));
    QuickPlay.getAppContext().startActivity(intent);
    if (MainGamePanel.thread.isAlive()) {
      MainGamePanel.thread.setRunning(false);
    }
    ((Activity)QuickPlay.getAppContext()).finish();
  }

  @Override
  public void resetBubblePosition() {
    //TODO change that velocity direction is an int and not a Direction
    velocity.setyDirection(velocity.getyDirection() == 1 ? -1 : 1);
  }

  @Override
  public void bubbleOutOfView() {
    resetBubblePosition();
  }

}
