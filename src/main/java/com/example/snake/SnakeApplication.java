package com.example.snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class SnakeApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SnakeApplication.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), Screen.getPrimary().getBounds().getWidth() / 2, Screen.getPrimary().getBounds().getHeight() / 2);
        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.show();

        BoardController controller = fxmlLoader.getController();
        controller.focus();


    }


    public static void main(String[] args) {
        launch();
    }
}