package com.goodguygames.bubblegame.util;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {

  private static String DB_PATH = "/data/data/com.goodguygames.bubblegame.full/databases/";
  private static String DB_NAME = "bubbleDB.sqlite";
  private static String user_Table = "user_Table";
  private SQLiteDatabase myDataBase;
  private final Context myContext;

  /**
   * Constructor Takes and keeps a reference of the passed context in order to access to the
   * application assets and resources.
   */

  public DataBaseHelper(Context context) {
    super(context, DB_NAME, null, 1);
    this.myContext = context;
  }

  /**
   * Creates a empty database on the system and rewrites it with your own database.
   */
  public void createDataBase() throws IOException {
    boolean dbExist = checkDataBase();
    if (dbExist) {
      // do nothing - database already exist
    } else {
      // By calling this method and empty database will be created into
      // the default system path
      // of your application so we are gonna be able to overwrite that
      // database with our database.
      this.getReadableDatabase();
      try {
        copyDataBase();
      } catch (IOException e) {
        throw new Error("Error copying database");
      }
    }
  }

  /**
   * Check if the database already exist to avoid re-copying the file each time you open the
   * application.
   *
   * @return true if it exists, false if it doesn't
   */
  private boolean checkDataBase() {
    SQLiteDatabase checkDB = null;
    try {
      String myPath = DB_PATH + DB_NAME;
      checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    } catch (SQLiteException e) {
      // database doesn't exist yet.
    }
    if (checkDB != null) {
      checkDB.close();
    }
    return checkDB != null ? true : false;
  }

  /**
   * Copies your database from your local assets-folder to the just created empty database in the
   * system folder, from where it can be accessed and handled. This is done by transfering
   * bytestream.
   */
  private void copyDataBase() throws IOException {
    // Open your local db as the input stream
    InputStream myInput = myContext.getAssets().open(DB_NAME);

    // Path to the just created empty db
    String outFileName = DB_PATH + DB_NAME;

    // Open the empty db as the output stream
    OutputStream myOutput = new FileOutputStream(outFileName);

    // transfer bytes from the inputfile to the outputfile
    byte[] buffer = new byte[1024];
    int length;
    while ((length = myInput.read(buffer)) > 0) {
      myOutput.write(buffer, 0, length);
    }
    // Close the streams
    myOutput.flush();
    myOutput.close();
    myInput.close();
  }

  public void openDataBase() throws SQLException {
    // Open the database
    String myPath = DB_PATH + DB_NAME;
    myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
  }

  @Override
  public synchronized void close() {
    if (myDataBase != null) {
      myDataBase.close();
    }
    super.close();
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  }

  public void Exists() {
    Cursor cursor = this.myDataBase.rawQuery("SELECT * from user_Table", null);
    if (cursor.moveToFirst()) {
      // record exists
    } else {
      // record not found
      String sql = "INSERT INTO user_Table (highscore) VALUES('0')";
      this.myDataBase.execSQL(sql);
      String sql1 = "UPDATE user_Table SET times_played='0'";
      this.myDataBase.execSQL(sql1);
      String sql2 = "UPDATE user_Table SET fb_num='0'";
      this.myDataBase.execSQL(sql2);
      String sql3 = "UPDATE user_Table SET serverscore='0'";
      this.myDataBase.execSQL(sql3);
      String sql4 = "UPDATE user_Table SET issound='1'";
      this.myDataBase.execSQL(sql4);
      String sql5 = "UPDATE user_Table SET israted='0'";
      this.myDataBase.execSQL(sql5);
    }
    cursor.close();

  }

  public String getTimesPlayed() {
    Cursor cursor = this.myDataBase.rawQuery("SELECT times_played FROM user_Table", null);
    if (cursor.moveToFirst()) {
      do {
        return cursor.getString(0);
      } while (cursor.moveToNext());
    }
    if (cursor != null && !cursor.isClosed()) {
      cursor.close();
    }
    return "0";
  }

  public void setTimesPlayed(String times_played) {

    String sql = "UPDATE user_Table SET times_played='" + times_played + "'";
    this.myDataBase.execSQL(sql);
  }

  public String getIsRated() {
    Cursor cursor = this.myDataBase.rawQuery("SELECT israted FROM user_Table", null);
    if (cursor.moveToFirst()) {
      do {
        return cursor.getString(0);
      } while (cursor.moveToNext());
    }
    if (cursor != null && !cursor.isClosed()) {
      cursor.close();
    }
    return "0";
  }

  public void setIsRated(String israted) {

    String sql = "UPDATE user_Table SET israted='" + israted + "'";
    this.myDataBase.execSQL(sql);
  }

  public String getFname() {
    Cursor cursor = this.myDataBase.rawQuery("SELECT f_name FROM user_Table", null);
    if (cursor.moveToFirst()) {
      do {
        return cursor.getString(0);
      } while (cursor.moveToNext());
    }
    if (cursor != null && !cursor.isClosed()) {
      cursor.close();
    }
    return "0";
  }

  public void setFname(String times_played) {

    String sql = "UPDATE user_Table SET f_name='" + times_played + "'";
    this.myDataBase.execSQL(sql);
  }

  public String getL_name() {
    Cursor cursor = this.myDataBase.rawQuery("SELECT l_name FROM user_Table", null);
    if (cursor.moveToFirst()) {
      do {
        return cursor.getString(0);
      } while (cursor.moveToNext());
    }
    if (cursor != null && !cursor.isClosed()) {
      cursor.close();
    }
    return "0";
  }

  public void setL_name(String times_played) {

    String sql = "UPDATE user_Table SET l_name='" + times_played + "'";
    this.myDataBase.execSQL(sql);
  }

  public String getisSound() {
    Cursor cursor = this.myDataBase.rawQuery("SELECT issound FROM user_Table", null);
    if (cursor.moveToFirst()) {
      do {
        return cursor.getString(0);
      } while (cursor.moveToNext());
    }
    if (cursor != null && !cursor.isClosed()) {
      cursor.close();
    }
    return "0";
  }

  public void setisSound(String sound) {
    String sql = "UPDATE user_Table SET issound='" + sound + "'";
    this.myDataBase.execSQL(sql);
  }

  public String getHighScore() {
    Cursor cursor = this.myDataBase.rawQuery("SELECT highscore FROM user_Table", null);
    if (cursor.moveToFirst()) {
      do {
        return cursor.getString(0);
      } while (cursor.moveToNext());
    }
    if (cursor != null && !cursor.isClosed()) {
      cursor.close();
    }
    return "0";
  }

  public void setFBID(String id) {
    String sql = "UPDATE user_Table SET fb_num='" + id + "'";
    this.myDataBase.execSQL(sql);
  }

  public String getFBID() {
    Cursor cursor = this.myDataBase.rawQuery("SELECT fb_num FROM user_Table", null);
    if (cursor.moveToFirst()) {
      do {
        return cursor.getString(0);
      } while (cursor.moveToNext());
    }
    if (cursor != null && !cursor.isClosed()) {
      cursor.close();
    }
    return "0";
  }

  public void setHighScore(String highscore) {

    String sql = "UPDATE user_Table SET highscore='" + highscore + "'";
    this.myDataBase.execSQL(sql);
  }

  public String getTotalPoints() {
    Cursor cursor = this.myDataBase.rawQuery("SELECT total_points FROM user_Table", null);
    if (cursor.moveToFirst()) {
      do {
        return cursor.getString(0);
      } while (cursor.moveToNext());
    }
    if (cursor != null && !cursor.isClosed()) {
      cursor.close();
    }
    return "0";
  }

  public void setTotalPoints(String total_points) {

    String sql = "UPDATE user_Table SET total_points='" + total_points + "'";
    this.myDataBase.execSQL(sql);
  }

  public String getTotalPops() {
    Cursor cursor = this.myDataBase.rawQuery("SELECT total_pops FROM user_Table", null);
    if (cursor.moveToFirst()) {
      do {
        return cursor.getString(0);
      } while (cursor.moveToNext());
    }
    if (cursor != null && !cursor.isClosed()) {
      cursor.close();
    }
    return "0";
  }

  public void setServerScore(String score) {

    String sql = "UPDATE user_Table SET serverscore='" + score + "'";
    this.myDataBase.execSQL(sql);
  }

  public String getServerScore() {
    Cursor cursor = this.myDataBase.rawQuery("SELECT serverscore FROM user_Table", null);
    if (cursor.moveToFirst()) {
      do {
        return cursor.getString(0);
      } while (cursor.moveToNext());
    }
    if (cursor != null && !cursor.isClosed()) {
      cursor.close();
    }
    return "0";
  }

  public void setTotalPops(String total_pops) {

    String sql = "UPDATE user_Table SET total_points='" + total_pops + "'";
    this.myDataBase.execSQL(sql);
  }

  public int verifyIfFull(String fb_id) {
    Cursor cursor = this.myDataBase.rawQuery(
        "SELECT isfull from user_Table WHERE fb_id = '" + fb_id + "'", null);
    if (cursor.moveToFirst()) {
      do {
        if (cursor.getString(cursor.getColumnIndex("isfull")).equals("yes")) {
          return -1;
        } else {
          return 1;
        }
      } while (cursor.moveToNext());
    }
    if (cursor != null && !cursor.isClosed()) {
      cursor.close();
    }
    return 1;
  }

  public void UpgradeToFull(String fb_id) {
    String sql = "UPDATE user_Table SET isfull='yes' WHERE fb_id = '" + fb_id + "'";
    this.myDataBase.execSQL(sql);
  }
}
