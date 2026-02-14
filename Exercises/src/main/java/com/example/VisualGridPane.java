package com.example;


import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class VisualGridPane extends Pane{
    private final int minGridDimension = 100;
    private int dimension = minGridDimension;
    private int lineSpacing = 10;
    private double lineOpacity = 0.5;
    private int gridSize = 1;

    VisualGridPane(){
        setPaneDimension(dimension);
        drawVisualGrid(minGridDimension, lineSpacing);
    }
    VisualGridPane(int dimension){
        this.dimension = dimension;
        setPaneDimension(dimension);
        drawVisualGrid(dimension, lineSpacing);
    }
    public VisualGridPane(int lineSpacing, int size){
        this.gridSize = size;
        dimension *= size;
        setPaneDimension(dimension);
        
        this.lineSpacing = lineSpacing;
        drawVisualGrid(dimension, lineSpacing);
    }
    VisualGridPane(int dimension, int size, int lineSpacing){
        this.dimension = dimension;
        this.gridSize = size;
        this.lineSpacing = lineSpacing;
        setPaneDimension(dimension);
        drawVisualGrid(dimension, lineSpacing);
    }
    public int getLineSpacing(){
        return lineSpacing;
    }
    public void setLineSpacing(int lineSpacing){
        this.lineSpacing = lineSpacing;
        drawVisualGrid(dimension, lineSpacing);
    }
    public int getSize(){
        return gridSize;
    }
    public void setSize(int size){
        //dimesion is updated here because size determines dimension
        if (size > 8) return;
        gridSize = size;
        dimension *= gridSize;
        setPaneDimension(dimension);
    }
    public int getDimension(){
        return dimension;
    }
    private void drawVisualGrid(int dimension, int lineSpacing){
        getChildren().clear();
        int startPoint = 0;
        int endPoint = dimension;
        while (startPoint <= endPoint) {
            
            Line horizontalLine = new Line(0,startPoint,endPoint,startPoint);
            //horizontalLine.setOpacity(lineOpacity);
            horizontalLine.setStrokeWidth(lineOpacity);

            Line verticalLine = new Line(startPoint,0,startPoint,endPoint);
            //verticalLine.setOpacity(lineOpacity);
            verticalLine.setStrokeWidth(lineOpacity);

            getChildren().addAll(horizontalLine,verticalLine);
            
            startPoint += lineSpacing;
        }

    }
    public void updateGrid(){
        drawVisualGrid(dimension, lineSpacing);
    }
    public void updateGrid(int dimension, int lineSpacing){
        drawVisualGrid(dimension, lineSpacing);
    }
    public void setPaneDimension(int dimension){
        setHeight(dimension);
        setWidth(dimension);
    }
    public double getLineOpacity(){
        return lineOpacity;
    }
    public void setLineOpacity(double lineOpacity){
        if(lineOpacity > 1 || lineOpacity < 0) return;
        this.lineOpacity = lineOpacity;
    }

}
