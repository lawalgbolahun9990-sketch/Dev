// Uses a Graphical interface to display an hilbert curve with an order specified by the user
package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

public class HilbertCurve extends Application {
    final int SIZE = 300;
    final int UP = 0;
    final int DOWN = 1;
    final int LEFT = 2;
    final int RIGHT = 3;

    ArrayList<int[]> points = getHilbertCurve(4);

    // Create a JavaFx UI that displays the hilbert curve
    // Prompts user for an order and refreshes when user changes the order
    public void start(Stage primaryStage) {
        final int MAX_ORDER = 11;
        // Draw Hilbert Curve
        BorderPane root = new BorderPane();

        Polyline HilbertCurve = new Polyline();
        ObservableList<Double> linePoints = HilbertCurve.getPoints();

        for (int[] point : points) {

            linePoints.add((double) point[0]);
            linePoints.add((double) point[1]);
        }

        // Promp user for order
        Label propmtText = new Label("Enter an order: ");
        TextField promptField = new TextField();
        promptField.setPrefWidth(70);
        promptField.setOnAction(event -> {
            String text = promptField.getText();
            try {
                // Draw new curve when user enters a new order
                HilbertCurve.getPoints().clear();
                int order = Integer.parseInt(text);
                // 11 is the maximum order JavaFx can allow before it crashes
                if (order > MAX_ORDER)
                    order = MAX_ORDER;
                points = getHilbertCurve(order);
                for (int[] point : points) {
                    linePoints.add((double) point[0]);
                    linePoints.add((double) point[1]);
                }
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid Input: Enter An Integer");
            }
        });

        HBox prompt = new HBox();
        prompt.setAlignment(Pos.CENTER);
        prompt.getChildren().addAll(propmtText, promptField);

        root.setCenter(HilbertCurve);
        root.setBottom(prompt);

        Scene scene = new Scene(root, 320, 320);
        primaryStage.setScene(scene);
        primaryStage.setTitle(getClass().getName());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public ArrayList<int[]> getHilbertCurve(int order) {
        return getHilbertCurve(new int[] { SIZE / 2, SIZE / 2 }, SIZE / 2, order, DOWN);
    }

    private ArrayList<int[]> getHilbertCurve(int[] gridMidpoint, int gridSize, int order, int orientation) {
        if (order <= 1) {
            return getBasePoints(gridMidpoint, gridSize, orientation);
        }
        // determine the oqientation of each quadrant
        int[] qOrientation = getQuadrantOrientation(orientation);

        // depresents the idstance between the grid mid point and the midpoint of each
        // quadrant
        int dist = gridSize / 2;
        // Get the coordinate of each quadrant and recursively call untill order = 0 and
        // base point is visited

        int[] fQ = { gridMidpoint[0] + (dist * getQuadrant(1)[0]), gridMidpoint[1] + (dist * getQuadrant(1)[1]) };
        ArrayList<int[]> firstQuadrant = getHilbertCurve(fQ, gridSize / 2, order - 1, qOrientation[0]);

        int[] sQ = { gridMidpoint[0] + (dist * getQuadrant(2)[0]), gridMidpoint[1] + (dist * getQuadrant(2)[1]) };
        ArrayList<int[]> secondQuadrant = getHilbertCurve(sQ, gridSize / 2, order - 1, qOrientation[1]);

        int[] tQ = { gridMidpoint[0] + (dist * getQuadrant(3)[0]), gridMidpoint[1] + (dist * getQuadrant(3)[1]) };
        ArrayList<int[]> thirdQuadrant = getHilbertCurve(tQ, gridSize / 2, order - 1, qOrientation[2]);

        int[] fourthQ = { gridMidpoint[0] + (dist * getQuadrant(4)[0]), gridMidpoint[1] + (dist * getQuadrant(4)[1]) };
        ArrayList<int[]> fourthQuadrant = getHilbertCurve(fourthQ, gridSize / 2, order - 1, qOrientation[3]);

        ArrayList<ArrayList<int[]>> quadrants = new ArrayList<>();
        // determine the order in which the point are merged based on the orieentaton of
        // the grid
        switch (orientation) {
            case UP:
                quadrants.addAll(Arrays.asList(fourthQuadrant, thirdQuadrant, firstQuadrant, secondQuadrant));
                break;
            case DOWN:
                quadrants.addAll(Arrays.asList(firstQuadrant, secondQuadrant, fourthQuadrant, thirdQuadrant));
                break;
            case LEFT:
                quadrants.addAll(Arrays.asList(firstQuadrant, thirdQuadrant, fourthQuadrant, secondQuadrant));
                break;
            case RIGHT:
                quadrants.addAll(Arrays.asList(fourthQuadrant, secondQuadrant, firstQuadrant, thirdQuadrant));
                break;
            default:
                break;
        }

        return mergePoints(quadrants);

    }

    // Visits all the quardrants(squares) in a (2 * 2) grid based on
    // an orientation that determine the order which each quadrant is visited
    private ArrayList<int[]> getBasePoints(int[] gridMidpoint, int gridSize, int orientation) {
        /*
         * this method divides a grid into 4 quadrants and returns a list of points
         * that repesent the order and orientation of visiting midpoint of all 4 smaller
         * quadrants
         */
        ArrayList<int[]> points = new ArrayList<>();
        int[][] connectMidpoints = connectMidpoints(orientation);

        // distance from the midpoint of the grid to the midpoint of the quadrants
        int dist = gridSize / 2;

        // determine starting point based on the given orientation
        if (orientation == UP || orientation == RIGHT)
            points.add(new int[] { gridMidpoint[0] + dist, gridMidpoint[1] + dist });// start at fourth quadrant
        else
            points.add(new int[] { gridMidpoint[0] - dist, gridMidpoint[1] - dist });// start at the first quadrant

        for (int i = 0; i < connectMidpoints.length; i++) {

            // visit other quadrants in an order that is determined by the specified
            // orientation
            int x = gridMidpoint[0] + (dist * connectMidpoints[i][0]);
            int y = gridMidpoint[1] + (dist * connectMidpoints[i][1]);

            points.add(new int[] { x, y });
        }

        return points;
    }

    // represents all the quadrants form the midpoint of a grid
    private int[] getQuadrant(int quadrant) {
        int[] firstQ = { -1, -1 };
        int[] secondQ = { -1, 1 };
        int[] thirdQ = { 1, -1 };
        int[] fourtQ = { 1, 1 };

        switch (quadrant) {
            case 1:
                return firstQ;
            case 2:
                return secondQ;
            case 3:
                return thirdQ;
            case 4:
                return fourtQ;
            default:
                return null;
        }
    }

    // Gives the blueprint on how to visits all quardrant (squares) in a (2*2)grid
    // based on the orrientation
    private int[][] connectMidpoints(int orientation) {
        int[] firstQ = getQuadrant(1);
        int[] secondQ = getQuadrant(2);
        int[] thirdQ = getQuadrant(3);
        int[] fourtQ = getQuadrant(4);
        // the arr represents the order in which we are to visit each quardrant to
        // repreent an overll orientation
        int[][][] orientationArr = {
                { thirdQ, firstQ, secondQ },
                { secondQ, fourtQ, thirdQ },
                { thirdQ, fourtQ, secondQ },
                { secondQ, firstQ, thirdQ },
        };

        return orientationArr[orientation];
    }

    // get the orientation of each quaadrant in a grid based on the orientation of
    // the grid(previusly a quarant) they came from
    private int[] getQuadrantOrientation(int parentOrientation) {
        final int INVALID = -1;
        switch (parentOrientation) {
            case UP:
                return new int[] { UP, LEFT, UP, RIGHT };
            case DOWN:
                return new int[] { LEFT, DOWN, RIGHT, DOWN };
            case LEFT:
                return new int[] { DOWN, UP, LEFT, LEFT };
            case RIGHT:
                return new int[] { RIGHT, RIGHT, DOWN, UP };
            default:
                return new int[] { INVALID, INVALID, INVALID, INVALID };
        }
    }

    public ArrayList<int[]> mergePoints(ArrayList<ArrayList<int[]>> points) {
        ArrayList<int[]> mergedList = points.getFirst();
        for (int i = 1; i < points.size(); i++) {
            for (int[] point : points.get(i)) {
                mergedList.add(point);
            }
        }

        return mergedList;
    }
}
