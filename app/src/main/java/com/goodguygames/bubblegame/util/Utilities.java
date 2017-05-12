package com.goodguygames.bubblegame.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.goodguygames.bubblegame.full.GamePanel;

public class Utilities {

  public boolean isOnline() {
    ConnectivityManager cm = (ConnectivityManager) GamePanel.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo netInfo = cm.getActiveNetworkInfo();
    return netInfo != null && netInfo.isConnected();
  }

}
