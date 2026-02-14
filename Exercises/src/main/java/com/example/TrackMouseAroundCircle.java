package com.example;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TrackMouseAroundCircle extends Application {
    private Pane pane = new Pane();
    private Circle circle = getCircle(200);
    @Override
    public void start(Stage primaryStage) {
        
        trackMouse();

        Scene scene = new Scene(pane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("User Input GUI");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    public void trackMouse(){
        createUI();
        setupMouseListener();
    }
    private boolean isMouseinCircle(double mouseX, double mouseY) {
        return circle.contains(mouseX,mouseY);
    }
    private Text getMousePositionFeedbackText(boolean isMouseinCircle){
        String insideOrOutside = (isMouseinCircle ? "Inside" : "Outside");
        Text text = getText();
        text.setText("Mouse is " + insideOrOutside + " The Circle");
        return text;
    }
    public void createUI(){
        pane.getChildren().add(circle);
        pane.getChildren().add(getText());
    }
    private void AddTextToPane(Text text){
        pane.getChildren().removeLast();
        pane.getChildren().add(text);
    }
    private void setupMouseListener(){
        pane.setOnMouseMoved(e -> {
            Text text = getText();
             text = updateText(text, e.getX(), e.getY());
            AddTextToPane(text);
        });
    }
    private Text updateText(Text text, double mouseX, double mouseY){
        text = getMousePositionFeedbackText(isMouseinCircle(mouseX, mouseY));
        setTextPositionOnMouse(text, mouseX, mouseY);
        return text;
    }
     private Text getText(){
        Text text = new Text();
        text.setFont(new Font("Times New Roman",15));
        return text;
    }
    private Text setTextPositionOnMouse(Text text, double mouseX, double mouseY){
        text.setX(mouseX);
        text.setY(mouseY);
        return text;
    }
    private Circle getCircle(int radius){
        MyCircle c = new MyCircle(radius);
        c.centerXProperty().bind(pane.widthProperty().divide(2));
        c.centerYProperty().bind(pane.heightProperty().divide(2));
        c.setStrokeWidth(5);
        c.setFill(Color.ANTIQUEWHITE);
        return c;
    }
}
