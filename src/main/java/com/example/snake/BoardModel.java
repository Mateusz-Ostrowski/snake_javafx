package com.example.snake;

import com.example.snake.game.MovementDirection;
import com.example.snake.game.Point;
import com.example.snake.game.SnakeSegment;

import java.util.Random;

/**
 * Klasa przechowująca stan gry
 */
public class BoardModel {
  private final int width;
  private final int height;
  private SnakeSegment snakeHead;
  private MovementDirection startDirection;
  private MovementDirection direction;
  private final Point foodLocation;
  private final Random random;
  private boolean gameOver = false;
  private int score;


  public BoardModel(int width, int height, MovementDirection startDirection) {
    this.random = new Random();
    this.width = width;
    this.height = height;
    snakeHead = new SnakeSegment(new Point(5, 5));
    snakeHead.setPrev(new SnakeSegment(new Point(5, 4)));
    direction = startDirection;
    this.startDirection = startDirection;
    foodLocation = new Point(-1, -1);
    score = 0;
  }

  /**
   * Restartuje wszystkie dane zmieniające się podczas gry
   */
  public void restart() {
    snakeHead.setLocation(new Point(5, 5));
    snakeHead.getPrev().setLocation(new Point(5, 4));
    snakeHead.getPrev().setPrev(null);
    direction = startDirection;
    score = 0;
    gameOver = false;
    spawnFood();

  }

  /**
   * Odświeża stan gry tzn.
   *
   * Przesuwa głowę o wektor przechowywany przez kierunek
   * Przesuwa każdy poprzedni segment na pozycje następnego
   * Sprawdza kolizje ze ścianami i samym sobą
   * Sprawdza kolizje z jedzeniem
   */
  public void update() {
    if (!gameOver) {

      //Przesunięcie całego węza
      SnakeSegment currentSegment = snakeHead.getPrev();
      Point prevLocation = snakeHead.getLocation();
      snakeHead.setLocation(prevLocation.plus(direction.getVector()));
      while (currentSegment != null) {
        Point tmp = currentSegment.getLocation();
        currentSegment.setLocation(prevLocation);
        prevLocation = tmp;
        currentSegment = currentSegment.getPrev();
      }

      //Sprawdzenie i obsługa kolizji ze ścianami lub segmentami węża
      detectCollision();

      //Sprawdzenie i obsługa kolizji z jedzeniem
      if (snakeHead.getLocation().equals(foodLocation)) {
        SnakeSegment lastSegment = snakeHead;
        while (lastSegment.getPrev() != null) {
          lastSegment = lastSegment.getPrev();
        }
        score++;
        lastSegment.setPrev(new SnakeSegment(prevLocation, null));
        spawnFood();
      }
    }
  }

  /**
   * Sprawdza kolizje z ścianami lub poprzednimi segmentami
   * Jeśli wystąpi to ustawia flage gameOver na wartość true
   */
  private void detectCollision() {
    if (
            snakeHead.getLocation().getX() >= width ||
                    snakeHead.getLocation().getX() < 0 ||
                    snakeHead.getLocation().getY() >= height ||
                    snakeHead.getLocation().getY() < 0) {
      gameOver = true;

    } else {
      SnakeSegment currentSegment = snakeHead.getPrev();
      while (currentSegment != null) {
        if (getSnakeHead().getLocation().equals(currentSegment.getLocation())) {
          gameOver = true;
        }
        currentSegment = currentSegment.getPrev();
      }
    }
  }

  public SnakeSegment getSnakeHead() {
    return snakeHead;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public MovementDirection getDirection() {
    return direction;
  }

  /**
   * Sprawdza czy można zmienić kierunek węża na wybrany
   * Jeśli można to zmienia
   * @param direction nowy kierunek węża
   */
  public void changeDirection(MovementDirection direction) {
    if (!snakeHead.getLocation().plus(direction.getVector()).equals(snakeHead.getPrev().getLocation())) {
      this.direction = direction;
    }
  }

  public Point getFoodLocation() {
    return foodLocation;
  }

  /**
   * Ustawia nową losową pozycje jedzenia tak aby nie pokrywała się z segmentami węża
   */
  public void spawnFood() {
    do {
      this.foodLocation.setX(random.nextInt(width));
      this.foodLocation.setY(random.nextInt(height));
    } while (snakeHead.contains(foodLocation));
  }

  public int getScore() {
    return score;
  }

  public Boolean isGameOver() {
    return gameOver;
  }
}