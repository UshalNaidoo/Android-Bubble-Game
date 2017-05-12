package com.goodguygames.bubblegame.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.goodguygames.bubblegame.util.DataBaseHelper;

import java.io.IOException;

public class GameOver extends Activity {

  private DataBaseHelper myDbHelper;
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
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
      Intent quickPlayPage = new Intent(GameOver.this, GamePanel.class);
      startActivity(quickPlayPage);
      finish();
      played++;
      myDbHelper.setTimesPlayed(Integer.toString(played));
      return true;
    }

    return super.onKeyDown(keyCode, event);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1 && resultCode == RESULT_OK) {
      finish();
    }
  }

}
