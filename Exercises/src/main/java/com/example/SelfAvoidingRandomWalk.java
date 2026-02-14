package com.example;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SelfAvoidingRandomWalk extends Application {
    VisualGridPane gridPane = new VisualGridPane(15, 3);
    private Timeline animation;
    public void start(Stage primaryStage) {
        VBox pane = new VBox();
        ArrayList<int[]> pointsPath = new ArrayList<>();
        gridPane.setLineOpacity(0.5);
        gridPane.updateGrid();

        Button startButton = new Button("Start");
        startButton.setOnAction(e -> {
            animateRandomWalkOnGrid(gridPane, pointsPath);
        });

        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(e -> {
            gridPane = new VisualGridPane(15, 3);
            pane.getChildren().removeFirst();
            pane.getChildren().addFirst(gridPane);
            pointsPath.clear();;
        });
        
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(startButton,refreshButton);
        buttonBox.setAlignment(Pos.CENTER);

        pane.getChildren().addAll(gridPane,buttonBox);
        pane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("User Input GUI");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public int[] generateRandomPoints(ArrayList<int[]> pointPath, int[] prevPoint, double dimension, int lineSpacing) {
        int[] newPoint = new int[2];
        int randomX = prevPoint[0];
        int randomY = prevPoint[1];
        newPoint[0] = randomX;
        newPoint[1] = randomY;

        //since there is a likelyhood of  slantline if both x znd y are updated simuteniously we only need random x or y at a time

        while (isPointExistent(pointPath, newPoint)) {
            newPoint[0] = prevPoint[0];
            newPoint[1] = prevPoint[1];

            boolean isXorY = new Random().nextBoolean();
            boolean direction = new Random().nextBoolean();

            if (isXorY) {
                randomX = (direction) ? prevPoint[0] - lineSpacing : prevPoint[0] + lineSpacing;
                newPoint[0] = randomX;
            }
            else{
                randomY = (direction) ? prevPoint[1] - lineSpacing : prevPoint[1] + lineSpacing;
                newPoint[1] = randomY;
            }
        }
        return newPoint;
    }

    public int[] getPaneCenterPoint(Pane pane) {
        int[] paneCenterPoint = new int[2];
        paneCenterPoint[0] = (int) (pane.getWidth() / 2);
        paneCenterPoint[1] = (int) (pane.getHeight() / 2);
        return paneCenterPoint;
    }

    public void animateRandomWalkOnGrid(VisualGridPane drawOnPane, ArrayList<int[]> pointsPath) {

        if (pointsPath.isEmpty())
            pointsPath.add(getPaneCenterPoint(drawOnPane));

        animation = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            int[] prevPoints = pointsPath.getLast();
            if (isOutOfBounds(pointsPath, prevPoints, gridPane.getLineSpacing())){
                System.out.println("Program Ended");
                animation.stop();
                return;
            }

            int[] currPoints = generateRandomPoints(pointsPath,prevPoints, drawOnPane.getDimension(), drawOnPane.getLineSpacing());
            
            Line line = new Line(prevPoints[0],prevPoints[1],currPoints[0],currPoints[1]);
            line.setStrokeWidth(2);
            drawOnPane.getChildren().add(line);
            pointsPath.add(currPoints);

            
        }));
        animation.setCycleCount(1000);
        animation.play();
    }
    private boolean isPointExistent(ArrayList<int[]> pointPath, int[] currPoint){
        for (int[] point : pointPath) {
            if (point[0] == currPoint[0] && point[1] == currPoint[1]) return true;
        }
        return false;
    }
    private boolean isOutOfBounds(ArrayList<int[]> pointPath, int[] currPoint, int lineSpacing){
        boolean isOutOfBounds = true;
        int[][] possiblePaths = new int[4][2];

        possiblePaths[0][0] = currPoint[0] - lineSpacing;
        possiblePaths[0][1] = currPoint[1];

        possiblePaths[1][0] = currPoint[0] + lineSpacing;
        possiblePaths[1][1] = currPoint[1];

        possiblePaths[2][0] = currPoint[0];
        possiblePaths[2][1] = currPoint[1] - lineSpacing;

        possiblePaths[3][0] = currPoint[0];
        possiblePaths[3][1] = currPoint[1] + lineSpacing;

        for (int i = 0; i < possiblePaths.length; i++) {
            if(!isPointExistent(pointPath, possiblePaths[i])){
                isOutOfBounds = false;
                break;
            };
        }
        if (currPoint[0] <= 0 || currPoint[1] <= 0 || currPoint[0] > gridPane.getDimension() || currPoint[1] > gridPane.getDimension()) {
            isOutOfBounds = true;
        }
        
        return isOutOfBounds;
    }
}
