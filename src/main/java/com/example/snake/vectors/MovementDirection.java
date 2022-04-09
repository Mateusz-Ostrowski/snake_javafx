package com.example.snake.vectors;

/**
 * Enum reprezentujący kierunek poruszania się
 */
public enum MovementDirection {
    DOWN(0,1),
    UP(0,-1),
    RIGHT(1,0),
    LEFT(-1,0),
  ;

    /**
     * Wektor jednostkowy odpowiadający kierunkowi
     */
    private final Vector2D vector;

  MovementDirection(int x, int y) {
    this.vector = new Vector2D(x,y);
  }

    /**
     * {@link MovementDirection#vector}
     */
  public Vector2D getVector() {
    return vector;
  }
}
