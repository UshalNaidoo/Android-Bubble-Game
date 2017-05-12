package com.goodguygames.bubblegame.physics;

public enum Direction {
  UP(-1),
  DOWN(1),
  LEFT(-1),
  RIGHT(1);

  public int value;

  Direction(int i) {
    this.value = i;
  }
}