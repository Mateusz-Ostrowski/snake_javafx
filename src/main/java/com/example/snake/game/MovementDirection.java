package com.example.snake.game;

import javafx.scene.input.KeyCode;

public enum MovementDirection {
    DOWN(0,1),
    UP(0,-1),
    RIGHT(1,0),
    LEFT(-1,0),
  ;

  private Point vector;

  MovementDirection(int x, int y) {
    this.vector = new Point(x,y);
  }

  public Point getVector() {
    return vector;
  }
}
