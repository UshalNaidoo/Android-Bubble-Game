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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.goodguygames.bubblegame.util.DataBaseHelper;

import java.io.IOException;

public class QuickPlay extends Activity {
  private TextView scoreTxt;
  private DataBaseHelper myDbHelper;
  private MediaPlayer mp;

  private MediaPlayer mp1;

  private ProgressBar LivesLevel;
  private ImageButton muteSound;
  private boolean isMute = false;
  private static Context context;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    QuickPlay.context = this;
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.gamepanel);
    this.muteSound = (ImageButton) this.findViewById(R.id.imageView2);

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
    this.LivesLevel = (ProgressBar) this.findViewById(R.id.lifebar);
    LivesLevel.setProgressDrawable(getResources().getDrawable(R.drawable.life_bar));
    LivesLevel.setMax(5);
    LivesLevel.setProgress(3);

    if (myDbHelper.getisSound().equals("1")) {
      isMute = false;
      muteSound.setImageResource(R.drawable.button_unmute_small);
    } else {
      isMute = true;
      muteSound.setImageResource(R.drawable.button_mute_small);
    }

    this.muteSound.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (view == findViewById(R.id.imageView2)) {
          if (!isMute) {
            isMute = true;
            myDbHelper.setisSound("0");
            muteSound.setImageResource(R.drawable.button_mute_small);
          } else if (isMute) {
            isMute = false;
            mp = MediaPlayer.create(QuickPlay.this, R.raw.bub_pop);
            mp.setVolume(0, 1);
            mp.setOnCompletionListener(new OnCompletionListener() {
              @Override
              public void onCompletion(MediaPlayer mp) {
                mp.release();
              }

            });
            mp.start();
            myDbHelper.setisSound("1");
            muteSound.setImageResource(R.drawable.button_unmute_small);
          }
        }
      }
    });

    ImageView pause = (ImageView) this.findViewById(R.id.ImageView01);
    pause.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (view == findViewById(R.id.ImageView01)) {
          mp = MediaPlayer.create(QuickPlay.this, R.raw.bub_pop);
          if (myDbHelper.getisSound().equals("1")) {
            mp.setVolume(0, 1);
          } else {
            mp.setVolume(0, 0);
          }
          mp.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
              mp.release();
            }

          });
          mp.start();
          Intent scorepage = new Intent(QuickPlay.this, Pause.class);
          startActivityForResult(scorepage, 1);
        }
      }
    });
  }

  public static Context getAppContext() {
    return QuickPlay.context;
  }

  public void bonkSound() {
    QuickPlay.this.runOnUiThread(new Runnable() {
      public void run() {
        mp = MediaPlayer.create(QuickPlay.this, R.raw.bonk);

        if (myDbHelper.getisSound().equals("1")) {
          mp.setVolume(0, 1);
        } else {
          mp.setVolume(0, 0);
        }
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

  public void chimeSound() {
    QuickPlay.this.runOnUiThread(new Runnable() {
      public void run() {
        mp1 = MediaPlayer.create(QuickPlay.this, R.raw.chime);
        if (myDbHelper.getisSound().equals("1")) {
          mp1.setVolume(0, 1);
        } else {
          mp1.setVolume(0, 0);
        }
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

  public void popSound() {
    QuickPlay.this.runOnUiThread(new Runnable() {
      public void run() {
        final MediaPlayer mp2 = MediaPlayer.create(QuickPlay.this, R.raw.bub_pop);
        if (myDbHelper.getisSound().equals("1")) {
          mp2.setVolume(0, 1);
        } else {
          mp2.setVolume(0, 0);
        }
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

  public void setScore(final String txt) {
    QuickPlay.this.runOnUiThread(new Runnable() {
      public void run() {
        scoreTxt.setText(txt);
      }
    });
  }

  public void setLives(final int lives) {
    QuickPlay.this.runOnUiThread(new Runnable() {
      public void run() {
        LivesLevel.setProgress(lives);
      }
    });
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
      if (myDbHelper.getisSound().equals("1")) {
        mp.setVolume(0, 1);
      } else {
        mp.setVolume(0, 0);
      }
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
