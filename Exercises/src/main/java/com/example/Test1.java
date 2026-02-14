package com.example;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;

import javafx.collections.ObservableList;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

public class Test1 extends Application{
    public void start(Stage primaryStage){

        double[] p = {500,500,700,500};
        rotatePoints(p, 90);

        Polyline line = new Polyline();

        line.getPoints().addAll(Arrays.asList(p[0],p[1],p[2],p[3]));

        Pane pane = new Pane();
        pane.getChildren().add(line);

        Scene scene = new Scene(pane,500,500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("User Input GUI");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    public void rotatePoints(double[] points, double degree){
        double vX = points[2] - points[0];
        double vY = points[3] - points[1];

        double distance = Math.abs(vX - vY);

        
        System.out.println(Math.cos(100));
    }
}
