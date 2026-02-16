package com.example;



import java.lang.reflect.Array;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RunningFan extends Application{
    private boolean clockwise = true;
    public void start(Stage primaryStage){
        Pane fanPane = new Pane();
        Circle fanBody = new Circle(100);
        fanBody.setFill(Color.WHITE);
        fanBody.setStroke(Color.BLACK);

        Arc[] fanBlades = new Arc[6];
        setBlades(fanBlades,6,fanBody.getRadius(),30);
        
        
        fanPane.getChildren().add(fanBody);
        for (Arc blade : fanBlades) {
            fanPane.getChildren().add(blade);
        }

        fanPane.widthProperty().addListener(ov -> {
            double centerX = fanPane.getWidth() / 2;
            fanBody.setCenterX(centerX);
            setBladeCoordinates(fanBlades, centerX, fanBody.getCenterY());
        });
        fanPane.heightProperty().addListener(ov -> {
            double centerY = fanPane.getHeight() / 2;
            fanBody.setCenterY(centerY);
            setBladeCoordinates(fanBlades, fanBody.getCenterX(), centerY);
        });
        
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            moveBlades(fanBlades, 1, clockwise);
        }));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
        
        //UI Controls
        HBox buttonBox = new HBox();
        Button[] buttons = new Button[3];
        buttons[0] = new Button("Pause");
        buttons[1] = new Button("Resume");
        buttons[2] = new Button("Reverse");

        buttons[0].setOnAction(e -> animation.pause());
        buttons[1].setOnAction(e ->  animation.play());
        buttons[2].setOnAction(e -> clockwise = !clockwise);

        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);
        buttonBox.getChildren().addAll(buttons);

        Slider speedSlider = new Slider();
        speedSlider.setMin(0.1);
        speedSlider.setMax(50);
        speedSlider.setValue(1);
        speedSlider.setMaxSize(400, 40);
    
        speedSlider.valueProperty().addListener(ov -> {
            animation.setRate(speedSlider.getValue());
        });

        BorderPane pane = new BorderPane();
        pane.setCenter(fanPane);
        pane.setTop(buttonBox);
        pane.setBottom(speedSlider);
        
        Scene scene = new Scene(pane, 250,250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("User Input GUI");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    public void moveBlades(Arc[] fanBlades, double spacing, boolean clockwise){
        for (Arc blade : fanBlades) {
            blade.setStartAngle(blade.getStartAngle() + (clockwise ? spacing : -spacing));
        }
    }
    private void setBlades(Arc[] blades, int numBlades,double bodyRadius, double bladeWidth){
        int angle = 0;
        double radius = bodyRadius - bodyRadius * 0.1;
        for (int i = 0; i < numBlades; i++) {
            blades[i] = new Arc(0,0,radius,radius,angle,bladeWidth);
            blades[i].setType(ArcType.ROUND);
            blades[i].setFill(Color.BURLYWOOD);
            blades[i].setStrokeWidth(2);
            blades[i].setStroke(Color.BLACK);
            angle += 90;
        }
    }
    private void setBladeCoordinates(Arc[] blades,double centerX, double centerY){
        for (Arc blade: blades) {
            blade.setCenterX(centerX);
            blade.setCenterY(centerY);
        }
    }
}
