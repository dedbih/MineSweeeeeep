package com.example.minesweep1;

import javafx.application.Application;
import javafx.stage.Stage;

public class MineMain extends Application {

    @Override
    public void start(Stage primaryStage) {
        MSController controller = new MSController();
        controller.startGame(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}