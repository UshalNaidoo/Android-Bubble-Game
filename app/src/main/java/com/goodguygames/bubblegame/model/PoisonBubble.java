/**
 *
 */
package com.goodguygames.bubblegame.model;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;

import com.goodguygames.bubblegame.full.GameOver;
import com.goodguygames.bubblegame.full.GameScene;
import com.goodguygames.bubblegame.full.GamePanel;
import com.goodguygames.bubblegame.full.R;

import java.util.Random;

public class PoisonBubble extends Bubble {

  private Velocity velocity;
  private Random random;

  public PoisonBubble() {
    super();
    this.setBitmap(BitmapFactory.decodeResource(GamePanel.getAppContext().getResources(), R.drawable.skull_bub));
    velocity = new Velocity(1, Direction.UP);
    this.setVelocity(velocity);
    this.setyCoordinate(GameScene.screenHeight);
    random = new Random();
    this.setxCoordinate(random.nextInt(GameScene.screenWidth));
  }

  @Override
  public void setTouched() {
    Intent intent = new Intent(GamePanel.getAppContext(), GameOver.class);
    intent.putExtra("score", Integer.toString(GameScene.score));
    GamePanel.getAppContext().startActivity(intent);
    if (GameScene.thread.isAlive()) {
      GameScene.thread.setRunning(false);
    }
    ((Activity) GamePanel.getAppContext()).finish();
  }

  @Override
  public void resetBubblePosition() {
    velocity.toggleVerticalDirection();
  }

  @Override
  public void bubbleOutOfView() {
    resetBubblePosition();
  }

}
