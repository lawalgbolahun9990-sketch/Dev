package com.example;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MazePath1 extends Application{
    private final int BLOCKED = 1;
    private final int DIMENSION = 8;
    private int[][] maze = new int[DIMENSION][DIMENSION];
    private int[][] blockedCells = new int[DIMENSION * DIMENSION][2];
    Stack<int[]> validPathStack = new Stack<>();
    
    public void start(Stage primaryStage) {
        blockCell(1,0,1);
        //blockCell(1,1,1);

        VisualGridPane gridPane = new VisualGridPane(400);
        gridPane.setLineSpacing(50);

        findValidPath(0, 0);

        for (int[] cell : validPathStack) {
            int row = cell[0];
            int col = cell[1];

            Rectangle pathRect = new Rectangle(col * gridPane.getLineSpacing(), row * gridPane.getLineSpacing(), gridPane.getLineSpacing(), gridPane.getLineSpacing());
            pathRect.setFill(Color.RED);
            pathRect.setStroke(Color.BLACK);
            gridPane.getChildren().add(pathRect);
        }
        
        Scene scene = new Scene(new Pane(gridPane), 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Maze Path Finder");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
    public boolean findValidPath(int startRow, int startCol){
        if (maze[startRow][startCol] == BLOCKED) return false;
        if (maze[DIMENSION-1][DIMENSION-1] == BLOCKED) return false;

        if (startRow == DIMENSION -1 && startCol == DIMENSION -1) {
            int[] dimension = {startRow, startCol};
            validPathStack.add(dimension);
            return true;
        }

        if (startRow != 7 && findValidPath(startRow +1, startCol)) {
            int[] dimension = {startRow, startCol};
            validPathStack.add(dimension);
            return true;
        } else if (startCol != 7 && findValidPath(startRow, startCol +1)) {
            int[] dimension = {startRow, startCol};
            validPathStack.add(dimension);
            return true;
        } else{
            return false;
        }
    }
    public void clearPath(int[][] maze, int blockedCount) {
        for (int i = 0; i < blockedCount; i++) {
            int row = blockedCells[i][0];
            int col = blockedCells[i][1];

            maze[row][col] = 0;
        }
    }

    public boolean isValidCell(int row, int col) {
        int cellStatus = maze[row][col];
        if(cellStatus == BLOCKED || formsSquare(row, col)) return false;

        boolean hasValidVerticalNeighbours = maze[row +1][col] != BLOCKED && !formsSquare(row +1, col);
        boolean hasValidHorizontalNeighbours = maze[row][col +1] != BLOCKED && !formsSquare(row, col +1);

        return hasValidHorizontalNeighbours || hasValidVerticalNeighbours;
    }

    public boolean formsSquare(int row, int col) {
        if (row == 0 || col == 0) return false;

        if(maze[row -1][col -1] == BLOCKED) return false; //Top Left
        if(maze[row -1][col] == BLOCKED) return false;//Top Center
        if(maze[row -1][col +1] == BLOCKED) return false;//Top Right
        if(maze[row][col -1] == BLOCKED) return false;//Center Left
        if(maze[row][col +1] == BLOCKED) return false;//Center Right
        if(maze[row +1][col -1] == BLOCKED) return false;//Bottom Left
        if(maze[row +1][col] == BLOCKED) return false;//Bottom Center
        if(maze[row +1][col +1] == BLOCKED) return false;//Bottom Right
        
        return true;
    }

    public int blockCell(int row, int col, int blockedCount) {
        maze[row][col] = BLOCKED;
        blockedCells[blockedCount][0] = row;
        blockedCells[blockedCount][1] = col;

        return blockedCount + 1;
    }
}
