/**
 *
 */
package com.goodguygames.bubblegame.model;

import android.content.Intent;
import android.graphics.BitmapFactory;

import com.goodguygames.bubblegame.full.GameOver;
import com.goodguygames.bubblegame.full.MainGamePanel;
import com.goodguygames.bubblegame.full.QuickPlay;
import com.goodguygames.bubblegame.full.R;

public class PoisonBubble extends Bubble {

  public PoisonBubble(int speed, int x, int y) {
    super(x, y);
    this.setBitmap(BitmapFactory.decodeResource(QuickPlay.getAppContext().getResources(), R.drawable.skull_bub));
    this.setVelocity(new Velocity(speed, Direction.UP));
  }

  @Override
  public void setTouched() {
    Intent intent = new Intent(QuickPlay.getAppContext(), GameOver.class);
    intent.putExtra("score", Integer.toString(MainGamePanel.score));
    QuickPlay.getAppContext().startActivity(intent);
    if (MainGamePanel.thread.isAlive()) {
      MainGamePanel.thread.setRunning(false);
    }
//    MainGamePanel.getGameContext().getApplicationContext().finish();
  }

}
