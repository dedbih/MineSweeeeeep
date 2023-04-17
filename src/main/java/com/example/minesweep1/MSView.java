package com.example.minesweep1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MSView {
    public static GridPane grid1;

    private Timeline timeline;
    private Label timeLabel;
    private Label hints;
    private int currentTime = 0;
    private int buttonPressCount = 0;
    public int pistasdisp = 0;
    public static List<String> safearray = new ArrayList<>();

    Image kero = new Image("file:C:\\Users\\lasle\\Downloads\\IntelijCosas\\MineSweep1\\src\\main\\resources\\com\\example\\minesweep1\\kerow.png");
    ImageView kerobg = new ImageView(kero);

    Image keroic = new Image("file:C:\\Users\\lasle\\Downloads\\IntelijCosas\\MineSweep1\\src\\main\\resources\\com\\example\\minesweep1\\keroicon.jpg");
    ImageView keroicon = new ImageView(keroic);


    public void start(Stage primaryStage) {
        TextField textField = new TextField();
        Button button = new Button("Set Bombs");
        HBox controls = new HBox(textField, button);
        controls.setAlignment(Pos.CENTER);
        controls.setSpacing(10.0);

        timeLabel = new Label("Time: 00:00");
        Label bombs = new Label("Bombs: 0");
        hints = new Label("Hints: " + pistasdisp );
        Label diff = new Label("Dificil=" + MSModel.isDifficult());

        Button switchButton = new Button("Switch diff");
        switchButton.setOnAction((e) -> {
            MSModel.setDifficult(!MSModel.isDifficult());
            System.out.println("diff=" + MSModel.isDifficult());
        });
        diff.textProperty().bind(Bindings.createStringBinding(() -> "Difficult: " + MSModel.isDifficult(), MSModel.difficultProperty()));
        HBox labels = new HBox(timeLabel, bombs, hints, diff, switchButton);
        labels.setAlignment(Pos.TOP_RIGHT);
        labels.setSpacing(10.0);

        button.setOnAction((event) -> {
            int numBombs = Integer.parseInt(textField.getText());
            MSModel.setNumBombs(numBombs);
            grid1 = this.createGrid();
            MSModel.setBomb(grid1);

            kerobg.setFitWidth(750);
            kerobg.setFitHeight(720);

            AnchorPane pane = new AnchorPane();
            pane.getChildren().addAll(kerobg, grid1);
            AnchorPane.setTopAnchor(grid1, 0.0);
            AnchorPane.setLeftAnchor(grid1, 0.0);

            VBox container = new VBox(pane);
            container.setSpacing(10.0);
//            container.setStyle("-fx-background-color: #FFFF6D");
            BorderPane root = new BorderPane();
            root.setTop(labels);
            root.setCenter(container);
            Scene scene = new Scene(root, 747, 712); //600,600
            primaryStage.setTitle("Minesweep!! щ(`Д´щ;)");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

            timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                currentTime++;
                timeLabel.setText("Time: " + String.format("%02d:%02d", currentTime / 60, currentTime % 60));
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        });

        VBox vBox = new VBox(controls);
        vBox.setAlignment(Pos.CENTER);
        BorderPane root = new BorderPane();
        root.setCenter(vBox);
        Scene scene = new Scene(root, 600.0, 600.0);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(keroic);
        primaryStage.show();
    }


    private GridPane createGrid() {
        GridPane grid = new GridPane();

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                Button button = new Button();
                button.setPrefSize(50.0, 50.0);
                button.setStyle("-fx-background-color: #B7F54B ; -fx-text-fill: white; -fx-font-family: Arial; -fx-font-size: 10;");
                grid.add(button, i, j);
                button.getProperties().put("revealed", false);
                int numBombs = MSModel.getNumBombs(grid, i, j);
                if (numBombs > 0) {
                    button.setText(String.valueOf(numBombs));
                }
                final int finalI = i;
                final int finalJ = j;
                button.setOnAction((event) -> {
                    System.out.println("Button pressed: i = " + finalI + ", j = " + finalJ);
                    if (!button.getProperties().containsKey("pressed")) {

                        if (button.getProperties().containsKey("bomb")) {
                            button.setText("B");
                            button.setStyle("-fx-background-color: #F84156; -fx-text-fill: white; -fx-font-family: Arial; -fx-font-size: 10; -fx-opacity: 1.0;");
                            MSModel.reveal(grid, finalI, finalJ);
                        } else {
                            int numBombsAround = MSModel.getNumBombs(grid, finalI, finalJ);

                            if (numBombsAround == 0) {
                                MSModel.reveal(grid, finalI, finalJ);
                            } else {
                                button.setText(String.valueOf(numBombsAround));
                                if (button.getText().isEmpty()) {
                                    MSModel.fire(grid1);
                                }
                                if (numBombsAround>2){
                                    button.setStyle("-fx-background-color: #f4acb9; -fx-background-radius: 0; -fx-text-fill: white; -fx-font-family: Arial; -fx-font-size: 10; -fx-opacity: 1.0;");
                                }
                                if (numBombsAround==1){
                                    button.setStyle("-fx-background-color: #e2fbb7; -fx-background-radius: 0; -fx-text-fill: white; -fx-font-family: Arial; -fx-font-size: 10; -fx-opacity: 1.0;");
                                }
                                MSModel.fire(grid1);}
                        }
                    }

                    buttonPressCount++;
                    if (buttonPressCount % 5 == 0) {
                        pistasdisp++;
//                        MSModel.pistas.push(Integer.valueOf(safearray.get(0)));
                        hints.setText("Hints: " + pistasdisp);
                    }
                    button.getProperties().put("revealed", true);
                    button.getProperties().put("pressed", true);
                    button.setDisable(true);
                    MSModel.listamakergen();
                    MSModel.showList();
                });

                button.setOnMouseClicked((MouseEvent event) -> {
                    if (event.getButton() == MouseButton.SECONDARY) {
                        button.setText("\uD83D\uDEA9");
                    }
                });
            }
        }
        return grid;
}
}