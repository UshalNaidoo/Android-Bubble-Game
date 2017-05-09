package com.goodguygames.bubblegame.full;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.goodguygames.bubblegame.util.DataBaseHelper;

import java.io.IOException;

public class Rate extends Activity {

  private Button Rate, No;
  private MediaPlayer mp;
  private DataBaseHelper myDbHelper;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    // setContentView(new MainGamePanel(this));
    setContentView(R.layout.rate);

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

    this.Rate = (Button) this.findViewById(R.id.button1);
    this.Rate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (view == findViewById(R.id.button1)) {
          mp = MediaPlayer.create(Rate.this, R.raw.bub_pop);
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
          myDbHelper.setIsRated("1");
          startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.goodguygames.bubblegame.full")));
          finish();
        }
      }
    });

    this.No = (Button) this.findViewById(R.id.button2);
    this.No.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (view == findViewById(R.id.button2)) {
          mp = MediaPlayer.create(Rate.this, R.raw.bub_pop);
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
  }

}
