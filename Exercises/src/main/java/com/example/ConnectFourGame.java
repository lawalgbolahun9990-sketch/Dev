package com.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ConnectFourGame extends Application {
    private Color player1 = Color.RED;
    private Color player2 = Color.YELLOW;
    private Color currPlayer = player1;
    private Circle[][] cells = new Circle[6][7];
    private int[][] consequtiveFourArr = new int[4][2];

    public void start(Stage primaryStage) {
        setCells();

        
        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: green;");
        pane.setVgap(5);
        pane.setHgap(5);
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                pane.add(cells[i][j], i, j);
            }
        }

        System.out.println(isWin());
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Connect Four");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setCells() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                Circle c = new Circle(30, Color.WHITE);
                c.setId(i + "," + j);
                c.setOnMouseClicked(e -> {
                    if (!isWin() && !isFill()) {
                        String indexStr = c.getId();
                        int row = Integer.parseInt(indexStr.substring(0, indexStr.indexOf(",")));
                        int col = Integer.parseInt(indexStr.substring(indexStr.indexOf(",") + 1, indexStr.length()));

                        if ((col == cells[row].length - 1 || !cells[row][col + 1].getFill().equals(Color.WHITE)) && c.getFill().equals(Color.WHITE)) {
                            c.setFill(currPlayer);
                            currPlayer = (currPlayer == player1) ? player2 : player1;
                        }
                        if (isWin()) {
                            winAnimation().play();
                        }
                    } 
                });
                cells[i][j] = c;

            }
        }
    }

    public boolean isFill() {
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                if (cells[row][col].getFill().equals(Color.WHITE))
                    return false;
            }
        }
        return true;
    }

    public boolean isWin() {
        // check for vertical and horizontal line
        for (int row = 0; row < cells.length; row++) {
            if (isLine(row, 0, true))
                return true;
        }

        for (int col = 0; col < cells[0].length; col++) {
            if (isLine(0, col, false))
                return true;
        }

        // check for diagonal
        for (int i = cells.length - 1; i >= 0; i--) {
            for (int j = cells[0].length - 1; j >= 0; j--) {
                boolean isMajorDiagonal = isDiagonal(i, j, true);
                boolean isMinorDiagonal = isDiagonal(i, j, false);
                if (isMajorDiagonal || isMinorDiagonal)
                    return isMajorDiagonal || isMinorDiagonal;
            }
        }
        return false;
    }

    public boolean isLine(int row, int col, boolean isHorizontal) {
    int dRow = isHorizontal ? 0 : 1;
    int dCol = isHorizontal ? 1 : 0;

    int occurrence = 0;
    Paint curr = Color.WHITE;
    clearConsequtiveFour();

    while (!isOutOfBounds(row, col)) {
        Paint cell = cells[row][col].getFill();

        if (cell.equals(Color.WHITE)) {
            occurrence = 0;
            curr = Color.WHITE;
            clearConsequtiveFour();
        }
        else if (cell.equals(curr)) {
            consequtiveFourArr[occurrence][0] = row;
            consequtiveFourArr[occurrence][1] = col;
            occurrence++;

            if (occurrence == 4) return true;
        }
        else {
            curr = cell;
            occurrence = 0;
            clearConsequtiveFour();

            consequtiveFourArr[0][0] = row;
            consequtiveFourArr[0][1] = col;
            occurrence = 1;
        }

        row += dRow;
        col += dCol;
    }
    return false;
}

    public boolean isDiagonal(int row, int col, boolean isMajorDiagonal) {
        clearConsequtiveFour();
        int occourence = 0;
        Paint curr = cells[row][col].getFill() ;

        while (!isOutOfBounds(row, col) && cells[row][col].getFill() != Color.WHITE) {
            if (cells[row][col].getFill().equals(curr)) {
                consequtiveFourArr[occourence][0] = row;
                consequtiveFourArr[occourence][1] = col;
                occourence++;

                row--;
                col += isMajorDiagonal ? -1 : 1;
                if (occourence == 4) {
                    System.out.println("iss");
                    return true;
                }
            } else {
                break;
            }
        }
        return false;
    }

    public boolean isOutOfBounds(int row, int col) {
        return row < 0 || col < 0 || row >= cells.length || col >= cells[0].length;
    }

    private void clearConsequtiveFour() {
        for (int i = 0; i < consequtiveFourArr.length; i++) {
            consequtiveFourArr[i][0] = 0;
            consequtiveFourArr[i][1] = 0;
        }
    }

    public Timeline winAnimation() {
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(5), e -> {
            Paint color = currPlayer.equals(player1) ? player2 : player1;

            for (int i = 0; i < consequtiveFourArr.length; i++) {
                Circle c = cells[consequtiveFourArr[i][0]][consequtiveFourArr[i][1]];
                c.setFill(c.getFill().equals(Color.BLACK) ? color : Color.BLACK);
            }
        }));
        animation.setCycleCount(Timeline.INDEFINITE);
        return animation;
    }
}
