package com.example.snake;

import com.example.snake.game.MovementDirection;
import com.example.snake.game.Point;
import com.example.snake.game.SnakeSegment;

public class BoardModel {
    private final int width;
    private final int height;
    private final SnakeSegment snakeHead;
    private final MovementDirection direction = MovementDirection.RIGHT;

    public BoardModel(int width, int height) {
        this.width = width;
        this.height = height;
        snakeHead = new SnakeSegment(new Point(5,5),new SnakeSegment(new Point(5,4), null));
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
}
