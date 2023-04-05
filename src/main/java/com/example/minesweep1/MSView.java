package com.example.minesweep1;

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

public class MSView {
    public GridPane grid1;

    public MSView() {
    }

    public void start(Stage primaryStage) {
        TextField textField = new TextField();
        Button button = new Button("Set Bombs");
        HBox controls = new HBox(textField, button);
        controls.setAlignment(Pos.CENTER);
        controls.setSpacing(10.0);

        Label time = new Label("Time: 0");
        Label bombs = new Label("Bombs: 0");
        Label hints = new Label("Hints: 0");
        Label diff = new Label("Dificil=" + MSModel.isDifficult());
        Button switchButton = new Button("Switch diff");
        switchButton.setOnAction((e) -> {
            MSModel.setDifficult(!MSModel.isDifficult());
            System.out.println("diff="+MSModel.isDifficult());
        });
        diff.textProperty().bind(Bindings.createStringBinding(() -> "Difficult: " + MSModel.isDifficult(), MSModel.difficultProperty()));
        HBox labels = new HBox(time,bombs,hints, diff, switchButton);
        labels.setAlignment(Pos.TOP_RIGHT);
        labels.setSpacing(10.0);

        button.setOnAction((event) -> {
            int numBombs = Integer.parseInt(textField.getText());
            MSModel.setNumBombs(numBombs);
            this.grid1 = this.createGrid();
            MSModel.setBomb(grid1);
            VBox container = new VBox(this.grid1);
            container.setSpacing(10.0);
            container.setStyle("-fx-background-color: #FFFF6D");
            BorderPane root = new BorderPane();
            root.setTop(labels);
            root.setCenter(container);
            Scene scene = new Scene(root, 600.0, 600.0);
            scene.setFill(Color.LIGHTBLUE);
            primaryStage.setTitle("Minesweep!! щ(`Д´щ;)");
            primaryStage.setScene(scene);
            primaryStage.show();
        });

        VBox vBox = new VBox(controls);
        vBox.setAlignment(Pos.CENTER);
        BorderPane root = new BorderPane();
        root.setCenter(vBox);
        Scene scene = new Scene(root, 600.0, 600.0);
        primaryStage.setTitle("Set Bombs");
        primaryStage.setScene(scene);
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
                            button.setStyle("-fx-background-color: #F84156; -fx-text-fill: white; -fx-font-family: Arial; -fx-font-size: 10;");
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
                                button.setStyle("-fx-background-color: #88CCEE; -fx-text-fill: white; -fx-font-family: Arial; -fx-font-size: 10;");
                            }
                        }
                    }
                    button.getProperties().put("pressed", true);
                    button.setDisable(true);});

                button.setOnMouseClicked((MouseEvent event) -> {
                    if (event.getButton() == MouseButton.SECONDARY) {
                        button.setText("\uD83D\uDEA9");
                    }
                });
            }
        }
        return grid;
    }}
