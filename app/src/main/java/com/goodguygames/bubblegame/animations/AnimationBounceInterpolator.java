package com.goodguygames.bubblegame.animations;

public class AnimationBounceInterpolator implements android.view.animation.Interpolator {
  double mAmplitude = 1;
  double mFrequency = 10;

  public AnimationBounceInterpolator(double amplitude, double frequency) {
    mAmplitude = amplitude;
    mFrequency = frequency;
  }

  public float getInterpolation(float time) {
    return (float) (-1 * Math.pow(Math.E, -time/ mAmplitude) *
                    Math.cos(mFrequency * time) + 1);
  }
}