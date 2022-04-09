package com.example.snake;

import com.example.snake.game.MovementDirection;
import com.example.snake.game.Vector2D;
import com.example.snake.game.SnakeSegment;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Klasa odpowiadająca za graficzną reprezentacje całego interfejsu gry oraz wykonująca pętle gry
 */
public class BoardController implements Initializable {
    /**
     * Szerokość planszy (ilość kwadratów)
     */
    private final int BOARD_WIDTH = 14;

    /**
     * Szerokość planszy (ilość kwadratów)
     */
    private final int BOARD_HEIGHT = 14;

    /**
     * Bok pojedyńczego kwadratu
     */
    private final double SQUARE_SIZE = 30;

    /**
     * Stan gry
     */
    private final BoardModel boardModel;

    /**
     * Flaga reprezentująca czy gra jest zapauzowana
     */
    private boolean isPaused = true;

    /**
     * Konstruktor
     */
    public BoardController() {
        this.boardModel = new BoardModel(BOARD_WIDTH, BOARD_HEIGHT, MovementDirection.DOWN);
    }

    @FXML
    private Canvas boardCanvas;

    @FXML
    private Label scoreLabel;

    @FXML
    private Button startButton;

    /**
     * Wykrywa kliknięcia przycisków i wywołuje zmiane na odpowiedni kierunek
     * @param event kod przycisku z klawiatury
     */
    @FXML
    protected void onKeyPressed(KeyEvent event) {
        if (!isPaused) {
            switch (event.getCode()) {
                case UP -> boardModel.changeDirection(MovementDirection.UP);
                case DOWN -> boardModel.changeDirection(MovementDirection.DOWN);
                case LEFT -> boardModel.changeDirection(MovementDirection.LEFT);
                case RIGHT -> boardModel.changeDirection(MovementDirection.RIGHT);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Petla gry
        Timeline t = new Timeline(new KeyFrame(Duration.millis(170), event -> {
            if (!isPaused) {
                boardModel.update();
            }
            if (!boardModel.isGameOver()) {
                drawBackground();
                drawSnake();
                drawFood();
                scoreLabel.setText("Wynik: " + boardModel.getScore());
            } else {
                scoreLabel.setText("Koniec gry twoj koncowy wynik to: " + boardModel.getScore());
                startButton.setVisible(true);
            }
        }));
        t.setCycleCount(Animation.INDEFINITE);
        t.play();
    }

    /**
     * restartuje gre oraz
     */
    public void startButton() {
        isPaused = false;
        startButton.setVisible(false);
        boardModel.restart();
    }

    /**
     * Rysuje na canvasie kratki odpowiadające planszy po której porusza się wąż
     */
    private void drawBackground() {
        for (int i = 0; i < boardModel.getHeight(); i++) {
            for (int j = 0; j < boardModel.getWidth(); j++) {
                if ((i + j) % 2 == 0) {
                    boardCanvas.getGraphicsContext2D().setFill(Color.web("AAD751"));
                    boardCanvas.getGraphicsContext2D().fillRect(j * SQUARE_SIZE, i * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                } else {
                    boardCanvas.getGraphicsContext2D().setFill(Color.web("A2D149"));
                    boardCanvas.getGraphicsContext2D().fillRect(j * SQUARE_SIZE, i * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                }
            }
        }
    }

    /**
     * Rysuje wszystkie segmenty węża
     */
    private void drawSnake() {
        drawSnakeSegment(boardModel.getSnakeHead());

        SnakeSegment currentSegment = boardModel.getSnakeHead().getPrev();
        while (currentSegment != null) {
            drawSnakeSegment(currentSegment);
            currentSegment = currentSegment.getPrev();
        }
    }

    /**
     * Rysuje pojedyńczy segment węża
     * @param segment segment do narysowania
     */
    private void drawSnakeSegment(SnakeSegment segment) {
        Vector2D location = segment.getLocation();
        if (boardModel.getSnakeHead().equals(segment)) {
            boardCanvas.getGraphicsContext2D().drawImage(resolveSegmentImage(segment), location.getX() * SQUARE_SIZE, location.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        } else {
            boardCanvas.getGraphicsContext2D().drawImage(resolveSegmentImage(segment), location.getX() * SQUARE_SIZE, location.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        }
    }
    /**
     * Zwraca odpowiedni Image z pliku graficznego segmentu węża zależnie od tego czy jest ono głową, ogonem czy
     * ciałem
     * @param segment
     * @return odpowiedni Image z pliku graficznego
     */
    private Image resolveSegmentImage(SnakeSegment segment) {
        if (segment.getPrev() == null) {
            return resolveTailDirection(segment);
        }
        if (segment.equals(boardModel.getSnakeHead())) {
            return resolveHeadImageFromDirection(segment);
        }
        return resolveBodyDirection(segment);
    }

    /**
     * Zwraca odpowiedni Image z pliku graficznego ogona węża zależnie od kierunku danego segmentu
     * Kierunek jest sprawdzany na podstawie położenia obecnego segmentu i do niego poprzedniego
     * @param segment segment którego kierunek jest sprawdzana
     * @return odpowiedni Image z pliku graficznego głowy węża
     */
    private Image resolveHeadImageFromDirection(SnakeSegment segment) {
        if(segment.getNext()!=null){
            throw new IllegalArgumentException("Głowa powinna być pierwszym segmentem!");
        }

        String imageUrl = "images/head_";
        String suffix;
        Vector2D currentLocation = segment.getLocation();
        Vector2D prevLocation = segment.getPrev().getLocation();
        if (currentLocation.getX() > prevLocation.getX()) {
            suffix = "right.png";
        }
        else if (currentLocation.getY() > prevLocation.getY()) {
            suffix = "down.png";
        }
        else if (currentLocation.getX() < prevLocation.getX()) {
            suffix = "left.png";
        }else{
            suffix = "up.png";
        }
        return new Image(imageUrl+suffix);
    }

    /**
     * Zwraca odpowiedni Image z pliku graficznego ogona węża zależnie od kierunku danego segmentu
     * Kierunek jest sprawdzany na podstawie położenia segmentu i jego następnego
     * @param segment segment którego kierunek jest sprawdzana
     * @return Image z odpowiedniego pliku graficznego ogona węża
     */
    private Image resolveTailDirection(SnakeSegment segment) {
        if(segment.getPrev()!=null){
            throw new IllegalArgumentException("Ogon powinien być ostatnim segmentem!");
        }
        if(segment.getNext()==null){
            throw new IllegalArgumentException("Ogon nie powinien być pierwszym segmentem!");
        }

        String imageUrl = "images/tail_";
        String suffix;
        Vector2D currentLocation = segment.getLocation();
        Vector2D nextLocation = segment.getNext().getLocation();
        if (currentLocation.getX() > nextLocation.getX()) {
            suffix = "right.png";
        }
        else if (currentLocation.getY() > nextLocation.getY()) {
            suffix = "down.png";
        }
        else if (currentLocation.getX() < nextLocation.getX()) {
            suffix = "left.png";
        }else{
            suffix = "up.png";
        }
        return new Image(imageUrl+suffix);
    }

    /**
     * Zwraca odpowiedni Image z pliku graficznego ciała węża zależnie od kierunku danego segmentu
     * Kierunek jest sprawdzany na podstawie położenia segmentu między poprzednim a następnym
     * @param segment segment którego kierunek jest sprawdzana
     * @return Image z odpowiedniego pliku graficznego ciała węża
     */
    private Image resolveBodyDirection(SnakeSegment segment) {
        if(segment.getNext() == null){
            throw new IllegalArgumentException("Ciało nie powinno być pierwszym segmentem");
        }
        if(segment.getPrev() == null){
            throw new IllegalArgumentException("Ciało nie powinno być ostatnim segmentem");
        }
        String imageUrl = "images/body_";
        String suffix;
        Vector2D nextLocation = segment.getNext().getLocation();
        Vector2D currentLocation = segment.getLocation();
        Vector2D prevLocation = segment.getPrev().getLocation();
        if (prevLocation.getY() == nextLocation.getY()) {
             suffix = "horizontal.png";
        }
        else if (prevLocation.getX() == nextLocation.getX()) {
            suffix = "vertical.png";
        }
        else if (isTopLeft(prevLocation, currentLocation, nextLocation)) {
            suffix ="topleft.png";
        }
        else if (isTopRight(prevLocation, currentLocation, nextLocation)) {
            suffix ="topright.png";
        }
        else if (isBottomLeft(prevLocation, currentLocation, nextLocation)) {
            suffix ="bottomleft.png";
        }
        else if (isBottomRight(prevLocation, currentLocation, nextLocation)) {
            suffix ="bottomright.png";
        }else {
            suffix = "vertical.png";
        }
        return new Image(imageUrl+suffix);

    }


    /**
     * Sprawdza czy 3 podane punkty tworzą prawy dalny róg
     * (dowolna kolejnosc moze byc zgodnie z ruchem wskazowek zegara lub nie)
     * @param prev punkt porzedni
     * @param current punkt miedzy prev a next
     * @param next punkt nastepny
     * @return
     */
    private boolean isTopLeft(Vector2D prev, Vector2D current, Vector2D next) {
        return (current.isAbove(next) && current.isLeft(prev))
                ||
                (current.isAbove(prev) && current.isLeft(next));
    }

    /**
     * Sprawdza czy 3 podane punkty tworzą prawy górny róg
     * (dowolna kolejnosc moze byc zgodnie z ruchem wskazowek zegara lub nie)
     * @param prev punkt porzedni
     * @param current punkt miedzy prev a next
     * @param next punkt nastepny
     * @return prawda jeśli 3 podane punkty tworzą prawy górny róg w innym wypadku fałsz
     */
    private boolean isTopRight(Vector2D prev, Vector2D current, Vector2D next) {
        return (current.isAbove(next) && current.isRight(prev))
                ||
                (current.isAbove(prev) && current.isRight(next));
    }

    /**
     * Sprawdza czy 3 podane punkty tworzą lewy dolny róg
     * (dowolna kolejnosc moze byc zgodnie z ruchem wskazowek zegara lub nie)
     * @param prev punkt porzedni
     * @param current punkt miedzy prev a next
     * @param next punkt nastepny
     * @return prawda jeśli 3 podane punkty tworzą lewy dolny róg w innym wypadku fałsz
     */
    private boolean isBottomLeft(Vector2D prev, Vector2D current, Vector2D next) {
        return (current.isBelow(next) && current.isLeft(prev))
                ||
                (current.isBelow(prev) && current.isLeft(next));
    }


    /**
     * Sprawdza czy 3 podane punkty tworzą prawy dolny róg
     * (dowolna kolejnosc moze byc zgodnie z ruchem wskazowek zegara lub nie)
     * @param prev punkt porzedni
     * @param current punkt miedzy prev a next
     * @param next punkt nastepny
     * @return prawda jeśli 3 podane punkty tworzą prawy dolny róg w innym wypadku fałsz
     */
    private boolean isBottomRight(Vector2D prev, Vector2D current, Vector2D next) {
        return (current.isBelow(next) && current.isRight(prev))
                ||
                (current.isBelow(prev) && current.isRight(next));
    }

    /**
     * Rysuje jedzenie dla węża
     */
    private void drawFood() {
        boardCanvas.getGraphicsContext2D().drawImage(new Image("images/apple.png"), boardModel.getFoodLocation().getX() * SQUARE_SIZE, boardModel.getFoodLocation().getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

    /**
     * Wywoluje metode requestFocus na canvasie aby wejscie od uzytkownika moglo byc odczytywane
     */
    public void focus() {
        boardCanvas.requestFocus();
    }

}
