package com.example.minesweep1;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.Random;




public class MSModel {
    public static int arsenal;


    public static void setNumBombs(int numBombs) {
        arsenal=numBombs;}

    public static void reveal(GridPane grid, int x, int y) {
        for (int i = Math.max(0, x - 1); i <= Math.min(7, x + 1); i++) { //Busca que x no se salga del índice de la matriz, si x<0, 0. si x>7, 7.
            for (int j = Math.max(0, y - 1); j <= Math.min(7, y + 1); j++) {
                Button button = (Button) grid.getChildren().get(j * 8 + i);
                if (button.getText().isEmpty()) {
                    int numBombsAround = getNumBombs(grid, i, j);
                    if (numBombsAround == 0) {
                        button.setText("0");
                        button.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #000000; -fx-font-family: Arial; -fx-font-size: 10;");
                        reveal(grid, i, j);
                    } else {
                        button.setText(String.valueOf(numBombsAround));
                        button.setStyle("-fx-background-color: #FF6DE8; -fx-text-fill: white; -fx-font-family: Arial; -fx-font-size: 10;");
                    }
                }
            }
        }
    }
    public static void fire(GridPane grid) {
        Random rand = new Random();
        int fil = rand.nextInt(8);
        int col = rand.nextInt(8);
        Button button = (Button) grid.getChildren().get(col * 8 + fil);
        if (button.getProperties().containsKey("pressed")){
            button.fire();
            button.fire();
        }
        else { button.fire();}
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
}