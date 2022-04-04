package com.example.snake;

import com.example.snake.game.MovementDirection;
import com.example.snake.game.Point;
import com.example.snake.game.SnakeSegment;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class BoardController implements Initializable {
    private final int BOARD_WIDTH = 14;
    private final int BOARD_HEIGHT = 14;
    private final double SQUARE_SIZE = 30;
    private final double SNAKE_HEAD_SIZE = 22;
    private final double SNAKE_BODY_SIZE = 14;

    private final BoardModel boardModel;
    private final boolean isPaused = true;
    public Button start;

    public BoardController() {
        this.boardModel = new BoardModel(BOARD_WIDTH,BOARD_HEIGHT, MovementDirection.DOWN);
    }

    @FXML
    private Canvas boardCanvas;

    @FXML
    protected void onKeyPressed(KeyEvent event) {
        switch (event.getCode()){
          case UP -> boardModel.changeDirection(MovementDirection.UP);
          case DOWN ->  boardModel.changeDirection(MovementDirection.DOWN);
          case LEFT -> boardModel.changeDirection(MovementDirection.LEFT);
          case RIGHT -> boardModel.changeDirection(MovementDirection.RIGHT);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

      Timeline t = new Timeline(new KeyFrame(Duration.millis(200), event -> {
          if(!isPaused) {
              boardModel.move();
          }
          if(!boardModel.getGameOver()) {
              drawBackground();
              drawSnake();
              drawFood();
          }
      }));
       t.setCycleCount(Animation.INDEFINITE);
       t.play();
    }

    private void drawBackground(){
        for(int i = 0;i<boardModel.getHeight();i++){
            for(int j = 0;j<boardModel.getWidth();j++){
                if((i+j)%2==0) {
                    boardCanvas.getGraphicsContext2D().setFill(Color.web("AAD751"));
                    boardCanvas.getGraphicsContext2D().fillRect(j * SQUARE_SIZE, i * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                }else{
                    boardCanvas.getGraphicsContext2D().setFill(Color.web("A2D149"));
                    boardCanvas.getGraphicsContext2D().fillRect(j * SQUARE_SIZE, i * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                }
            }
        }
    }

    private void drawSnake(){
        drawSnakeSegment(boardModel.getSnakeHead());

        SnakeSegment currentSegment = boardModel.getSnakeHead().getPrev();
        while(currentSegment != null){
            drawSnakeSegment(currentSegment);
            currentSegment = currentSegment.getPrev();
        }
    }

    private void drawSnakeSegment(SnakeSegment segment){
        Point location = segment.getLocation();
        if(boardModel.getSnakeHead().equals(segment)){
            boardCanvas.getGraphicsContext2D().drawImage(resolveSegmentImage(segment),location.getX() * SQUARE_SIZE, location.getY() * SQUARE_SIZE,SQUARE_SIZE,SQUARE_SIZE);
        }else {
            boardCanvas.getGraphicsContext2D().drawImage(resolveSegmentImage(segment),location.getX() * SQUARE_SIZE, location.getY() * SQUARE_SIZE,SQUARE_SIZE,SQUARE_SIZE);
        }
    }

    private Image resolveSegmentImage(SnakeSegment segment){
        if(segment.getPrev() == null){
            return new Image("images/tail_"+resolveTailDirection(segment));
        }
        if(segment.equals(boardModel.getSnakeHead())){
            return new Image("images/head_"+resolveHeadDirection(segment));
        }
        return new Image("images/body_"+resolveBodyDirection(segment));
    }

    private String resolveHeadDirection(SnakeSegment segment){
      Point currentLocation = segment.getLocation();
      Point prevLocation = segment.getPrev().getLocation();
      if(currentLocation.getX() > prevLocation.getX()){
        return "right.png";
      }
      if(currentLocation.getY() > prevLocation.getY()){
        return "down.png";
      }
      if(currentLocation.getX() < prevLocation.getX()){
        return "left.png";
      }
      return "up.png";
    }
    private String resolveTailDirection(SnakeSegment segment){
      Point currentLocation = segment.getLocation();
      Point nextLocation = segment.getNext().getLocation();
      if(currentLocation.getX() > nextLocation.getX()){
        return "right.png";
      }
      if(currentLocation.getY() > nextLocation.getY()){
        return "down.png";
      }
      if(currentLocation.getX() < nextLocation.getX()){
        return "left.png";
      }
      return "up.png";
    }
    private String resolveBodyDirection(SnakeSegment segment) {
        Point nextLocation = segment.getNext().getLocation();
        Point currentLocation = segment.getLocation();
        Point prevLocation = segment.getPrev().getLocation();
        if (prevLocation.getY() == nextLocation.getY()) {
            return "horizontal.png";
        }
        if (prevLocation.getX() == nextLocation.getX()) {
            return "vertical.png";
        }
        if (isBottomRight(prevLocation,currentLocation, nextLocation)) {
            return "bottomright.png";
        }
        if (isBottomLeft(prevLocation,currentLocation, nextLocation)) {
            return "bottomleft.png";
        }
        if (isTopRight(prevLocation,currentLocation, nextLocation)) {
            return "topright.png";
        }
        if (isTopLeft(prevLocation,currentLocation, nextLocation)) {
            return "topleft.png";
        }
        return "vertical.png";

    }

    private boolean isBottomRight(Point prev, Point current, Point next){
        return  (current.isAbove(next) && current.isLeft(prev))
                ||
                (current.isAbove(prev) && current.isLeft(next));
    }

    private boolean isBottomLeft(Point prev, Point current, Point next){
        return  (current.isAbove(next) && current.isRight(prev))
                ||
                (current.isAbove(prev) && current.isRight(next));
    }

    private boolean isTopRight(Point prev, Point current, Point next){
        return  (current.isBelow(next) && current.isLeft(prev))
                ||
                (current.isBelow(prev) && current.isLeft(next));
    }

    private boolean isTopLeft(Point prev, Point current, Point next){
        return  (current.isBelow(next) && current.isRight(prev))
                ||
                (current.isBelow(prev) && current.isRight(next));
    }

    private void drawFood(){
        boardCanvas.getGraphicsContext2D().drawImage(new Image("images/apple.png"), boardModel.getFoodLocation().getX() * SQUARE_SIZE, boardModel.getFoodLocation().getY() * SQUARE_SIZE,SQUARE_SIZE,SQUARE_SIZE);
    }

    public void focus(){
        boardCanvas.requestFocus();
    }

}
