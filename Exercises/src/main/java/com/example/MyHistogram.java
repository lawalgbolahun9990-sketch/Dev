package com.example;

import java.util.Arrays;

import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MyHistogram extends HBox {
    public final int OPTIMAL_PANE_HEIGHT = 400;
    private final int FIXED_WIDTH = 10;
    private int[] counts;
    private String[] subTexts = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    private double fixedDimension;

    public MyHistogram(int[] counts){
        setCount(counts);
        setSpacing(1);
        setAlignment(Pos.BOTTOM_CENTER);
        drawHistogram();
    }
    public void drawHistogram(){
        getChildren().clear();
        for (int i = 0; i < counts.length; i++) {
            Rectangle bar = new Rectangle(FIXED_WIDTH,fixedDimension * counts[i]);
            bar.setFill(Color.WHITE);
            bar.setStroke(Color.BLACK);
            Label barLabel = new Label(subTexts[i],bar);
            barLabel.setContentDisplay(ContentDisplay.TOP);
            getChildren().add(barLabel);
        }
    }
    public int[] getCounts(){
        return counts;
    }
    public void setCount(int[] counts){
        this.counts = counts;
        fixedDimension = getOptimalDimension();
        drawHistogram();
    }
    public String[] getSubTexts(){
        return subTexts;
    }
    public void setSubTexts(String[] subText){
        this.subTexts = subText;
    }
    public double getOptimalDimension(){
        double dimension = 10;
        double scale = 0.1;
        int max = counts[0];
        for (int i = 1; i < counts.length; i++) {
            max = max > counts[i] ? max : counts[i];
        }
        System.out.println(max);
        while (max * dimension >= OPTIMAL_PANE_HEIGHT) {
            if (dimension - scale == 0) {
                scale *= 0.1;
            }
            dimension -= scale;
        }
        return dimension;
    }
}

