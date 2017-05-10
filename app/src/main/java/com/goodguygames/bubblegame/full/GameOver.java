package com.goodguygames.bubblegame.full;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.goodguygames.bubblegame.util.DataBaseHelper;

import java.io.IOException;

public class GameOver extends Activity {

  private TextView yourScore, highScore;
  private DataBaseHelper myDbHelper;
  private TextView highscrBanner;
  private Button ButtonPlay;
  private MediaPlayer mp;
  private int played;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    // setContentView(new MainGamePanel(this));
    setContentView(R.layout.gameover);
    yourScore = (TextView) findViewById(R.id.yourscore);
    String score = getIntent().getStringExtra("score");
    yourScore.setText(score);
    highscrBanner = (TextView) findViewById(R.id.HighScoreTV);
    highscrBanner.setVisibility(View.GONE);
    highScore = (TextView) findViewById(R.id.highscore);

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

    played = Integer.parseInt(myDbHelper.getTimesPlayed());

    if (Integer.parseInt(score) > Integer.parseInt(myDbHelper.getHighScore())) {
      highscrBanner.setVisibility(View.VISIBLE);
      myDbHelper.setHighScore(score);
    }
    String highscore = myDbHelper.getHighScore();
    highScore.setText(highscore);

    this.ButtonPlay = (Button) this.findViewById(R.id.button1);
    this.ButtonPlay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (view == findViewById(R.id.button1)) {
          try {

            mp = MediaPlayer.create(GameOver.this, R.raw.bub_pop);
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

            Intent quickplaypage = new Intent(GameOver.this, QuickPlay.class);
            startActivity(quickplaypage);
            finish();
            played++;
            myDbHelper.setTimesPlayed(Integer.toString(played));
          } catch (Exception e) {
          }
        }
      }
    });

  }


  @Override
  protected void onResume() {
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
    super.onResume();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    myDbHelper.close();
    finish();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1) {
      if (resultCode == RESULT_OK) {
        this.finish();

      }
    }
  }

  public boolean isOnline() {
    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo netInfo = cm.getActiveNetworkInfo();
    if (netInfo != null && netInfo.isConnected()) {
      return true;
    }
    return false;
  }
}
