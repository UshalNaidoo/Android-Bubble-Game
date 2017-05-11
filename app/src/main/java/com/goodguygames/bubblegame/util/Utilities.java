package com.goodguygames.bubblegame.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.goodguygames.bubblegame.full.QuickPlay;

public class Utilities {

  public boolean isOnline() {
    ConnectivityManager cm = (ConnectivityManager) QuickPlay.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo netInfo = cm.getActiveNetworkInfo();
    return netInfo != null && netInfo.isConnected();
  }

}
