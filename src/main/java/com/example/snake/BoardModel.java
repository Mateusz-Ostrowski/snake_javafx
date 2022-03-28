package com.example.snake;

import com.example.snake.game.MovementDirection;
import com.example.snake.game.Point;
import com.example.snake.game.SnakeSegment;

import java.util.Random;

public class BoardModel {
  private final int width;
  private final int height;
  private final SnakeSegment snakeHead;
  private MovementDirection direction;
  private final Point foodLocation;
  private final Random random;

  public BoardModel(int width, int height, MovementDirection startDirection) {
    this.random = new Random();
    this.width = width;
    this.height = height;
    snakeHead = new SnakeSegment(new Point(5, 5), new SnakeSegment(new Point(5, 4), null));
    direction = startDirection;
    foodLocation = new Point(random.nextInt(width),random.nextInt(height));
  }

  public void move() {
    SnakeSegment currentSegment = snakeHead.getPrev();
    Point prevLocation = snakeHead.getLocation();
    snakeHead.setLocation(prevLocation.plus(direction.getVector()));
    while (currentSegment != null) {
      Point tmp = currentSegment.getLocation();
      currentSegment.setLocation(prevLocation);
      prevLocation = tmp;
      currentSegment = currentSegment.getPrev();
    }

    if(snakeHead.getLocation().equals(foodLocation)){
      SnakeSegment lastSegment = snakeHead;
      while(lastSegment.getPrev()!=null){
        lastSegment = lastSegment.getPrev();
      }
      lastSegment.setPrev(new SnakeSegment(prevLocation,null));
      spawnFood();

      System.out.println("TEST");
    }
  }
//    public FieldType[][] getFields() {
//        return fields;
//    }
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

  public void changeDirection(MovementDirection direction) {
    if (!direction.getVector().isReverse(this.direction.getVector())) {
      this.direction = direction;
    }
  }

  public Point getFoodLocation() {
    return foodLocation;
  }

  public void spawnFood(){
    this.foodLocation.setX(random.nextInt(width));
    this.foodLocation.setY(random.nextInt(height));
  }
}
