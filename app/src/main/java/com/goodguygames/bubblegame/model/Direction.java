package com.goodguygames.bubblegame.model;

public enum Direction {
  UP(-1),
  DOWN(1),
  LEFT(-10),
  RIGHT(10);

  public int value;

  Direction(int i) {
    this.value = i;
  }
}