package com.example.snake;

import com.example.snake.game.MovementDirection;
import com.example.snake.game.Vector2D;
import com.example.snake.game.SnakeSegment;

import java.util.Random;

/**
 * Klasa przechowująca stan gry
 */
public class BoardModel {
  /**
   * Szerokość planszy (ilość kwadratów)
   */
  private final int width;
  /**
   * Wysokość planszy (ilość kwadratów)
   */
  private final int height;
  /**
   * Wysokość planszy (ilość kwadratów)
   */
  private SnakeSegment snakeHead;
  /**
   * Kierunek z początku gry
   */
  private MovementDirection startDirection;
  /**
   * Obecny kierunek
   */
  private MovementDirection direction;
  /**
   * Obecna pozycja jedzenia
   */
  private final Vector2D foodLocation;

  private final Random random;

  /**
   * Flaga reprezentująca czy gra została zakończona
   */
  private boolean gameOver = false;
  /**
   * Wynik
   */
  private int score;


  public BoardModel(int width, int height, MovementDirection startDirection) {
    this.random = new Random();
    this.width = width;
    this.height = height;
    snakeHead = new SnakeSegment(new Vector2D(5, 5));
    snakeHead.setPrev(new SnakeSegment(new Vector2D(5, 4)));
    direction = startDirection;
    this.startDirection = startDirection;
    foodLocation = new Vector2D(-1, -1);
    score = 0;
  }

  /**
   * Restartuje wszystkie dane zmieniające się podczas gry
   */
  public void restart() {
    snakeHead.setLocation(new Vector2D(5, 5));
    snakeHead.getPrev().setLocation(new Vector2D(5, 4));
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
      Vector2D prevLocation = snakeHead.getLocation();
      snakeHead.setLocation(prevLocation.plus(direction.getVector()));
      while (currentSegment != null) {
        Vector2D tmp = currentSegment.getLocation();
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
  /**
   * {@link BoardModel#snakeHead}
   */
  public SnakeSegment getSnakeHead() {
    return snakeHead;
  }

  /**
   * {@link BoardModel#width}
   */
  public int getWidth() {
    return width;
  }

  /**
   * {@link BoardModel#height}
   */
  public int getHeight() {
    return height;
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

  /**
   * {@link BoardModel#foodLocation}
   */
  public Vector2D getFoodLocation() {
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

  /**
   * {@link BoardModel#score}
   */
  public int getScore() {
    return score;
  }

  /**
   * {@link BoardModel#gameOver}
   */
  public boolean isGameOver() {
    return gameOver;
  }
}