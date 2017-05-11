package com.goodguygames.bubblegame.full;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.goodguygames.bubblegame.util.DataBaseHelper;

import java.io.IOException;

public class Productions extends Activity {

  protected boolean _active = true;
  protected int _splashTime = 4000;

  private MediaPlayer mp1;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.productions);

    DataBaseHelper myDbHelper = new DataBaseHelper(this);
    try {
      myDbHelper.createDataBase();
    } catch (IOException ioe) {
      throw new Error("Unable to create database");
    }

    myDbHelper.openDataBase();

    mp1 = MediaPlayer.create(this, R.raw.title);
    mp1.setLooping(true);
    mp1.start();

    final ImageView splashImageView = (ImageView) findViewById(R.id.SplashImageView);
    splashImageView.setBackgroundResource(R.drawable.productiongif);
    final AnimationDrawable frameAnimation = (AnimationDrawable) splashImageView.getBackground();

    splashImageView.post(new Runnable() {
      @Override
      public void run() {
        frameAnimation.start();
      }
    });
    Thread splashTread = new Thread() {
      @Override
      public void run() {
        try {
          int waited = 0;
          while (_active && (waited < _splashTime)) {
            sleep(100);
            if (_active) {
              waited += 100;
            }
          }
        } catch (InterruptedException e) {
          // do nothing
        } finally {
          finish();
          mp1.release();
          startActivity(new Intent("com.goodguygames.bubblegame.full.Main"));
        }
      }
    };
    splashTread.start();
  }
}