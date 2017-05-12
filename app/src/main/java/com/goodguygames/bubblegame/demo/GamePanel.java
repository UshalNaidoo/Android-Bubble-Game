package com.goodguygames.bubblegame.demo;

import android.app.Activity;
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
  private static ProgressBar lifeBar;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
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
          Intent scorePage = new Intent(GamePanel.this, Pause.class);
          startActivityForResult(scorePage, 1);
        }
      }
    });
  }

  public static void bonkSound() {
    ((Activity) MainMenu.getAppContext()).runOnUiThread(new Runnable() {
      public void run() {
        final MediaPlayer mp = MediaPlayer.create(MainMenu.getAppContext(), R.raw.bonk);
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
    ((Activity) MainMenu.getAppContext()).runOnUiThread(new Runnable() {
      public void run() {
        final MediaPlayer mp1 = MediaPlayer.create(MainMenu.getAppContext(), R.raw.chime);
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
    ((Activity) MainMenu.getAppContext()).runOnUiThread(new Runnable() {
      public void run() {
        final MediaPlayer mp2 = MediaPlayer.create(MainMenu.getAppContext(), R.raw.bub_pop);
        mp2.setVolume(0,1);
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
    ((Activity) MainMenu.getAppContext()).runOnUiThread(new Runnable() {
      public void run() {
        scoreTxt.setText(newScore);
      }
    });
  }

  public static void gainALife() {
    GameScene.lives = GameScene.lives == GameScene.maxLives ? GameScene.lives : GameScene.lives + 1;
    ((Activity) MainMenu.getAppContext()).runOnUiThread(new Runnable() {
      public void run() {
        lifeBar.setProgress(GameScene.lives);
      }
    });
  }

  public static void loseALife() {
    GameScene.lives -= 1;
    ((Activity) MainMenu.getAppContext()).runOnUiThread(new Runnable() {
      public void run() {
        lifeBar.setProgress(GameScene.lives);
      }
    });

    if (GameScene.lives == 0) {
      Intent intent = new Intent(MainMenu.getAppContext(), GameOver.class);
      intent.putExtra("score", Integer.toString(GameScene.score));
      MainMenu.getAppContext().startActivity(intent);
      if (GameScene.thread.isAlive()) {
        GameScene.thread.setRunning(false);
      }
      ((Activity)MainMenu.getAppContext()).finish();
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
      startActivityForResult(new Intent(GamePanel.this, Pause.class), 1);
      return true;
    }

    return super.onKeyDown(keyCode, event);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == 1 && resultCode == RESULT_OK) {
      this.finish();
    }
  }

}
