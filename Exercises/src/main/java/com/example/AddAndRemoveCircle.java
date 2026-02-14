package com.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AddAndRemoveCircle extends Application {
    private Pane rootPane = new Pane();
    private Timeline animation;
    private int effectCount = 1;
    private final int POINT_SIZE = 10;
    private int indexOfRemovedPoint = 0;

    @Override
    public void start(Stage primaryStage) {
        setupMouseListener(rootPane);
        var scene = new Scene(rootPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("User Input GUI");
        primaryStage.show();
    }

    public void setupMouseListener(Pane pane) {
        pane.setOnMouseClicked(e -> {
            MyCircle pointAsCircle = new MyCircle(10);
            if (!e.getButton().equals(MouseButton.PRIMARY)) return;

            double mouseX = e.getX(), mouseY = e.getY();
            ObservableList<Node> pointCircles = pane.getChildren();

            pointAsCircle.setCenterX(mouseX);
            pointAsCircle.setCenterY(mouseY);
            Pane effectsPane = new Pane();
            

            if (isMouseinCircle(e.getX(), e.getY(), pointCircles)){
                pointCircles.remove(indexOfRemovedPoint);
                return;
            } else {
                afterEffectsAnimation(effectsPane, pointAsCircle);
            }
            
            pane.getChildren().addAll(pointAsCircle,effectsPane);
        });
    }

    public boolean isMouseinCircle(double mouseX, double mouseY, ObservableList<Node> pointCircles) {
        for (int i = 0; i < pointCircles.size(); i++) {
            if (pointCircles.get(i).contains(mouseX, mouseY)) {
                indexOfRemovedPoint = i;
                return true;
            }
        }
        return false;
    }
    public void afterEffectsAnimation(Pane p, Circle c){
        animation = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            if(effectCount == 10){
                rootPane.getChildren().removeLast();
                effectCount = 0;
                animation.stop();
            }
            MyCircle effectCircle = new MyCircle(c.getRadius() + increaseEffeCtCount());
            effectCircle.setCenterX(c.getCenterX());
            effectCircle.setCenterY(c.getCenterY());
            effectCircle.setStroke(Color.color(Math.random(), Math.random(), Math.random()));
            p.getChildren().add(effectCircle);
        }));
        animation.setCycleCount(10);
        animation.play();
    }
    public int increaseEffeCtCount(){
        return effectCount++;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
