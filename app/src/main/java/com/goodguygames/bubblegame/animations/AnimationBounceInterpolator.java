package com.goodguygames.bubblegame.animations;

public class AnimationBounceInterpolator implements android.view.animation.Interpolator {
  private double amplitude = 1;
  private double frequency = 10;

  public AnimationBounceInterpolator(double amplitude, double frequency) {
    this.amplitude = amplitude;
    this.frequency = frequency;
  }

  public float getInterpolation(float time) {
    return (float) (-1 * Math.pow(Math.E, -time/ this.amplitude) * Math.cos(this.frequency * time) + 1);
  }
}