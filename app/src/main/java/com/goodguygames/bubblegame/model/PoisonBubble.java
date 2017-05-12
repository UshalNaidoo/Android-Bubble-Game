/**
 *
 */
package com.goodguygames.bubblegame.model;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;

import com.goodguygames.bubblegame.demo.GameOver;
import com.goodguygames.bubblegame.demo.GameScene;
import com.goodguygames.bubblegame.demo.GamePanel;
import com.goodguygames.bubblegame.demo.MainMenu;
import com.goodguygames.bubblegame.demo.R;
import com.goodguygames.bubblegame.physics.Direction;
import com.goodguygames.bubblegame.physics.Position;
import com.goodguygames.bubblegame.physics.Velocity;

import java.util.Random;

public class PoisonBubble extends Bubble {

  private Velocity velocity;

  public PoisonBubble() {
    super();
    this.setBitmap(BitmapFactory.decodeResource(MainMenu.getAppContext().getResources(), R.drawable.skull_bub));
    velocity = new Velocity(Direction.UP);
    this.setVelocity(velocity);
    Random random = new Random();
    Position initialPosition = new Position(random.nextInt(GameScene.screenWidth), GameScene.screenHeight);
    this.setCurrentPosition(initialPosition);
  }

  @Override
  public void setTouched() {
    Intent intent = new Intent(MainMenu.getAppContext(), GameOver.class);
    intent.putExtra("score", Integer.toString(GameScene.score));
    MainMenu.getAppContext().startActivity(intent);
    if (GameScene.thread.isAlive()) {
      GameScene.thread.setRunning(false);
    }
    ((Activity) MainMenu.getAppContext()).finish();
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
