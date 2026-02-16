package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class EightQueensProblem extends Application {
    private final int Q = 1;
    public int numQueens = 8;
    private int[][] board = new int[8][8];

    public void start(Stage primaryStage) {
        VisualGridPane boardPane = new VisualGridPane(50, 4);

        placeQueens();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == Q) {
                    int spacing = boardPane.getLineSpacing();
                    ImageView queenView = new ImageView("queen.jpg");
                    queenView.setFitHeight(spacing);
                    queenView.setFitWidth(spacing);
                    queenView.setX(j * spacing);
                    queenView.setY(i * spacing);
                    boardPane.getChildren().add(queenView);
                }
            }
        }

        Scene scene = new Scene(boardPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle(getClass().getName());
        primaryStage.show();
    }

    public void placeQueens() {
        board[0][0] = Q;
        placeQueens(1, 0, 1);
    }

    public boolean placeQueens(int r, int c, int qCount) {
        if (qCount == numQueens)
            return true;

        for (int i = 0; i < board.length; i++) {
            if (isValidPos(r, c + i)) {
                board[r][c + i] = Q;

                if (placeQueens(r + 1, c, qCount + 1))
                    return true;
                else
                    board[r][c + i] = 0;
            }
        }
        return false;
    }

    public boolean isValidPos(int r, int c) {
        // check row and col
        for (int i = 0; i < board.length; i++) {
            if (board[r][i] == Q)
                return false;
            if (board[i][c] == Q)
                return false;
        }

        // check diagonals
        for (int i = 0; i < board.length; i++) {
            // major
            if ((r + i <= board.length - 1 && c + i <= board.length - 1) && board[r + i][c + i] == Q)
                return false;
            if ((r - i >= 0 && c - i >= 0) && board[r - i][c - i] == Q)
                return false;
            // minor
            if ((r + i <= board.length - 1 && c - i >= 0) && board[r + i][c - i] == Q)
                return false;
            if ((r - i >= 0 && c + i <= board.length - 1) && board[r - i][c + i] == Q)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
