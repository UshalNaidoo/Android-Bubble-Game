package com.goodguygames.bubblegame.full;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
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
  private TextView highscoreTxt;
  private DataBaseHelper myDbHelper;
  private MediaPlayer mp, mp1, mp2;
  private ProgressBar LivesLevel;
  private ImageView pause;
  private ImageButton mutesound;
  private boolean isMute = false;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.gamepanel);
    this.mutesound = (ImageButton) this.findViewById(R.id.imageView2);

    scoreTxt = (TextView) findViewById(R.id.score);
    highscoreTxt = (TextView) findViewById(R.id.highscore);

    myDbHelper = new DataBaseHelper(this);
    try {
      myDbHelper.createDataBase();
    } catch (IOException ioe) {
      throw new Error("Unable to create database");
    }

    try {
      myDbHelper.openDataBase();
    } catch (SQLException sqle) {
      throw sqle;
    }

    highscoreTxt.setText(myDbHelper.getHighScore());
    this.LivesLevel = (ProgressBar) this.findViewById(R.id.lifebar);
    LivesLevel.setProgressDrawable(getResources().getDrawable(R.drawable.life_bar));
    LivesLevel.setMax(5);
    LivesLevel.setProgress(3);

    if (myDbHelper.getisSound().equals("1")) {
      isMute = false;
      mutesound.setImageResource(R.drawable.button_unmute_small);
    } else {
      isMute = true;
      mutesound.setImageResource(R.drawable.button_mute_small);
    }

    this.mutesound.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (view == findViewById(R.id.imageView2)) {
          try {
            if (isMute == false) {
              isMute = true;
              myDbHelper.setisSound("0");
              mutesound.setImageResource(R.drawable.button_mute_small);
            } else if (isMute == true) {
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
              mutesound.setImageResource(R.drawable.button_unmute_small);
            }
          } catch (Exception e) {}
        }
      }
    });

    this.pause = (ImageView) this.findViewById(R.id.ImageView01);
    this.pause.setOnClickListener(new View.OnClickListener() {
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
            // TODO Auto-generated method stub
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
            // TODO Auto-generated method stub
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
        mp2 = MediaPlayer.create(QuickPlay.this, R.raw.bub_pop);
        if (myDbHelper.getisSound().equals("1")) {
          mp2.setVolume(0, 1);
        } else {
          mp2.setVolume(0, 0);
        }
        mp2.setOnCompletionListener(new OnCompletionListener() {

          @Override
          public void onCompletion(MediaPlayer mp) {
            // TODO Auto-generated method stub
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
      Intent scorepage = new Intent(QuickPlay.this, Pause.class);
      startActivityForResult(scorepage, 1);
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
