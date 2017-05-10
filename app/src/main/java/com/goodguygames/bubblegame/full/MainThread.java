/**
 *
 */
package com.goodguygames.bubblegame.full;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

  private SurfaceHolder surfaceHolder;
  private MainGamePanel gamePanel;
  private boolean running;

  public void setRunning(boolean running) {
    this.running = running;
  }

  public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
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
