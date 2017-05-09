package com.goodguygames.bubblegame.full;


import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.goodguygames.bubblegame.util.DataBaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ScoreBoardScreen extends Activity implements OnItemClickListener {

  private ListView listView;
  private String friendsresponse;
  private Button ButtonPlay;
  private DataBaseHelper myDbHelper;
  private MediaPlayer mp;
  private int played;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.scoreboard);

    friendsresponse = getIntent().getStringExtra("friendsresponse");
    showdialog();

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

    this.ButtonPlay = (Button) this.findViewById(R.id.button1);
    this.ButtonPlay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (view == findViewById(R.id.button1)) {
          try {

            mp = MediaPlayer.create(ScoreBoardScreen.this, R.raw.bub_pop);
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

            Intent quickplaypage = new Intent(ScoreBoardScreen.this, QuickPlay.class);
            startActivity(quickplaypage);
            setResult(RESULT_OK, null);
            finish();
            played++;
            myDbHelper.setTimesPlayed(Integer.toString(played));
          } catch (Exception e) {
          }
        }
      }
    });
  }

  private void showdialog() {
    try {

      String similar = getIntent().getStringExtra("Similar");
      similar = similar.replace("[", "");
      similar = similar.replace(" ", "");
      similar = similar.replace("]", "");
      String[] strArray = similar.split(",");

      ScoreBoard ScoreBoard_data[] = new ScoreBoard[strArray.length + 1];

      for (int i = 0; i < strArray.length + 1; i++) {
        String Name = null;

        int Score;
        String ID = null;
        String isMe = null;
        if (i == strArray.length) {
          Name = getIntent().getStringExtra("name");
          Score = Integer.parseInt(getIntent().getStringExtra("score"));
          ID = getIntent().getStringExtra("id");
          isMe = "1";

        } else {
          Name = getNames(strArray[i]);
          Score = 0;
          //Score = Integer.parseInt(ConnectToServer.getScore(this,strArray[i]));
          ID = strArray[i];
          isMe = "0";
        }
        ScoreBoard_data[i] = new ScoreBoard(Name, Score, ID, isMe);
      }
      ScoreBoardAdapter adapter;
      adapter = new ScoreBoardAdapter(this, R.layout.scoreboard_row_item, ScoreBoard_data);

      adapter.sort(ScoreBoard.ScoreComparator);
      listView = (ListView) findViewById(R.id.listfacebook);
      listView.setOnItemClickListener(this);
      listView.setAdapter(adapter);
    } catch (Exception e) {
    }
  }


  @Override
  public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                          long arg3) {
    // TODO Auto-generated method stub

  }


  public void onDestroy() {
    super.onDestroy();

  }

  public String getNames(String id) {
    String name = null;
    try {
      id = id.replace("[", "");
      id = id.replace(" ", "");
      id = id.replace("]", "");

      JSONObject json;

      json = new JSONObject(friendsresponse);

      JSONArray jArray = json.getJSONArray("data");
      JSONObject json_data = null;
      int x = 0;

      while (x < jArray.length()) {

        json_data = jArray.getJSONObject(x);
        String FriendID = json_data.getString("id");
        if (FriendID.equals(id)) {
          name = json_data.getString("name");
        }
        x++;

      }
    } catch (JSONException e) {
    } catch (Exception e) {
    }
    return name;

  }
}
