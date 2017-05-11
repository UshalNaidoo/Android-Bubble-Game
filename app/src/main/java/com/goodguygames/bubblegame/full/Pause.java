package com.goodguygames.bubblegame.full;


import android.app.Activity;
import android.database.SQLException;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.goodguygames.bubblegame.util.DataBaseHelper;

import java.io.IOException;

public class Pause extends Activity {

  private ImageView play;
  private ImageButton muteSound;
  private DataBaseHelper myDbHelper;
  private boolean isMute = false;
  private MediaPlayer mp;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.pausescreen);
    this.muteSound = (ImageButton) this.findViewById(R.id.muteUnmute);
    myDbHelper = new DataBaseHelper(this);
    try {
      myDbHelper.createDataBase();
    } catch (IOException ioe) {
      throw new Error("Unable to create database");
    }

    myDbHelper.openDataBase();
    myDbHelper.Exists();

    if (myDbHelper.getisSound().equals("1")) {
      isMute = false;
      muteSound.setImageResource(R.drawable.button_unmute);
    } else {
      isMute = true;
      muteSound.setImageResource(R.drawable.button_mute);
    }

    this.muteSound.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (view == findViewById(R.id.muteUnmute)) {
          if (!isMute) {
            isMute = true;
            myDbHelper.setisSound("0");
            muteSound.setImageResource(R.drawable.button_mute);
          } else if (isMute) {
            isMute = false;
            mp = MediaPlayer.create(Pause.this, R.raw.bub_pop);
            mp.setVolume(0, 1);
            mp.setOnCompletionListener(new OnCompletionListener() {

              @Override
              public void onCompletion(MediaPlayer mp) {
                mp.release();
              }

            });
            mp.start();
            myDbHelper.setisSound("1");
            muteSound.setImageResource(R.drawable.button_unmute);
          }
        }
      }
    });

    this.play = (ImageView) this.findViewById(R.id.imageView1);
    this.play.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (view == findViewById(R.id.imageView1)) {
          play.setImageResource(R.drawable.playpressed);

          mp = MediaPlayer.create(Pause.this, R.raw.bub_pop);
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

          finish();
        }
      }
    });

    Button exit = (Button) this.findViewById(R.id.exitButton);
    exit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (view == findViewById(R.id.exitButton)) {
          setResult(RESULT_OK, null);
          finish();
        }
      }
    });
  }

}