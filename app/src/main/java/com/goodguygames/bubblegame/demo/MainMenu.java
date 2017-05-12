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

public class MainMenu extends Activity {

  private DataBaseHelper dataBaseHelper;
  private int played;
  private TextView highScoreTextView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.main);
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

    Button buttonPlay = (Button) this.findViewById(R.id.button1);
    buttonPlay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (view == findViewById(R.id.button1)) {
          startActivity(new Intent(MainMenu.this, GamePanel.class));
          played++;
          dataBaseHelper.setTimesPlayed(Integer.toString(played));
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
      finish();
      return true;
    }

    return super.onKeyDown(keyCode, event);
  }

}