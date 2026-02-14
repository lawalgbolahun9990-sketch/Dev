package com.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javafx.geometry.Point2D;

public class KochSnowflake {
    private final int DIMENSION = 100;
    private int size = 1;
    private int x = 200;
    private int y = 200;
    ArrayList<Point2D> points = new ArrayList<>();

    public ArrayList<double[]> getFractal(int order){
        ArrayList<double[]> points = new ArrayList<>();
        if (order == 0) {
            //base traingle
            return getTriangle(x, y);
        }

        ArrayList<double[]> childPoints = getFractal(order -1);

        //converts a line from the lines in a previous fractal into new connected lines that forms an new equilateral triangle with no base
        for (int i = 0; i < childPoints.size()-1; i++) {

            ArrayList<Point2D> newLines = mutateLine(childPoints.get(i), childPoints.get(i + 1));
            for (Point2D point : newLines) {
                double[] dimension = {point.getX(),point.getY()};
                points.add(dimension);
            }
        }

        return points;
    }

    public ArrayList<Point2D> mutateLine(double[] startPoint, double[] endPoint) {
        ArrayList<Point2D> newPoints = new ArrayList<>();

        Point2D p1 = new Point2D(startPoint[0], startPoint[1]);
        Point2D p2 = new Point2D(endPoint[0], endPoint[1]);

        //These represent the divide of the full line ints smaller lines that move in diff directions to create the bend

        double fractalDistanceX = (p1.getX() - p2.getX()) * (1.0 / 3);
        double fractalDistanceY = (p1.getY() - p2.getY()) * (1.0 / 3);// divide the line into three

        Point2D firstThird = new Point2D(p1.getX() - fractalDistanceX, p1.getY() - fractalDistanceY);//first line
        Point2D secondThird = new Point2D(p1.getX() -  fractalDistanceX * 2,p1.getY() - fractalDistanceY * 2);//last line

        Point2D midpPoint = p1.midpoint(p2);

        //This creates the sharp corner that the line would make towards the left in the middle
        
        Point2D extendMidPionToLeft = new Point2D(midpPoint.getX() + fractalDistanceY, midpPoint.getY() - fractalDistanceX);// bend

        //Add all 
        
        //Start line
        newPoints.add(p1);

        //First bend
        newPoints.add(firstThird);;

        //Second bend
        newPoints.add(extendMidPionToLeft);;

        //Last line
        newPoints.add(secondThird);
        newPoints.add(p2);

        

        return newPoints;
    }
    public ArrayList<double[]> getTriangle(int startX, int startY) {
        ArrayList<double[]> points = new ArrayList<>();

        double[] p1 = {startX,startY};
        double[] p2 = {startX + DIMENSION * 2 ,startY};
        double[] p3 = {startX+ DIMENSION, startY - (DIMENSION * Math.sqrt(3))};
        double[] p4 = {startX,startY};

        points.add(p1);
        points.add(p2);
        points.add(p3);
        points.add(p4);

        return points;
    }
    public int getX(){
        return x;
    }
    public void setX(int x){
        this.x = x;
    }
    public int getY(){
        return y;
    }
    public void setY(int y){
        this.y = y;
    }
}