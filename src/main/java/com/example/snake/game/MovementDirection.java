package com.example.snake.game;

/**
 * Enum reprezentujący kierunek poruszania się i dla uproszczenia logiki zawiera wektory
 * które będą dodawane do położenia głowy węża
 */
public enum MovementDirection {
    DOWN(0,1),
    UP(0,-1),
    RIGHT(1,0),
    LEFT(-1,0),
  ;

  private final Point vector;

  MovementDirection(int x, int y) {
    this.vector = new Point(x,y);
  }

  public Point getVector() {
    return vector;
  }
}
