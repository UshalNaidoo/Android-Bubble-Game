package com.goodguygames.bubblegame.model;

import java.util.Random;

import android.graphics.BitmapFactory;

import com.goodguygames.bubblegame.demo.GameScene;
import com.goodguygames.bubblegame.demo.GamePanel;
import com.goodguygames.bubblegame.demo.MainMenu;
import com.goodguygames.bubblegame.demo.R;
import com.goodguygames.bubblegame.physics.Direction;
import com.goodguygames.bubblegame.physics.Position;
import com.goodguygames.bubblegame.physics.Velocity;

public class Heart extends Bubble {
  private Random random;
  private Position initialPosition;

  public Heart() {
    super();
    this.setBitmap(BitmapFactory.decodeResource(MainMenu.getAppContext().getResources(), R.drawable.heart_bub));
    this.setVelocity(new Velocity(Direction.DOWN));

    random = new Random();
    initialPosition = new Position(random.nextInt(GameScene.screenWidth), 0);
    resetBubblePosition();
  }

  @Override
  public void setTouched() {
    GamePanel.chimeSound();
    GamePanel.gainALife();
    resetBubblePosition();
  }

  @Override
  //TODO Wait a while before resetting heart - it could be determined by mechanics
  public void resetBubblePosition() {
    initialPosition.setyCoordinate(0);
    initialPosition.setxCoordinate(random.nextInt(GameScene.screenWidth));
    this.setCurrentPosition(initialPosition);
  }

  @Override
  public void bubbleOutOfView() {
    resetBubblePosition();
  }

}
