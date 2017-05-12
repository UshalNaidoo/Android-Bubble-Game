package com.goodguygames.bubblegame.full;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.goodguygames.bubblegame.util.DataBaseHelper;

public class GameOver extends Activity {

  private DataBaseHelper myDbHelper;
  private MediaPlayer mp;
  private int played;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.gameover);
    TextView yourScore = (TextView) findViewById(R.id.yourscore);
    String score = getIntent().getStringExtra("score");
    yourScore.setText(getIntent().getStringExtra("score"));
    TextView highScoreBanner = (TextView) findViewById(R.id.HighScoreTV);
    highScoreBanner.setVisibility(View.GONE);
    TextView highScore = (TextView) findViewById(R.id.highscore);

    myDbHelper = new DataBaseHelper(this);

    try {
      myDbHelper.createDataBase();
    } catch (IOException ioe) {
      throw new Error("Unable to create database");
    }

    myDbHelper.openDataBase();
    played = Integer.parseInt(myDbHelper.getTimesPlayed());

    if (Integer.parseInt(score) > Integer.parseInt(myDbHelper.getHighScore())) {
      highScoreBanner.setVisibility(View.VISIBLE);
      myDbHelper.setHighScore(score);
    }
    highScore.setText(myDbHelper.getHighScore());

    Button buttonPlay = (Button) this.findViewById(R.id.button1);
    buttonPlay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (view == findViewById(R.id.button1)) {
          mp = MediaPlayer.create(GameOver.this, R.raw.bub_pop);
          mp.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
              mp.release();
            }
          });
          mp.start();
          Intent quickPlayPage = new Intent(GameOver.this, GamePanel.class);
          startActivity(quickPlayPage);
          finish();
          played++;
          myDbHelper.setTimesPlayed(Integer.toString(played));
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
    myDbHelper.openDataBase();
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
    if (requestCode == 1 && resultCode == RESULT_OK) {
      finish();
    }
  }

}
