package com.goodguygames.bubblegame.full;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.goodguygames.bubblegame.util.DataBaseHelper;

import java.io.IOException;

public class Main extends Activity {

  private MediaPlayer mediaPlayer;
  private DataBaseHelper dataBaseHelper;
  private int played;
  private TextView highScoreTextView;
  private ImageButton muteSound;
  private boolean isMute = false;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.main);
    this.muteSound = (ImageButton) this.findViewById(R.id.muteUnmute);
    dataBaseHelper = new DataBaseHelper(this);
    try {
      dataBaseHelper.createDataBase();
    } catch (IOException ioe) {
      throw new Error("Unable to create database");
    }

    dataBaseHelper.openDataBase();
    dataBaseHelper.Exists();

    played = Integer.parseInt(dataBaseHelper.getTimesPlayed());
    highScoreTextView = (TextView) findViewById(R.id.textView1);
    highScoreTextView.setText(getResources().getString(R.string.high_score) + dataBaseHelper.getHighScore());

    if (dataBaseHelper.getisSound().equals("1")) {
      isMute = false;
      muteSound.setImageResource(R.drawable.button_unmute);
    } else {
      isMute = true;
      muteSound.setImageResource(R.drawable.button_mute);
    }

    Button buttonPlay = (Button) this.findViewById(R.id.button1);
    buttonPlay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (view == findViewById(R.id.button1)) {
          mediaPlayer = MediaPlayer.create(Main.this, R.raw.bub_pop);
          if (dataBaseHelper.getisSound().equals("1")) {
            mediaPlayer.setVolume(0, 1);
          } else {
            mediaPlayer.setVolume(0, 0);
          }
          mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
              mp.release();
            }

          });
          mediaPlayer.start();

          startActivity(new Intent(Main.this, QuickPlay.class));
          played++;
          dataBaseHelper.setTimesPlayed(Integer.toString(played));
        }
      }
    });

    this.muteSound.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (view == findViewById(R.id.muteUnmute)) {
          if (!isMute) {
            isMute = true;
            dataBaseHelper.setisSound("0");
            muteSound.setImageResource(R.drawable.button_mute);
          } else if (isMute) {
            isMute = false;
            mediaPlayer = MediaPlayer.create(Main.this, R.raw.bub_pop);
            mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

              @Override
              public void onCompletion(MediaPlayer mp) {
                mp.release();
              }
            });
            mediaPlayer.start();
            dataBaseHelper.setisSound("1");
            muteSound.setImageResource(R.drawable.button_unmute);
          }
        }
      }
    });

  }

  @Override
  protected void onDestroy() {
    dataBaseHelper.close();
    super.onDestroy();
  }

  @Override
  protected void onResume() {
    dataBaseHelper = new DataBaseHelper(this);
    try {
      dataBaseHelper.createDataBase();
    } catch (IOException ioe) {
      throw new Error("Unable to create database");
    }

    dataBaseHelper.openDataBase();

    highScoreTextView = (TextView) findViewById(R.id.textView1);
    highScoreTextView.setText(getResources().getString(R.string.high_score) + dataBaseHelper.getHighScore());

    if (dataBaseHelper.getisSound().equals("1")) {
      isMute = false;
      muteSound.setImageResource(R.drawable.button_unmute);
    } else {
      isMute = true;
      muteSound.setImageResource(R.drawable.button_mute);
    }
    super.onResume();
  }

  @Override
  protected void onStop() {
    super.onStop();
  }

  @Override
  protected void onPause() {
    super.onPause();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
  }


  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
      mediaPlayer = MediaPlayer.create(Main.this, R.raw.bub_pop);
      if (dataBaseHelper.getisSound().equals("1")) {
        mediaPlayer.setVolume(0, 1);
      } else {
        mediaPlayer.setVolume(0, 0);
      }
      mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mp) {
          mp.release();
        }

      });
      mediaPlayer.start();
      finish();
      return true;
    }

    return super.onKeyDown(keyCode, event);
  }

}