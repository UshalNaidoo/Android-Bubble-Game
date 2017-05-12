package com.goodguygames.bubblegame.physics;

public enum Speed {
  REVERSE(-1),
  STOP(0),
  SLOW(2),
  NORMAL(5),
  FAST(8),
  SUPERFAST(11);

  public int value;

  Speed(int i) {
    this.value = i;
  }
}