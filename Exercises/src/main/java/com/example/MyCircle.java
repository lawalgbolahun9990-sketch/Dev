package com.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MyCircle extends Circle{
    MyCircle(){
        setFill(Color.WHITE);
        setStroke(Color.BLACK);
    }
    MyCircle(double radius){
        this();
        setRadius(radius);
    }
    MyCircle(double centerX, double centerY){
        this();
        setCenterX(centerX);
        setCenterY(centerY);
    }
    MyCircle(double centerX, double centerY, double radius){
        this(centerX, centerY);
        setRadius(radius);
    }
}
