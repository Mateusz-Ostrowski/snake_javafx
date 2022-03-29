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
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class BoardController implements Initializable {
    private final int BOARD_WIDTH = 16;
    private final int BOARD_HEIGHT = 16;
    private final double SQUARE_SIZE = 30;
    private final double SNAKE_HEAD_SIZE = 22;
    private final double SNAKE_BODY_SIZE = 14;

    private final BoardModel boardModel;

    public BoardController() {
        this.boardModel = new BoardModel(BOARD_WIDTH,BOARD_HEIGHT, MovementDirection.RIGHT);
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
          boardModel.move();
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
                    boardCanvas.getGraphicsContext2D().setFill(Color.web("49ba18"));
                    boardCanvas.getGraphicsContext2D().fillRect(j * SQUARE_SIZE, i * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                }else{
                    boardCanvas.getGraphicsContext2D().setFill(Color.web("4fe010"));
                    boardCanvas.getGraphicsContext2D().fillRect(j * SQUARE_SIZE, i * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                }
            }
        }
    }

    private void drawSnake(){
        drawSnakeSegment(boardModel.getSnakeHead(),true);

        SnakeSegment currentSegment = boardModel.getSnakeHead().getPrev();
        while(currentSegment != null){
            drawSnakeSegment(currentSegment,false);
            currentSegment = currentSegment.getPrev();
        }
    }

    private void drawSnakeSegment(SnakeSegment segment, boolean isHead){
        Point location = segment.getLocation();
        if(isHead){
            boardCanvas.getGraphicsContext2D().setFill(Color.web("0000bb"));
            boardCanvas.getGraphicsContext2D().fillRect(location.getX() * SQUARE_SIZE, location.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        }else {
            boardCanvas.getGraphicsContext2D().setFill(Color.web("0000ff"));
            boardCanvas.getGraphicsContext2D().fillRect(location.getX() * SQUARE_SIZE, location.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        }
    }

    private void drawFood(){
        boardCanvas.getGraphicsContext2D().setFill(Color.web("ff0000"));
        boardCanvas.getGraphicsContext2D().fillRect(
          boardModel.getFoodLocation().getX()*SQUARE_SIZE,
          boardModel.getFoodLocation().getY()*SQUARE_SIZE,
            SQUARE_SIZE,SQUARE_SIZE);
    }

    public void focus(){
        boardCanvas.requestFocus();
    }

}
