package com.example;

import java.io.File;
import java.util.ArrayList;

import javafx.animation.PathTransition;
import javafx.animation.Animation.Status;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;

public class KnightTravesalAnimation extends VBox {
    int count;
    private int[] startPoint = { 0, 0 };
    private KnightTraversal traversal;
    private VisualGridPane board = new VisualGridPane(50, 4);
    private PathTransition animation = new PathTransition();

    KnightTravesalAnimation(KnightTraversal traversal) {
        this.traversal = traversal;

        Button btSolve = new Button("Solve");
        btSolve.setOnAction(e -> animate());
        btSolve.setAlignment(Pos.CENTER);

        board.setOnMouseClicked(e -> {
            int spacing = board.getLineSpacing();
            startPoint[0] = (int) e.getY() / spacing;
            startPoint[1] = (int) e.getX() / spacing;
            System.out.println(" ( " + startPoint[0] + " , " + startPoint[1] + ") ");

            ImageView startKnight = new ImageView(new File("knight.png").getPath());
            startKnight.setFitHeight(spacing);
            startKnight.setFitWidth(spacing);

            startKnight.setX(startPoint[1] * spacing);
            startKnight.setY(startPoint[0] * spacing);

            clear();
            board.getChildren().add(startKnight);
        });

        getChildren().addAll(board, new StackPane(btSolve));
    }

    public void animate() {
        if (animation.getStatus().equals(Status.RUNNING))
            animation.stop();
        board.getChildren().removeIf(node -> (node instanceof ImageView) && node.getId() != null);

        ImageView knight = new ImageView(new File("knight.png").getPath());
        knight.setId("movingKnight");
        knight.setFitHeight(board.getLineSpacing());
        knight.setFitWidth(board.getLineSpacing());

        ArrayList<int[]> coordinates = traversal.getTraversedCoordinates(startPoint[0], startPoint[1]);
        count = coordinates.size() -1;
        int spacing = board.getLineSpacing();
        Line path = new Line(
            coordinates.get(count)[0] * spacing + spacing / 2,
            coordinates.get(count)[1] * spacing + spacing / 2,
            coordinates.get(count -1)[0] * spacing + spacing / 2,
            coordinates.get(count -1)[1] * spacing + spacing / 2
        );
        path.setId("path");
        count -= 1;
        updatePath(coordinates);

        animation.setNode(knight);
        board.getChildren().add(knight);
        board.getChildren().add(path);

        animation.setPath(path);
        animation.setDuration(new Duration(400));
        animation.setRate(1);
        animation.play();
    }
    public void updatePath(ArrayList<int[]> coordinates){
        int spacing = board.getLineSpacing();
        animation.statusProperty().addListener(ov -> {
            if (animation.getStatus().equals(Status.STOPPED)){
                Line newPath = new Line();
                newPath.setId("newPath");
                board.getChildren().add(newPath);
                if (count >= 1){
                    newPath.setStartX(coordinates.get(count)[0] * spacing + spacing / 2);
                    newPath.setStartY(coordinates.get(count)[1] * spacing + spacing / 2);
                    newPath.setEndX(coordinates.get(count - 1)[0] * spacing + spacing / 2);
                    newPath.setEndY(coordinates.get(count - 1)[1] * spacing + spacing / 2);
                    count -= 1;
                }
                animation.setPath(newPath);
                animation.playFromStart();
            }
        });
    }
    public void clear() {
        animation.pause();
        board.getChildren().removeIf(node -> (node instanceof ImageView || (node instanceof Line && ((Line)node).getId() != null)));
    }
}
