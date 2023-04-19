package com.example.minesweep1;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import static com.example.minesweep1.MSView.grid1;


public class MSModel {
    public static int arsenal;
    private static BooleanProperty difficult = new SimpleBooleanProperty(false);

    public static boolean isDifficult() {
        return difficult.get();
    }

    public static void setDifficult(boolean value) {
        difficult.set(value);
    }

    public static BooleanProperty difficultProperty() {
        return difficult;
    }

    public static LinkedList<Button> listagen = new LinkedList<>();
    public static LinkedList<Button> listasafe = new LinkedList<>();
    public static LinkedList<Button> listaidk = new LinkedList<>();
    public static Stack<Integer> pistas = new Stack<>();
    public static LinkedList<Button> defijosafe = new LinkedList<>();

    public static void setNumBombs(int numBombs) {
        arsenal = numBombs;
    }

    public static void reveal(GridPane grid, int x, int y) {
        for (int i = Math.max(0, x - 1); i <= Math.min(7, x + 1); i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(7, y + 1); j++) {
                Button button = (Button) grid.getChildren().get(j * 8 + i);
                if (button.getText().isEmpty()) {
                    int numBombsAround = getNumBombs(grid, i, j);
                    if (numBombsAround == 0) {
                        button.setText("0");
                        button.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #000000; -fx-font-family: Arial; -fx-font-size: 10;");
                        button.setDisable(true);
                        reveal(grid, i, j);
                    } else {
                        button.setText(String.valueOf(numBombsAround));
                        button.setStyle("-fx-background-color: #FF6DE8; -fx-text-fill: white; -fx-font-family: Arial; -fx-font-size: 10;");
                        button.setDisable(true);
                    }
                }
            }
        }
    }

    public static void fire(GridPane grid) {
        Random rand = new Random();
        if (!difficult.get()) {
            int size = listagen.getSize();
            int r = (size > 0) ? rand.nextInt(size) : 0;
            Button rButton = listagen.get(r);
            int i = GridPane.getRowIndex(rButton);
            int j = GridPane.getColumnIndex(rButton);
            System.out.println("Firing: " + i + "," + j);
            if (!rButton.isPressed() && !rButton.isDisabled() && rButton.getOpacity() == 1) {
                rButton.fire();
                listagen.removeNode(rButton);
            }
            listamakergen();
        } else if (difficult.get()) {
            listamakergen();
            safelistgen();
            showList();
            int size = listasafe.getSize();
            int rr = (size > 0) ? rand.nextInt(size) : 0;
            Button rrButton = listagen.get(rr);
            int ii = GridPane.getRowIndex(rrButton);
            int jj = GridPane.getColumnIndex(rrButton);
            System.out.println("Firing: " + ii + "," + jj);
            if (!rrButton.isPressed() && !rrButton.isDisabled() && rrButton.getOpacity() == 1) {
                rrButton.fire();
                listagen.removeNode(rrButton);
            }
            listamakergen();
        }
    }


    public static int getNumBombs(GridPane grid, int x, int y) {
        int numBombs = 0;
        for (int i = Math.max(0, x - 1); i <= Math.min(7, x + 1); i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(7, y + 1); j++) {
                int index = j * 8 + i;
                if (index >= 0 && index < grid.getChildren().size()) {
                    Button button = (Button) grid.getChildren().get(index);
                    if (button.getProperties().containsKey("bomb")) {
                        numBombs++;
                    }
                }
            }
        }
        return numBombs;
    }

    public static void setBomb(GridPane grid) {
        Random random = new Random();
        //    int arsenal = 8;
        while (arsenal > 0) {
            int x = random.nextInt(8);
            int y = random.nextInt(8);
            Button button = (Button) grid.getChildren().get(y * 8 + x); //Consigue índice del botón
            if (!button.getProperties().containsKey("bomb")) {
                button.getProperties().put("bomb", true);
                arsenal--;
            }
        }
    }

    public static void listamakergen() {
        if (listagen != null) {
            listagen.clearList();
        }
        for (Node node : grid1.getChildren()) {
            if (node instanceof Button button) {
                boolean revealed = (boolean) button.getProperties().get("revealed");
                boolean pressed = (boolean) button.getProperties().getOrDefault("pressed", false);

                if (!revealed && !pressed) {
                    listagen.addNode(button);
                }
            }
        }
    }

    public static void safelistgen() {
        if (listasafe != null) {
            listasafe.clearList();
        }

        for (Node node : grid1.getChildren()) {
            if (node instanceof Button button) {
                boolean revealed = (boolean) button.getProperties().get("revealed");
                boolean pressed = (boolean) button.getProperties().getOrDefault("pressed", false);

                if (!revealed && !pressed) {
                    int row = GridPane.getRowIndex(button);
                    int col = GridPane.getColumnIndex(button);
                    for (int i = row - 1; i <= row + 1; i++) {
                        for (int j = col - 1; j <= col + 1; j++) {
                            if (i >= 0 && i < 8 && j >= 0 && j < 8 && (i != row || j != col)) {
                                Button neighbor = (Button) grid1.getChildren().get(i * 8 + j);
                                String text = neighbor.getText();
                                if (text.matches("[0-8]")) {
                                    listasafe.addNode(neighbor);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public static void safegen() {
        for (Node b : grid1.getChildren()) {
                if ((!b.isDisabled() && !b.isPressed() && (!b.getProperties().containsKey("pressed")) && (!b.getProperties().containsKey("bomb")))) {
                    defijosafe.addNode((Button) b);
                }
            }
        }


    public static void showList() {
        System.out.println("Lista general");
        listagen.traverseList();
        System.out.println("Lista segura");
        listasafe.traverseList();
    }

    public static void perder() {
        for (Node node : grid1.getChildren()) {
            if (node instanceof Button button) {
                button.setDisable(true);
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("BOOM");
        alert.setHeaderText(null);
        alert.setContentText("GAME OVER!! ヽ(*｀ﾟД´)ﾉ");
        alert.showAndWait();
    }


}