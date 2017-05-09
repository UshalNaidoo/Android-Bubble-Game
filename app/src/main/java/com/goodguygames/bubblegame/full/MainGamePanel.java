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

import com.goodguygames.bubblegame.components.HeartSpeed;
import com.goodguygames.bubblegame.components.Speed;
import com.goodguygames.bubblegame.model.Bub1;
import com.goodguygames.bubblegame.model.Heart;

import java.util.Random;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

  private static final String TAG = "fb";
  private int scr = 0;
  private int lives = 3;
  private int maxlives = 5;
  private MainThread thread;
  private Bub1 bubble1, bubble2, bubble3, bubble4, bubble5;
  private Bub1 bubble2_1, bubble2_2, bubble2_3, bubble2_4, bubble2_5;
  private Bub1 bubble5_1, bubble5_2, bubble5_3, bubble5_4, bubble5_5;
  private Bub1 bomb1 = null, bomb2 = null, bomb3 = null, bomb4 = null, bomb5 = null;
  private Heart heart1 = null, heart2 = null, heart3 = null, heart4 = null, heart5 = null;
  private int screenHeight = 0;
  private int screenWidth = 0;
  private int spacer = 0;
  private int space1, space2, space3, space4, space5;
  private int speedcount = 1;
  private int modcounter = 1;
  private int modcounter1 = 1;
  private int percheart = 1;

  public MainGamePanel(Context context) {
    super(context);
    getHolder().addCallback(this);

    DisplayMetrics metrics = new DisplayMetrics();
    ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
    screenHeight = metrics.heightPixels;
    screenWidth = metrics.widthPixels;
    spacer = screenWidth / 5;
    space1 = spacer - 40;
    space2 = space1 + spacer;
    space3 = space2 + spacer;
    space4 = space3 + spacer;
    space5 = space4 + spacer;

    bubble1 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space1,
                       screenHeight + 500);
    bubble2 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space2,
                       screenHeight + 500);
    bubble3 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space3, screenHeight);
    bubble4 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space4,
                       screenHeight + 500);
    bubble5 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space5,
                       screenHeight + 500);
    // create the game loop thread
    thread = new MainThread(getHolder(), this);
    // make the GamePanel focusable so it can handle events
    setFocusable(true);
  }

  public MainGamePanel(Context context, AttributeSet attrs) {
    super(context, attrs);
    getHolder().addCallback(this);

    // create droid and load bitmap
    DisplayMetrics metrics = new DisplayMetrics();
    ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
    screenHeight = metrics.heightPixels;
    screenWidth = metrics.widthPixels;
    spacer = screenWidth / 5;
    space1 = spacer - 40;
    space2 = space1 + spacer;
    space3 = space2 + spacer;
    space4 = space3 + spacer;
    space5 = space4 + spacer;

    bubble1 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space1,
                       screenHeight + 500);
    bubble2 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space2,
                       screenHeight + 500);
    bubble3 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space3, screenHeight);
    bubble4 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space4,
                       screenHeight + 500);
    bubble5 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space5,
                       screenHeight + 500);
    // create the game loop thread
    thread = new MainThread(getHolder(), this);
    // make the GamePanel focusable so it can handle events
    setFocusable(true);
  }

  public MainGamePanel(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    getHolder().addCallback(this);
    // create droid and load bitmap

    DisplayMetrics metrics = new DisplayMetrics();
    ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
    screenHeight = metrics.heightPixels;
    screenWidth = metrics.widthPixels;
    spacer = screenWidth / 5;
    space1 = spacer - 40;
    space2 = space1 + spacer;
    space3 = space2 + spacer;
    space4 = space3 + spacer;
    space5 = space4 + spacer;

    bubble1 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space1,
                       screenHeight + 500);
    bubble2 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space2,
                       screenHeight + 500);
    bubble3 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space3, screenHeight);
    bubble4 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space4,
                       screenHeight + 500);
    bubble5 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space5,
                       screenHeight + 500);
    // create the game loop thread
    thread = new MainThread(getHolder(), this);

    // make the GamePanel focusable so it can handle events
    setFocusable(true);
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
    // tell the thread to shut down and wait for it to finish
    // this is a clean shutdown
    boolean retry = true;
    while (retry) {
      try {
        thread.setRunning(false);
        retry = false;
      } catch (Exception e) {
        // try again shutting down the thread
      }
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      // delegating event handling to the droid
      if (bubble1 != null) {
        bubble1.handleActionDown((int) event.getX(), (int) event.getY());

        if (bubble1.isTouched()) {

          scr++;
          ((QuickPlay) getContext()).popSound();
          ((QuickPlay) getContext()).setScore(Integer.toString(scr));
          bubble1.setTouched(false);
          bubble1 = null;
          checkSpeed();
          Random ran = new Random();
          int perc = ran.nextInt(100);
          int perc2 = ran.nextInt(100 + percheart);
          if (perc < 9) {
            bomb1 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.skull_bub), speedcount, space1,
                             screenHeight + 50);
          } else if ((perc2 > 9) && (perc2 < 12)) {
            heart1 = new Heart(BitmapFactory.decodeResource(getResources(), R.drawable.heart_bub), speedcount, space1, 0);
          } else {
            int perc1 = ran.nextInt(100);
            if (perc1 < 5) {
              bubble2_1 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble_2),
                                   speedcount + 2, space1, screenHeight + 50);
            } else if ((perc1 > 9) && (perc1 < 12)) {
              bubble5_1 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble_5),
                                   speedcount + 4, space1, screenHeight + 50);
            } else {
              bubble1 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space1,
                                 screenHeight + 50);
            }
          }
        }
      }
      if (bubble2 != null) {
        bubble2.handleActionDown((int) event.getX(), (int) event.getY());
        if (bubble2.isTouched()) {
          ((QuickPlay) getContext()).popSound();
          scr++;
          ((QuickPlay) getContext()).setScore(Integer.toString(scr));
          bubble2.setTouched(false);
          bubble2 = null;
          checkSpeed();
          Random ran = new Random();
          int perc = ran.nextInt(100);
          int perc2 = ran.nextInt(100 + percheart);
          if (perc < 9) {
            bomb2 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.skull_bub), speedcount, space2,
                             screenHeight + 50);
          } else if ((perc2 > 9) && (perc2 < 12)) {
            heart2 = new Heart(BitmapFactory.decodeResource(getResources(), R.drawable.heart_bub), speedcount, space2, 0);
          } else {
            int perc1 = ran.nextInt(100);
            if (perc1 < 5) {
              bubble2_2 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble_2),
                                   speedcount + 2, space2, screenHeight + 50);
            } else if ((perc1 > 9) && (perc1 < 12)) {
              bubble5_2 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble_5),
                                   speedcount + 4, space2, screenHeight + 50);
            } else {
              bubble2 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space2,
                                 screenHeight + 50);
            }
          }
        }
      }
      if (bubble3 != null) {
        bubble3.handleActionDown((int) event.getX(), (int) event.getY());
        if (bubble3.isTouched()) {
          ((QuickPlay) getContext()).popSound();
          scr++;
          ((QuickPlay) getContext()).setScore(Integer.toString(scr));
          bubble3.setTouched(false);
          bubble3 = null;
          checkSpeed();
          Random ran = new Random();
          int perc = ran.nextInt(100);
          int perc2 = ran.nextInt(100 + percheart);
          if (perc < 9) {
            bomb3 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.skull_bub), speedcount, space3,
                             screenHeight + 50);
          } else if ((perc2 > 9) && (perc2 < 12)) {
            heart3 = new Heart(BitmapFactory.decodeResource(getResources(), R.drawable.heart_bub), speedcount, space3, 0);
          } else {
            int perc1 = ran.nextInt(100);
            if (perc1 < 5) {
              bubble2_3 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble_2),
                                   speedcount + 2, space3, screenHeight + 50);
            } else if ((perc1 > 9) && (perc1 < 12)) {
              bubble5_3 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble_5),
                                   speedcount + 4, space3, screenHeight + 50);
            } else {
              bubble3 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space3,
                                 screenHeight + 50);
            }
          }
        }
      }
      if (bubble4 != null) {
        bubble4.handleActionDown((int) event.getX(), (int) event.getY());
        if (bubble4.isTouched()) {
          ((QuickPlay) getContext()).popSound();
          scr++;
          ((QuickPlay) getContext()).setScore(Integer.toString(scr));
          bubble4.setTouched(false);
          bubble4 = null;
          checkSpeed();
          Random ran = new Random();
          int perc = ran.nextInt(100);
          int perc2 = ran.nextInt(100 + percheart);
          if (perc < 9) {
            bomb4 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.skull_bub), speedcount, space4,
                             screenHeight + 50);
          } else if ((perc2 > 9) && (perc2 < 12)) {
            heart4 = new Heart(BitmapFactory.decodeResource(getResources(), R.drawable.heart_bub), speedcount, space4, 0);
          } else {
            int perc1 = ran.nextInt(100);
            if (perc1 < 5) {
              bubble2_4 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble_2),
                                   speedcount + 2, space4, screenHeight + 50);
            } else if ((perc1 > 9) && (perc1 < 12)) {
              bubble5_4 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble_5),
                                   speedcount + 4, space4, screenHeight + 50);
            } else {
              bubble4 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space4,
                                 screenHeight + 50);
            }
          }
        }
      }
      if (bubble5 != null) {
        bubble5.handleActionDown((int) event.getX(), (int) event.getY());
        if (bubble5.isTouched()) {
          ((QuickPlay) getContext()).popSound();
          scr++;
          ((QuickPlay) getContext()).setScore(Integer.toString(scr));
          bubble5.setTouched(false);
          bubble5 = null;
          checkSpeed();
          Random ran = new Random();
          int perc = ran.nextInt(100);
          int perc2 = ran.nextInt(100 + percheart);
          if (perc < 9) {
            bomb5 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.skull_bub), speedcount, space5,
                             screenHeight + 50);
          } else if ((perc2 > 9) && (perc2 < 12)) {
            heart5 = new Heart(BitmapFactory.decodeResource(getResources(), R.drawable.heart_bub), speedcount, space5, 0);
          } else {
            int perc1 = ran.nextInt(100);
            if (perc1 < 5) {
              bubble2_5 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble_2), speedcount, space5,
                                   screenHeight + 50);
            } else if ((perc1 > 9) && (perc1 < 12)) {
              bubble5_5 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble_5), speedcount, space5,
                                   screenHeight + 50);
            } else {
              bubble5 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space5,
                                 screenHeight + 50);
            }
          }
        }
      }

      if (bubble2_1 != null) {
        bubble2_1.handleActionDown((int) event.getX(), (int) event.getY());

        if (bubble2_1.isTouched()) {
          ((QuickPlay) getContext()).popSound();
          scr = scr + 2;
          ((QuickPlay) getContext()).setScore(Integer.toString(scr));
          bubble2_1.setTouched(false);
          bubble2_1 = null;
          checkSpeed();
          bubble1 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space1, screenHeight);

        }
      }
      if (bubble2_2 != null) {
        bubble2_2.handleActionDown((int) event.getX(), (int) event.getY());

        if (bubble2_2.isTouched()) {
          ((QuickPlay) getContext()).popSound();
          scr = scr + 2;
          ((QuickPlay) getContext()).setScore(Integer.toString(scr));
          bubble2_2.setTouched(false);
          bubble2_2 = null;
          checkSpeed();
          bubble2 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space2, screenHeight);

        }
      }
      if (bubble2_3 != null) {
        bubble2_3.handleActionDown((int) event.getX(), (int) event.getY());

        if (bubble2_3.isTouched()) {
          ((QuickPlay) getContext()).popSound();
          scr = scr + 2;
          ((QuickPlay) getContext()).setScore(Integer.toString(scr));
          bubble2_3.setTouched(false);
          bubble2_3 = null;
          checkSpeed();
          bubble3 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space3, screenHeight);

        }
      }
      if (bubble2_4 != null) {
        bubble2_4.handleActionDown((int) event.getX(), (int) event.getY());

        if (bubble2_4.isTouched()) {
          ((QuickPlay) getContext()).popSound();
          scr = scr + 2;
          ((QuickPlay) getContext()).setScore(Integer.toString(scr));
          bubble2_4.setTouched(false);
          bubble2_4 = null;
          checkSpeed();
          bubble4 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space4, screenHeight);

        }
      }
      if (bubble2_5 != null) {
        bubble2_5.handleActionDown((int) event.getX(), (int) event.getY());

        if (bubble2_5.isTouched()) {
          ((QuickPlay) getContext()).popSound();
          scr = scr + 2;
          ((QuickPlay) getContext()).setScore(Integer.toString(scr));
          bubble2_5.setTouched(false);
          bubble2_5 = null;
          checkSpeed();
          bubble5 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space5, screenHeight);

        }
      }

      if (bubble5_1 != null) {
        bubble5_1.handleActionDown((int) event.getX(), (int) event.getY());

        if (bubble5_1.isTouched()) {
          ((QuickPlay) getContext()).popSound();
          scr = scr + 5;
          ((QuickPlay) getContext()).setScore(Integer.toString(scr));
          bubble5_1.setTouched(false);
          bubble5_1 = null;
          checkSpeed();
          bubble1 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space1, screenHeight);

        }
      }
      if (bubble5_2 != null) {
        bubble5_2.handleActionDown((int) event.getX(), (int) event.getY());

        if (bubble5_2.isTouched()) {
          ((QuickPlay) getContext()).popSound();
          scr = scr + 5;
          ((QuickPlay) getContext()).setScore(Integer.toString(scr));
          bubble5_2.setTouched(false);
          bubble5_2 = null;
          checkSpeed();
          bubble2 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space2, screenHeight);

        }
      }
      if (bubble5_3 != null) {
        bubble5_3.handleActionDown((int) event.getX(), (int) event.getY());

        if (bubble5_3.isTouched()) {
          ((QuickPlay) getContext()).popSound();
          scr = scr + 5;
          ((QuickPlay) getContext()).setScore(Integer.toString(scr));
          bubble5_3.setTouched(false);
          bubble5_3 = null;
          checkSpeed();
          bubble3 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space3, screenHeight);

        }
      }
      if (bubble5_4 != null) {
        bubble5_4.handleActionDown((int) event.getX(), (int) event.getY());

        if (bubble5_4.isTouched()) {
          ((QuickPlay) getContext()).popSound();
          scr = scr + 5;
          ((QuickPlay) getContext()).setScore(Integer.toString(scr));
          bubble5_4.setTouched(false);
          bubble5_4 = null;
          checkSpeed();
          bubble4 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space4, screenHeight);

        }
      }
      if (bubble5_5 != null) {
        bubble5_5.handleActionDown((int) event.getX(), (int) event.getY());

        if (bubble5_5.isTouched()) {
          ((QuickPlay) getContext()).popSound();
          scr = scr + 5;
          ((QuickPlay) getContext()).setScore(Integer.toString(scr));
          bubble5_5.setTouched(false);
          bubble5_5 = null;
          checkSpeed();
          bubble5 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space5, screenHeight);

        }
      }

      //BOMBS
      if (bomb1 != null) {
        bomb1.handleActionDown((int) event.getX(), (int) event.getY());
        if (bomb1.isTouched()) {
          //boom

          Context context = getContext(); // from MySurfaceView/Activity
          Intent intent = new Intent(context, GameOver.class);
          intent.putExtra("score", Integer.toString(scr));
          context.startActivity(intent);
          if (thread.isAlive()) {
            thread.setRunning(false);
          }
          ((Activity) getContext()).finish();

        }
      }
      if (bomb2 != null) {
        bomb2.handleActionDown((int) event.getX(), (int) event.getY());
        if (bomb2.isTouched()) {
          //boom

          Context context = getContext(); // from MySurfaceView/Activity
          Intent intent = new Intent(context, GameOver.class);
          intent.putExtra("score", Integer.toString(scr));
          context.startActivity(intent);
          if (thread.isAlive()) {
            thread.setRunning(false);
          }
          ((Activity) getContext()).finish();

        }
      }
      if (bomb3 != null) {
        bomb3.handleActionDown((int) event.getX(), (int) event.getY());
        if (bomb3.isTouched()) {
          //boom

          Context context = getContext(); // from MySurfaceView/Activity
          Intent intent = new Intent(context, GameOver.class);
          intent.putExtra("score", Integer.toString(scr));
          context.startActivity(intent);
          if (thread.isAlive()) {
            thread.setRunning(false);
          }
          ((Activity) getContext()).finish();

        }
      }
      if (bomb4 != null) {
        bomb4.handleActionDown((int) event.getX(), (int) event.getY());
        if (bomb4.isTouched()) {
          //boom

          Context context = getContext(); // from MySurfaceView/Activity
          Intent intent = new Intent(context, GameOver.class);
          intent.putExtra("score", Integer.toString(scr));
          context.startActivity(intent);
          if (thread.isAlive()) {
            thread.setRunning(false);
          }
          ((Activity) getContext()).finish();

        }
      }
      if (bomb5 != null) {
        bomb5.handleActionDown((int) event.getX(), (int) event.getY());
        if (bomb5.isTouched()) {
          //boom

          Context context = getContext(); // from MySurfaceView/Activity
          Intent intent = new Intent(context, GameOver.class);
          intent.putExtra("score", Integer.toString(scr));
          context.startActivity(intent);
          if (thread.isAlive()) {
            thread.setRunning(false);
          }
          ((Activity) getContext()).finish();

        }
      }

      //Heart

      if (heart1 != null) {
        heart1.handleActionDown((int) event.getX(), (int) event.getY());

        if (heart1.isTouched()) {
          ((QuickPlay) getContext()).chimeSound();
          lives++;
          if (lives > maxlives) {
            lives = maxlives;
          }
          //((QuickPlay) getContext()).setLives(Integer.toString(lives));
          ((QuickPlay) getContext()).setLives(lives);
          heart1.setTouched(false);
          heart1 = null;

          bubble1 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space1, screenHeight);

        }
      }
      if (heart2 != null) {
        heart2.handleActionDown((int) event.getX(), (int) event.getY());

        if (heart2.isTouched()) {
          ((QuickPlay) getContext()).chimeSound();
          lives++;
          if (lives > maxlives) {
            lives = maxlives;
          }
          //((QuickPlay) getContext()).setLives(Integer.toString(lives));
          ((QuickPlay) getContext()).setLives(lives);
          heart2.setTouched(false);
          heart2 = null;

          bubble2 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space2, screenHeight);

        }
      }
      if (heart3 != null) {
        heart3.handleActionDown((int) event.getX(), (int) event.getY());

        if (heart3.isTouched()) {
          ((QuickPlay) getContext()).chimeSound();
          lives++;
          if (lives > maxlives) {
            lives = maxlives;
          }
          //((QuickPlay) getContext()).setLives(Integer.toString(lives));
          ((QuickPlay) getContext()).setLives(lives);
          heart3.setTouched(false);
          heart3 = null;

          bubble3 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space3, screenHeight);

        }
      }
      if (heart4 != null) {
        heart4.handleActionDown((int) event.getX(), (int) event.getY());

        if (heart4.isTouched()) {
          ((QuickPlay) getContext()).chimeSound();
          lives++;
          if (lives > maxlives) {
            lives = maxlives;
          }
          //((QuickPlay) getContext()).setLives(Integer.toString(lives));
          ((QuickPlay) getContext()).setLives(lives);
          heart4.setTouched(false);
          heart4 = null;

          bubble4 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space4, screenHeight);

        }
      }
      if (heart5 != null) {
        heart5.handleActionDown((int) event.getX(), (int) event.getY());

        if (heart5.isTouched()) {
          ((QuickPlay) getContext()).chimeSound();
          lives++;
          if (lives > maxlives) {
            lives = maxlives;
          }
          //((QuickPlay) getContext()).setLives(Integer.toString(lives));
          ((QuickPlay) getContext()).setLives(lives);
          heart5.setTouched(false);
          heart5 = null;

          bubble5 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space5, screenHeight);

        }
      }
    }
    return true;
  }

  public void render(Canvas canvas) {
    //canvas.drawRGB(213, 243, 243);
    Rect dest = new Rect(0, 0, getWidth(), getHeight());
    Paint paint = new Paint();
    paint.setFilterBitmap(true);
    if (canvas != null) {
      canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.background1), null, dest, paint);
    }                //canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.background1), 0, 0, null);
    if (bubble1 != null) {
      bubble1.draw(canvas);
    }
    if (bubble2 != null) {
      bubble2.draw(canvas);
    }
    if (bubble3 != null) {
      bubble3.draw(canvas);
    }
    if (bubble4 != null) {
      bubble4.draw(canvas);
    }
    if (bubble5 != null) {
      bubble5.draw(canvas);
    }

    if (bubble2_1 != null) {
      bubble2_1.draw(canvas);
    }
    if (bubble2_2 != null) {
      bubble2_2.draw(canvas);
    }
    if (bubble2_3 != null) {
      bubble2_3.draw(canvas);
    }
    if (bubble2_4 != null) {
      bubble2_4.draw(canvas);
    }
    if (bubble2_5 != null) {
      bubble2_5.draw(canvas);
    }

    if (bubble5_1 != null) {
      bubble5_1.draw(canvas);
    }
    if (bubble5_2 != null) {
      bubble5_2.draw(canvas);
    }
    if (bubble5_3 != null) {
      bubble5_3.draw(canvas);
    }
    if (bubble5_4 != null) {
      bubble5_4.draw(canvas);
    }
    if (bubble5_5 != null) {
      bubble5_5.draw(canvas);
    }

    if (bomb1 != null) {
      bomb1.draw(canvas);
    }
    if (bomb2 != null) {
      bomb2.draw(canvas);
    }
    if (bomb3 != null) {
      bomb3.draw(canvas);
    }
    if (bomb4 != null) {
      bomb4.draw(canvas);
    }
    if (bomb5 != null) {
      bomb5.draw(canvas);
    }

    if (heart1 != null) {
      heart1.draw(canvas);
    }
    if (heart2 != null) {
      heart2.draw(canvas);
    }
    if (heart3 != null) {
      heart3.draw(canvas);
    }
    if (heart4 != null) {
      heart4.draw(canvas);
    }
    if (heart5 != null) {
      heart5.draw(canvas);
    }
  }

  /**
   * This is the game update method. It iterates through all the objects and calls their update
   * method if they have one or calls specific engine's update method.
   */
  public void update() {
    // check collision with top wall if heading up
    if (bubble1 != null) {
      bubble1.update();
      checkHit(bubble1);
    }
    if (bubble2 != null) {
      bubble2.update();
      checkHit(bubble2);
    }
    if (bubble3 != null) {
      bubble3.update();
      checkHit(bubble3);
    }
    if (bubble4 != null) {
      bubble4.update();
      checkHit(bubble4);
    }
    if (bubble5 != null) {
      bubble5.update();
      checkHit(bubble5);
    }

    if (bubble2_1 != null) {
      bubble2_1.update();
      checkHit(bubble2_1);
    }
    if (bubble2_2 != null) {
      bubble2_2.update();
      checkHit(bubble2_2);
    }
    if (bubble2_3 != null) {
      bubble2_3.update();
      checkHit(bubble2_3);
    }
    if (bubble2_4 != null) {
      bubble2_4.update();
      checkHit(bubble2_4);
    }
    if (bubble2_5 != null) {
      bubble2_5.update();
      checkHit(bubble2_5);
    }

    if (bubble5_1 != null) {
      bubble5_1.update();
      checkHit(bubble5_1);
    }
    if (bubble5_2 != null) {
      bubble5_2.update();
      checkHit(bubble5_2);
    }
    if (bubble5_3 != null) {
      bubble5_3.update();
      checkHit(bubble5_3);
    }
    if (bubble5_4 != null) {
      bubble5_4.update();
      checkHit(bubble5_4);
    }
    if (bubble5_5 != null) {
      bubble5_5.update();
      checkHit(bubble5_5);
    }

    if (bomb1 != null) {
      bomb1.update();
      checkHit(bomb1);
    }
    if (bomb2 != null) {
      bomb2.update();
      checkHit(bomb2);
    }
    if (bomb3 != null) {
      bomb3.update();
      checkHit(bomb3);
    }
    if (bomb4 != null) {
      bomb4.update();
      checkHit(bomb4);
    }
    if (bomb5 != null) {
      bomb5.update();
      checkHit(bomb5);
    }

    if (heart1 != null) {
      heart1.update();
      checkHit1(heart1);
    }
    if (heart2 != null) {
      heart2.update();
      checkHit1(heart2);
    }
    if (heart3 != null) {
      heart3.update();
      checkHit1(heart3);
    }
    if (heart4 != null) {
      heart4.update();
      checkHit1(heart4);
    }
    if (heart5 != null) {
      heart5.update();
      checkHit1(heart5);
    }
  }

  public void checkHit(Bub1 droid) {
    if (droid.getSpeed().getyDirection() == Speed.DIRECTION_UP
        && droid.getY() - droid.getBitmap().getHeight() / 2 <= 0) {
      //droid.getSpeed().toggleYDirection();

      if ((droid == bubble1) || (droid == bubble2_1) || (droid == bubble5_1)) {
        ((QuickPlay) getContext()).bonkSound();
        loseAlife();
        if (droid == bubble2_1) {
          bubble2_1 = null;
        }
        if (droid == bubble5_1) {
          bubble5_1 = null;
        }
        bubble1 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space1, screenHeight);
      }
      if ((droid == bubble2) || (droid == bubble2_2) || (droid == bubble5_2)) {
        ((QuickPlay) getContext()).bonkSound();
        loseAlife();
        if (droid == bubble2_2) {
          bubble2_2 = null;
        }
        if (droid == bubble5_2) {
          bubble5_2 = null;
        }
        bubble2 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space2, screenHeight);
      }
      if ((droid == bubble3) || (droid == bubble2_3) || (droid == bubble5_3)) {
        ((QuickPlay) getContext()).bonkSound();
        loseAlife();
        if (droid == bubble2_3) {
          bubble2_3 = null;
        }
        if (droid == bubble5_3) {
          bubble5_3 = null;
        }
        bubble3 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space3, screenHeight);
      }
      if ((droid == bubble4) || (droid == bubble2_4) || (droid == bubble5_4)) {
        ((QuickPlay) getContext()).bonkSound();
        loseAlife();
        if (droid == bubble2_4) {
          bubble2_4 = null;
        }
        if (droid == bubble5_4) {
          bubble5_4 = null;
        }
        bubble4 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space4, screenHeight);
      }
      if ((droid == bubble5) || (droid == bubble2_5) || (droid == bubble5_5)) {
        ((QuickPlay) getContext()).bonkSound();
        loseAlife();
        if (droid == bubble2_5) {
          bubble2_5 = null;
        }
        if (droid == bubble5_5) {
          bubble5_5 = null;
        }
        bubble5 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space5, screenHeight);
      }

      //Bombs
      if (droid == bomb1) {
        bomb1 = null;
        bubble1 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space1, screenHeight);
      }
      if (droid == bomb2) {
        bomb2 = null;
        bubble2 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space2, screenHeight);
      }
      if (droid == bomb3) {
        bomb3 = null;
        bubble3 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space3, screenHeight);
      }
      if (droid == bomb4) {
        bomb4 = null;
        bubble4 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space4, screenHeight);
      }
      if (droid == bomb5) {
        bomb5 = null;
        bubble5 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space5, screenHeight);
      }
      if (lives < 1) {
        Context context = getContext(); // from MySurfaceView/Activity
        Intent intent = new Intent(context, GameOver.class);
        intent.putExtra("score", Integer.toString(scr));
        context.startActivity(intent);
        if (thread.isAlive()) {
          thread.setRunning(false);
        }
        ((Activity) getContext()).finish();
      }
    }
  }

  public void checkHit1(Heart droid) {
    if (droid.getSpeed().getyDirection() == HeartSpeed.DIRECTION_DOWN
        && droid.getY() + droid.getBitmap().getHeight() / 2 >= getHeight()) {

      //Bombs
      if (droid == heart1) {
        heart1 = null;
        bubble1 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space1, screenHeight);
      }
      if (droid == heart2) {
        heart2 = null;
        bubble2 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space2, screenHeight);
      }
      if (droid == heart3) {
        heart3 = null;
        bubble3 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space3, screenHeight);
      }
      if (droid == heart4) {
        heart4 = null;
        bubble4 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space4, screenHeight);
      }
      if (droid == heart5) {
        heart5 = null;
        bubble5 = new Bub1(BitmapFactory.decodeResource(getResources(), R.drawable.bubble), speedcount, space5, screenHeight);
      }
    }
  }

  public void loseAlife() {
    lives--;
    ((QuickPlay) getContext()).setLives(lives);
  }

  public void checkSpeed() {
    if ((scr - (25 + 25 * modcounter)) >= 0) {
      modcounter++;
      speedcount++;
    }

    if ((scr - (5 + 5 * modcounter1)) >= 0) {
      modcounter1++;
      percheart++;
    }
  }
}