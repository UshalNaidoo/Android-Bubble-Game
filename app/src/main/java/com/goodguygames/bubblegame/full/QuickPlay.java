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

public class QuickPlay extends Activity {
  private static TextView scoreTxt;
  private static DataBaseHelper myDbHelper;
  private MediaPlayer mp;
  private static ProgressBar lifeBar;
  private static Context context;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    QuickPlay.context = this;
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
    lifeBar.setMax(MainGamePanel.maxLives);
    lifeBar.setProgress(MainGamePanel.lives);
    MainGamePanel.score = 0;

    ImageView pause = (ImageView) this.findViewById(R.id.ImageView01);
    pause.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (view == findViewById(R.id.ImageView01)) {
          mp = MediaPlayer.create(QuickPlay.this, R.raw.bub_pop);
          mp.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
              mp.release();
            }

          });
          mp.start();
          Intent scorePage = new Intent(QuickPlay.this, Pause.class);
          startActivityForResult(scorePage, 1);
        }
      }
    });
  }

  public static Context getAppContext() {
    return QuickPlay.context;
  }

  public static void bonkSound() {
    ((Activity)QuickPlay.getAppContext()).runOnUiThread(new Runnable() {
      public void run() {
        final MediaPlayer mp = MediaPlayer.create(QuickPlay.getAppContext(), R.raw.bonk);
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
    ((Activity)QuickPlay.getAppContext()).runOnUiThread(new Runnable() {
      public void run() {
        final MediaPlayer mp1 = MediaPlayer.create(QuickPlay.getAppContext(), R.raw.chime);
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
    ((Activity)QuickPlay.getAppContext()).runOnUiThread(new Runnable() {
      public void run() {
        final MediaPlayer mp2 = MediaPlayer.create(QuickPlay.getAppContext(), R.raw.bub_pop);
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
    ((Activity)QuickPlay.getAppContext()).runOnUiThread(new Runnable() {
      public void run() {
        scoreTxt.setText(newScore);
      }
    });
  }

  public static void gainALife() {
    MainGamePanel.lives = MainGamePanel.lives == MainGamePanel.maxLives ? MainGamePanel.lives : MainGamePanel.lives + 1;
    ((Activity) QuickPlay.getAppContext()).runOnUiThread(new Runnable() {
      public void run() {
        lifeBar.setProgress(MainGamePanel.lives);
      }
    });
  }

  public static void loseALife() {
    MainGamePanel.lives -= 1;
    ((Activity)QuickPlay.getAppContext()).runOnUiThread(new Runnable() {
      public void run() {
        lifeBar.setProgress(MainGamePanel.lives);
      }
    });

    if (MainGamePanel.lives == 0) {
      Intent intent = new Intent(context, GameOver.class);
      intent.putExtra("score", Integer.toString(MainGamePanel.score));
      context.startActivity(intent);
      if (MainGamePanel.thread.isAlive()) {
        MainGamePanel.thread.setRunning(false);
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
      mp = MediaPlayer.create(QuickPlay.this, R.raw.bub_pop);
      mp.setOnCompletionListener(new OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mp) {
          mp.release();
        }

      });
      mp.start();
      Intent scorePage = new Intent(QuickPlay.this, Pause.class);
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
