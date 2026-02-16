package com.example;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MazePath extends Application {
    private final int BLOCKED = 1;
    private final int DIMENSION = 8;
    private boolean pathFound = false;
    private int[][] maze = new int[DIMENSION][DIMENSION];
    private boolean[][] visited = new boolean[DIMENSION][DIMENSION];
    private ArrayList<int[]> path = new ArrayList<>();

    public void start(Stage primaryStage) {
        VBox root = new VBox();
        VisualGridPane gridPane = new VisualGridPane(400);
        gridPane.setLineSpacing(50);
        gridPane.setLineOpacity(3);

        setBlockPathUI(gridPane);
        Label status = new Label();

        HBox buttonBox = getButtons(gridPane, status);

        root.getChildren().addAll(new StackPane(status), gridPane, buttonBox);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle(getClass().getName());
        primaryStage.show();
        ;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public boolean setPath(int startRow, int startCol) {
        if (!isValidCell(startRow, startCol))
            return false;
        if (visited[startRow][startCol])
            return false;

        visited[startRow][startCol] = true;

        if (startRow == DIMENSION - 1 && startCol == DIMENSION - 1) {
            int[] dimension = { startRow, startCol };
            path.add(dimension);
            return true;
        }

        int[] dr = { 0, 1, 0, -1 };
        int[] dc = { 1, 0, -1, 0 };
        
        for (int i = 0; i < 4; i++) {
            if (setPath(startRow + dr[i], startCol + dc[i])) {
                int[] dimension = { startRow, startCol };
                path.add(dimension);
                return true;
            }
           
        }
        //visited[startRow][startCol] = false;
        return false;
    }

    public boolean isValidCell(int row, int col) {
        if (row < 0 || row > DIMENSION - 1 || col < 0 || col > DIMENSION - 1)
            return false;
        if (maze[row][col] == BLOCKED)
            return false;

        return true;

    }

    public void clearVisited() {
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited.length; j++) {
                visited[i][j] = false;
            }
        }
    }

    public void updatePathUI(VisualGridPane gridPane) {
        gridPane.getChildren()
                .removeIf(node -> node instanceof Rectangle && ((Rectangle) node).getFill().equals(Color.RED));

        for (int i = 0; i < path.size(); i++) {
            int row = path.get(i)[0] * gridPane.getLineSpacing();
            int col = path.get(i)[1] * gridPane.getLineSpacing();

            Rectangle rectPath = new Rectangle(col, row, gridPane.getLineSpacing(), gridPane.getLineSpacing());
            rectPath.setFill(Color.RED);
            rectPath.setStroke(Color.BLACK);
            rectPath.setStrokeWidth(1.5);

            gridPane.getChildren().add(rectPath);
        }

    }

    public void setBlockPathUI(VisualGridPane gridPane) {
        gridPane.setOnMouseClicked(e -> {
            int lineSpacing = gridPane.getLineSpacing();
            int row = (int) e.getY() / lineSpacing;
            int col = (int) e.getX() / lineSpacing;

            if (maze[row][col] != BLOCKED) {
                int lineRow = row * lineSpacing;
                int lineCol = col * lineSpacing;
                Line crossLineLeft = new Line(lineCol + lineSpacing, lineRow, lineCol, lineRow + lineSpacing);
                Line crossLineRight = new Line(lineCol + lineSpacing, lineRow + lineSpacing, lineCol, lineRow);

                crossLineLeft.setId("Blocked");
                crossLineRight.setId("Blocked");

                gridPane.getChildren().addAll(crossLineLeft, crossLineRight);
                maze[row][col] = BLOCKED;

                return;
            }

            gridPane.getChildren().removeIf(node -> node instanceof Line
                    && (((Line) node).getId() != null)

                    && ((Line) node).getId().equals("Blocked")

                    && (((Line) node).getStartX() == col * lineSpacing + lineSpacing

                            && ((Line) node).getEndX() == col * lineSpacing)

                    && (((((Line) node).getStartY() == row * lineSpacing)
                            && ((Line) node).getEndY() == row * lineSpacing + lineSpacing)

                            || ((((Line) node).getStartY() == row * lineSpacing + lineSpacing)
                                    && ((Line) node).getEndY() == row * lineSpacing)));

            maze[row][col] = 0;
        });
    }

    public HBox getButtons(VisualGridPane gridPane, Label status) {
        Button findPathButton = new Button("Find Path");
        findPathButton.setOnAction(e -> {
            clearPath();
            pathFound = setPath(0, 0);
            setFoundStatus(status);
            updatePathUI(gridPane);
        });

        Button clearPathButton = new Button("Clear Path");
        clearPathButton.setOnAction(e -> {
            clearPath();
            updatePathUI(gridPane);
        });
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);
        buttonBox.getChildren().addAll(findPathButton, clearPathButton);

        return buttonBox;
    }

    public void setFoundStatus(Label status) {
        String message = "Path " + (pathFound ? "Found" : "Not Found");
        status.setText(message);
    }

    public void clearPath() {
        clearVisited();
        path.clear();
    }
}