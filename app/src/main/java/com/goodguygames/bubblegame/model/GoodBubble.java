/**
 *
 */
package com.goodguygames.bubblegame.model;

import android.graphics.BitmapFactory;

import com.goodguygames.bubblegame.demo.GameScene;
import com.goodguygames.bubblegame.demo.GamePanel;
import com.goodguygames.bubblegame.demo.R;
import com.goodguygames.bubblegame.physics.Direction;
import com.goodguygames.bubblegame.physics.Position;
import com.goodguygames.bubblegame.physics.Velocity;

import java.util.Random;

public class GoodBubble extends Bubble {

  private Position initialPosition;
  private Random random;

  public GoodBubble() {
    super();
    this.setBitmap(BitmapFactory.decodeResource(GamePanel.getAppContext().getResources(), R.drawable.bubble));
    this.setVelocity(new Velocity(Direction.UP));

    random = new Random();
    initialPosition = new Position(random.nextInt(GameScene.screenWidth), GameScene.screenHeight);
    resetBubblePosition();
  }

  @Override
  public void setTouched() {
    GameScene.score += 1;
    GamePanel.setScore(Integer.toString(GameScene.score));
    GamePanel.popSound();
    resetBubblePosition();
  }

  @Override
  public void resetBubblePosition() {
    initialPosition.setyCoordinate(GameScene.screenHeight);
    initialPosition.setxCoordinate(random.nextInt(GameScene.screenWidth));
    this.setCurrentPosition(initialPosition);
  }

  @Override
  public void bubbleOutOfView() {
    GamePanel.bonkSound();
    GamePanel.loseALife();
    resetBubblePosition();
  }

}
