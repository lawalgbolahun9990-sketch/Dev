package com.example;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FileCharOccurenceHistogram extends Application {
    private MyHistogram histogram;
    private int[] countOccurences = new int[26];

    public void start(Stage primaryStage) {
        BorderPane pane = new BorderPane();

        histogram = new MyHistogram(countOccurences);

        TextField fileNameField = new TextField();
        fileNameField.setPrefWidth(300);
        fileNameField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                setHistogram(fileNameField);
            }
        });

        Button viewButton = new Button("View");
        viewButton.setOnAction(e -> {
            setHistogram(fileNameField);
        });

        HBox userPrompt = new HBox();
        userPrompt.getChildren().addAll(new Text("FileName"), fileNameField, viewButton);
        userPrompt.setAlignment(Pos.CENTER);

        pane.setBottom(userPrompt);
        pane.setCenter(histogram);

        Scene scene = new Scene(pane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("User Input GUI");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setOccourenceString(String s, int[] countOccurences) {
        String string = s.toLowerCase();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (!Character.isAlphabetic(c))
                continue;
            countOccurences[c - 'a']++;
        }
    }

    private void setHistogram(TextField fileNameField) {
        try {
            clear();
            File file = new File(fileNameField.getText());
            Scanner s = new Scanner(file);
            while (s.hasNext()) {
                setOccourenceString(s.next(), countOccurences);
            }
            histogram.setCount(countOccurences);
        } catch (IOException exception) {
            System.err.println("IO Error");
            return;
        }
    }

    public void clear() {
        for (int i = 0; i < countOccurences.length; i++) {
            countOccurences[i] = 0;
        }
    }
}
