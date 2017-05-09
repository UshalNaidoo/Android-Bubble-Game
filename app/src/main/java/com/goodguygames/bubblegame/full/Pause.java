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
  private ImageButton mutesound;
  private DataBaseHelper myDbHelper;
  private boolean isMute = false;
  private MediaPlayer mp;
  private Button exit;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    // setContentView(new MainGamePanel(this));
    setContentView(R.layout.pausescreen);
    this.mutesound = (ImageButton) this.findViewById(R.id.muteUnmute);
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
    myDbHelper.Exists();

    if (myDbHelper.getisSound().equals("1")) {
      isMute = false;
      mutesound.setImageResource(R.drawable.button_unmute);
    } else {
      isMute = true;
      mutesound.setImageResource(R.drawable.button_mute);
    }

    this.mutesound.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (view == findViewById(R.id.muteUnmute)) {
          try {
            if (isMute == false) {
              isMute = true;
              myDbHelper.setisSound("0");
              mutesound.setImageResource(R.drawable.button_mute);
            } else if (isMute == true) {
              isMute = false;
              mp = MediaPlayer.create(Pause.this, R.raw.bub_pop);
              mp.setVolume(0, 1);
              mp.setOnCompletionListener(new OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                  // TODO Auto-generated method stub
                  mp.release();
                }

              });
              mp.start();
              myDbHelper.setisSound("1");
              mutesound.setImageResource(R.drawable.button_unmute);
            }
          } catch (Exception e) {}
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
              // TODO Auto-generated method stub
              mp.release();
            }

          });
          mp.start();

          finish();
        }
      }
    });

    this.exit = (Button) this.findViewById(R.id.exitButton);
    this.exit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (view == findViewById(R.id.exitButton)) {
          try {
            setResult(RESULT_OK, null);
            finish();
          } catch (Exception e) {
          }
        }
      }
    });
  }

}