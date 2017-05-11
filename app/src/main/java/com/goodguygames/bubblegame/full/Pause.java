package com.goodguygames.bubblegame.full;


import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class Pause extends Activity {

  private ImageView play;
  private MediaPlayer mp;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.pausescreen);

    this.play = (ImageView) this.findViewById(R.id.imageView1);
    this.play.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (view == findViewById(R.id.imageView1)) {
          play.setImageResource(R.drawable.playpressed);
          mp = MediaPlayer.create(Pause.this, R.raw.bub_pop);
          mp.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
              mp.release();
            }

          });
          mp.start();
          finish();
        }
      }
    });

    Button exit = (Button) this.findViewById(R.id.exitButton);
    exit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (view == findViewById(R.id.exitButton)) {
          setResult(RESULT_OK, null);
          finish();
        }
      }
    });
  }

}