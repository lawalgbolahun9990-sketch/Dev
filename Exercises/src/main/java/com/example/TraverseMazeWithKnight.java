package com.example;

import java.util.ArrayList;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TraverseMazeWithKnight extends Application{
    int[][] board = new int[8][8];
    public void start(Stage primaryStage){
        Pane root = new Pane();
        KnightTravesalAnimation ui = new KnightTravesalAnimation(new KnightTraversal(board));
        
        root .getChildren().add(ui);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle(getClass().getName());
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
