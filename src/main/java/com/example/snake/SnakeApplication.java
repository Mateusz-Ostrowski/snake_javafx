package com.example.snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Klasa będaca puntem wejśćiowym całej aplikacji
 */
public class SnakeApplication extends Application {


    /**
     * Punkt wejściowy dla aplikacji JavaFX
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SnakeApplication.class.getResource("game-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), Screen.getPrimary().getBounds().getWidth()*0.8, Screen.getPrimary().getBounds().getHeight()*0.8);
        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.show();

        //Ustawiamy focus na canvas aby kontroler mógł wykrywać wejścia z klawiatury
        BoardController controller = fxmlLoader.getController();
        controller.focus();


    }

    /**
     * Punkt wejśiowy programu napisanego w języku Java
     * Wywołuje {@link SnakeApplication#start(Stage)}
     * @param args nie używane
     */
    public static void main(String[] args) {
        launch();
    }
}
