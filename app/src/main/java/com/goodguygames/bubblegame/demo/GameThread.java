package com.goodguygames.bubblegame.demo;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

  private final SurfaceHolder surfaceHolder;
  private GameScene gamePanel;
  private boolean running;

  public void setRunning(boolean running) {
    this.running = running;
  }

  GameThread(SurfaceHolder surfaceHolder, GameScene gamePanel) {
    super();
    this.surfaceHolder = surfaceHolder;
    this.gamePanel = gamePanel;
  }

  @Override
  public void run() {
    Canvas canvas;
    while (running) {
      canvas = this.surfaceHolder.lockCanvas();
      if (canvas != null) {
        try {
          synchronized (surfaceHolder) {
            this.gamePanel.update();
            this.gamePanel.render(canvas);
          }
        } finally {
          surfaceHolder.unlockCanvasAndPost(canvas);
        }
      }
    }
  }
}
