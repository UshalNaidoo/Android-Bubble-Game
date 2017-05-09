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

  private static final String TAG = "fb";
  private Button ButtonPlay;
  private MediaPlayer mp;
  private DataBaseHelper myDbHelper;
  private int played;
  private TextView highscr;
  private ImageButton mutesound;
  private boolean isMute = false;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    // setContentView(new MainGamePanel(this));
    setContentView(R.layout.main);

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

    played = Integer.parseInt(myDbHelper.getTimesPlayed());
    highscr = (TextView) findViewById(R.id.textView1);
    highscr.setText("High Score : " + myDbHelper.getHighScore());
    if ((played % 40 == 0) && (played != 0)) {
      if (isOnline()) {
        String rated = myDbHelper.getIsRated();
        if (rated.equals("0")) {
          Intent scorepage = new Intent(Main.this, Rate.class);
          startActivity(scorepage);
        }
      }
    }
    if (myDbHelper.getisSound().equals("1")) {
      isMute = false;
      mutesound.setImageResource(R.drawable.button_unmute);
    } else {
      isMute = true;
      mutesound.setImageResource(R.drawable.button_mute);
    }

    if (isOnline()) {
      String FB_ID = myDbHelper.getFBID();
      if (FB_ID.equals("0")) {
      } else {
        String FName = myDbHelper.getFname();
        String LName = myDbHelper.getL_name();
        int serverScore = Integer.parseInt(myDbHelper.getServerScore());
        int highScr = Integer.parseInt(myDbHelper.getHighScore());
        if (highScr > serverScore) {
          //ConnectToServer.uploadScore(this, FB_ID, Integer.toString(highScr), FName, LName);
          myDbHelper.setServerScore(Integer.toString(highScr));
        }
      }
    }

    this.ButtonPlay = (Button) this.findViewById(R.id.button1);
    this.ButtonPlay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (view == findViewById(R.id.button1)) {
          try {

            mp = MediaPlayer.create(Main.this, R.raw.bub_pop);
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

            Intent quickplaypage = new Intent(Main.this, QuickPlay.class);
            startActivity(quickplaypage);
            played++;
            myDbHelper.setTimesPlayed(Integer.toString(played));
          } catch (Exception e) {
          }
        }
      }
    });

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
              mp = MediaPlayer.create(Main.this, R.raw.bub_pop);
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

  }

  @Override
  protected void onDestroy() {
    //	 mp1.release();
    myDbHelper.close();
    super.onDestroy();
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
    highscr = (TextView) findViewById(R.id.textView1);
    highscr.setText("High Score : " + myDbHelper.getHighScore());

    if (myDbHelper.getisSound().equals("1")) {
      isMute = false;
      mutesound.setImageResource(R.drawable.button_unmute);
    } else {
      isMute = true;
      mutesound.setImageResource(R.drawable.button_mute);
    }
    if ((played % 40 == 0) && (played != 0)) {
      if (isOnline()) {
        String rated = myDbHelper.getIsRated();
        if (rated.equals("0")) {
          played++;
          Intent scorepage = new Intent(Main.this, Rate.class);
          startActivity(scorepage);
        }
      }
    }

    if (isOnline()) {
      String FB_ID = myDbHelper.getFBID();
      if (FB_ID.equals("0")) {
      } else {
        String FName = myDbHelper.getFname();
        String LName = myDbHelper.getL_name();
        int serverScore = Integer.parseInt(myDbHelper.getServerScore());
        int highScr = Integer.parseInt(myDbHelper.getHighScore());
        if (highScr > serverScore) {
          myDbHelper.setServerScore(Integer.toString(highScr));
        }
      }
    } else {
    }
    super.onResume();
  }

  @Override
  protected void onStop() {
    //	 mp1.release();
    //myDbHelper.close();
    super.onStop();
  }

  @Override
  protected void onPause() {
    //	 mp1.release();
    //	myDbHelper.close();
    super.onPause();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    try {
//      facebook.authorizeCallback(requestCode, resultCode, data);
    } catch (Exception e) {
      //dialog.dismiss();
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

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
      mp = MediaPlayer.create(Main.this, R.raw.bub_pop);
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
      return true;
    }

    return super.onKeyDown(keyCode, event);
  }

}