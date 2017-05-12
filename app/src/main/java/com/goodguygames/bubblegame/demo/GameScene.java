/**
 *
 */
package com.goodguygames.bubblegame.demo;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.goodguygames.bubblegame.model.Bubble;
import com.goodguygames.bubblegame.model.GoodBubble;
import com.goodguygames.bubblegame.model.Heart;
import com.goodguygames.bubblegame.model.PoisonBubble;

public class GameScene extends SurfaceView implements SurfaceHolder.Callback {

  public static int score = 0;
  public static int maxLives = 10;
  public static int lives = 3;
  public static GameThread thread;
  private Bubble goodBubble;
  private Bubble heart;
  private Bubble poisonBubble;
  public static int screenHeight = 0;
  public static int screenWidth = 0;

  public GameScene(Context context) {
    super(context);
    getHolder().addCallback(this);
    setUpScene();
    thread = new GameThread(getHolder(), this);
    setFocusable(true);
  }

  public GameScene(Context context, AttributeSet attrs) {
    super(context, attrs);
    getHolder().addCallback(this);
    setUpScene();
    thread = new GameThread(getHolder(), this);
    setFocusable(true);
  }

  public GameScene(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    getHolder().addCallback(this);
    setUpScene();
    thread = new GameThread(getHolder(), this);
    setFocusable(true);
  }

  /**
   * Split the screen into a grid of 5 columns.
   */
  private void setUpScene() {

    DisplayMetrics metrics = new DisplayMetrics();
    ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
    screenHeight = metrics.heightPixels;
    screenWidth = metrics.widthPixels;
    goodBubble = new GoodBubble();
    poisonBubble = new PoisonBubble();
    heart = new Heart();

  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width,
                             int height) {
  }

  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    if (thread.getState() == Thread.State.TERMINATED) {
      thread = new GameThread(getHolder(), this);
      thread.setRunning(true);
      thread.start();
    } else {
      thread.setRunning(true);
      thread.start();
    }
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
    thread.setRunning(false);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      // delegating event handling to the droid
      if (goodBubble != null) {
        goodBubble.handleActionDown((int) event.getX(), (int) event.getY());
      }
      if (heart != null) {
        heart.handleActionDown((int) event.getX(), (int) event.getY());
      }
      if(poisonBubble != null) {
        poisonBubble.handleActionDown((int) event.getX(), (int) event.getY());
      }
    }
    return true;
  }

  public void render(Canvas canvas) {
    Rect dest = new Rect(0, 0, getWidth(), getHeight());
    Paint paint = new Paint();
    paint.setFilterBitmap(true);
    if (canvas != null) {
      canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.background1), null, dest, paint);
    }
    if (goodBubble != null) {
      goodBubble.draw(canvas);
    }
    if (heart != null) {
      heart.draw(canvas);
    }
    if (poisonBubble != null) {
      poisonBubble.draw(canvas);
    }
  }

  /**
   * This is the game moveBubble method. It iterates through all the objects and calls their moveBubble
   * method if they have one or calls specific engine's moveBubble method.
   */
  public void update() {
    if (goodBubble != null) {
      goodBubble.moveBubble();
    }
    if (heart != null) {
      heart.moveBubble();
    }
    if (poisonBubble != null) {
      poisonBubble.moveBubble();
    }
  }

}
