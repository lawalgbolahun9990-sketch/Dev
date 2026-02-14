package com.example;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class TrafficLight extends Application{
    private final int RED = 0;
    private final int YELLOW = 1;
    private final int GREEN = 2;
    private final int LIGHT_OFF = -1;
    private int status = LIGHT_OFF;

    public void start(Stage primaryStage){
        MyCircle[] lights = new MyCircle[3];
        int lightSize = 30;

        lights[RED] = new MyCircle(lightSize);
        lights[YELLOW] = new MyCircle(lightSize);
        lights[GREEN] = new MyCircle(lightSize);


        VBox lightPane = new VBox();
        lightPane.getChildren().addAll(lights);
        lightPane.setAlignment(Pos.TOP_CENTER);
        lightPane.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;");
        lightPane.setSpacing(10);


        ToggleGroup group = new ToggleGroup();
        HBox buttons = new HBox();
        RadioButton redButton = new RadioButton("Red");
        redButton.setOnAction(e -> {
            
            turnLightOn(lightPane,lights, RED);
        });
        RadioButton yellowButton = new RadioButton("Yellow");
        yellowButton.setOnAction(e -> {
            turnLightOn(lightPane,lights, YELLOW);
        });
        RadioButton greenButton = new RadioButton("Green");
        greenButton.setOnAction(e -> {
            
            turnLightOn(lightPane,lights, GREEN);
        });
        buttons.getChildren().addAll(redButton, yellowButton, greenButton);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(20);
        group.getToggles().addAll(redButton, yellowButton, greenButton);

        BorderPane p = new BorderPane();
        p.setCenter(new Group(lightPane));
        p.setBottom(buttons);
        Scene scene = new Scene(p);
        primaryStage.setScene(scene);
        primaryStage.setTitle("User Input GUI");
        primaryStage.show();
    }
    public static void main (String[] args){
        launch(args);
    }
    private void turnLightOn(Pane lightPane ,Circle[] lights, int color){
        if (status != LIGHT_OFF) {
            lights[status].setFill(Color.TRANSPARENT);
        }
        switch (color) {
            case RED:
                lights[RED].setFill(Color.RED);
                status = RED;
                break;
            case YELLOW:
                lights[YELLOW].setFill(Color.YELLOW);
                status = YELLOW;
                break;
            case GREEN:
                lights[GREEN].setFill(Color.GREEN);
                status = GREEN;
                break;
            default:
                break;
        }
    }
}
