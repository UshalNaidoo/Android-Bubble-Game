package com.goodguygames.bubblegame.util;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.goodguygames.bubblegame.animations.AnimationBounceInterpolator;
import com.goodguygames.bubblegame.demo.MainMenu;
import com.goodguygames.bubblegame.demo.R;

public class AnimationHelper {

  public static Animation getButtonAnimation () {
    final Animation animation = AnimationUtils.loadAnimation(MainMenu.getAppContext(), R.anim.bounce);
    AnimationBounceInterpolator interpolator = new AnimationBounceInterpolator(0.2, 20);
    animation.setInterpolator(interpolator);
    return animation;
  }

}
