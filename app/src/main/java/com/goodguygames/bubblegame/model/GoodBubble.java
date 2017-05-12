/**
 *
 */
package com.goodguygames.bubblegame.model;

import android.graphics.BitmapFactory;

import com.goodguygames.bubblegame.full.GameScene;
import com.goodguygames.bubblegame.full.GamePanel;
import com.goodguygames.bubblegame.full.R;

import java.util.Random;

public class GoodBubble extends Bubble {

  private Velocity velocity;
  private Random random;

  public GoodBubble() {
    super();
    this.setBitmap(BitmapFactory.decodeResource(GamePanel.getAppContext().getResources(), R.drawable.bubble));

    velocity = new Velocity(1, Direction.UP);
    this.setVelocity(velocity);
    resetBubblePosition();
  }

  @Override
  public void setTouched() {
    GamePanel.popSound();
    GameScene.score += 1;
    GamePanel.setScore(Integer.toString(GameScene.score));
    resetBubblePosition();
  }

  @Override
  public void resetBubblePosition() {
    this.setyCoordinate(GameScene.screenHeight);
    random = new Random();
    this.setxCoordinate(random.nextInt(GameScene.screenWidth));
  }

  @Override
  public void bubbleOutOfView() {
    GamePanel.bonkSound();
    GamePanel.loseALife();
    resetBubblePosition();
  }

}
