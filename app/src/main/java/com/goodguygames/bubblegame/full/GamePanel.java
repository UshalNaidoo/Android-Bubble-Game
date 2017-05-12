package com.goodguygames.bubblegame.full;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.goodguygames.bubblegame.util.DataBaseHelper;

import java.io.IOException;

public class GamePanel extends Activity {
  private static TextView scoreTxt;
  private static DataBaseHelper myDbHelper;
  private MediaPlayer mp;
  private static ProgressBar lifeBar;
  private static Context context;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GamePanel.context = this;
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.gamepanel);

    scoreTxt = (TextView) findViewById(R.id.score);
    TextView highScoreText = (TextView) findViewById(R.id.highscore);

    myDbHelper = new DataBaseHelper(this);
    try {
      myDbHelper.createDataBase();
    } catch (IOException ioe) {
      throw new Error("Unable to create database");
    }

    myDbHelper.openDataBase();

    highScoreText.setText(myDbHelper.getHighScore());
    this.lifeBar = (ProgressBar) this.findViewById(R.id.lifebar);
    lifeBar.setProgressDrawable(getResources().getDrawable(R.drawable.life_bar));
    lifeBar.setMax(GameScene.maxLives);
    lifeBar.setProgress(GameScene.lives);
    GameScene.score = 0;

    ImageView pause = (ImageView) this.findViewById(R.id.ImageView01);
    pause.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (view == findViewById(R.id.ImageView01)) {
          mp = MediaPlayer.create(GamePanel.this, R.raw.bub_pop);
          mp.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
              mp.release();
            }

          });
          mp.start();
          Intent scorePage = new Intent(GamePanel.this, Pause.class);
          startActivityForResult(scorePage, 1);
        }
      }
    });
  }

  public static Context getAppContext() {
    return GamePanel.context;
  }

  public static void bonkSound() {
    ((Activity) GamePanel.getAppContext()).runOnUiThread(new Runnable() {
      public void run() {
        final MediaPlayer mp = MediaPlayer.create(GamePanel.getAppContext(), R.raw.bonk);
        mp.setOnCompletionListener(new OnCompletionListener() {

          @Override
          public void onCompletion(MediaPlayer mp) {
            mp.release();
          }

        });
        mp.start();
      }
    });
  }

  public static void chimeSound() {
    ((Activity) GamePanel.getAppContext()).runOnUiThread(new Runnable() {
      public void run() {
        final MediaPlayer mp1 = MediaPlayer.create(GamePanel.getAppContext(), R.raw.chime);
        mp1.setOnCompletionListener(new OnCompletionListener() {

          @Override
          public void onCompletion(MediaPlayer mp) {
            mp1.release();
          }

        });
        mp1.start();
      }
    });
  }

  public static void popSound() {
    ((Activity) GamePanel.getAppContext()).runOnUiThread(new Runnable() {
      public void run() {
        final MediaPlayer mp2 = MediaPlayer.create(GamePanel.getAppContext(), R.raw.bub_pop);
        mp2.setOnCompletionListener(new OnCompletionListener() {
          @Override
          public void onCompletion(MediaPlayer mp) {
            mp2.release();
          }
        });
        mp2.start();

      }
    });
  }

  public static void setScore(final String newScore) {
    ((Activity) GamePanel.getAppContext()).runOnUiThread(new Runnable() {
      public void run() {
        scoreTxt.setText(newScore);
      }
    });
  }

  public static void gainALife() {
    GameScene.lives = GameScene.lives == GameScene.maxLives ? GameScene.lives : GameScene.lives + 1;
    ((Activity) GamePanel.getAppContext()).runOnUiThread(new Runnable() {
      public void run() {
        lifeBar.setProgress(GameScene.lives);
      }
    });
  }

  public static void loseALife() {
    GameScene.lives -= 1;
    ((Activity) GamePanel.getAppContext()).runOnUiThread(new Runnable() {
      public void run() {
        lifeBar.setProgress(GameScene.lives);
      }
    });

    if (GameScene.lives == 0) {
      Intent intent = new Intent(context, GameOver.class);
      intent.putExtra("score", Integer.toString(GameScene.score));
      context.startActivity(intent);
      if (GameScene.thread.isAlive()) {
        GameScene.thread.setRunning(false);
      }
      ((Activity)getAppContext()).finish();
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    myDbHelper.close();
    finish();
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
      mp = MediaPlayer.create(GamePanel.this, R.raw.bub_pop);
      mp.setOnCompletionListener(new OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mp) {
          mp.release();
        }

      });
      mp.start();
      Intent scorePage = new Intent(GamePanel.this, Pause.class);
      startActivityForResult(scorePage, 1);
      return true;
    }

    return super.onKeyDown(keyCode, event);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == 1) {
      if (resultCode == RESULT_OK) {
        this.finish();
      }
    }
  }

}
