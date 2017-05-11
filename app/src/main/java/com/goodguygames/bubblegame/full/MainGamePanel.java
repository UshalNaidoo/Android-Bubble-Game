/**
 *
 */
package com.goodguygames.bubblegame.full;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

  public static int score = 0;
  private int lives = 3;
  public static MainThread thread;
  private Bubble goodBubble;
  public static int screenHeight = 0;
  public static int screenWidth = 0;

  public MainGamePanel(Context context) {
    super(context);
    getHolder().addCallback(this);
    setUpScene();
    thread = new MainThread(getHolder(), this);
    setFocusable(true);
  }

  public MainGamePanel(Context context, AttributeSet attrs) {
    super(context, attrs);
    getHolder().addCallback(this);
    setUpScene();
    thread = new MainThread(getHolder(), this);
    setFocusable(true);
  }

  public MainGamePanel(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    getHolder().addCallback(this);
    setUpScene();
    thread = new MainThread(getHolder(), this);
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

  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width,
                             int height) {
  }

  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    if (thread.getState() == Thread.State.TERMINATED) {
      thread = new MainThread(getHolder(), this);
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
  }

  /**
   * This is the game moveBubble method. It iterates through all the objects and calls their moveBubble
   * method if they have one or calls specific engine's moveBubble method.
   */
  public void update() {
    if (goodBubble != null) {
      goodBubble.moveBubble();
      checkHit(goodBubble);
    }
  }

  public void checkHit(Bubble droid) {
    if (!droid.isBubbleOnScreen()) {
      if ((droid == goodBubble)) {
        ((QuickPlay) getContext()).bonkSound();
        loseAlife();
        goodBubble.resetBubblePosition();
      }
      if (lives < 1) {
        Context context = getContext(); // from MySurfaceView/Activity
        Intent intent = new Intent(context, GameOver.class);
        intent.putExtra("score", Integer.toString(score));
        context.startActivity(intent);
        if (thread.isAlive()) {
          thread.setRunning(false);
        }
        ((Activity) getContext()).finish();
      }
    }
  }

  private void loseAlife() {
    lives--;
    ((QuickPlay) getContext()).setLives(lives);
  }

}
