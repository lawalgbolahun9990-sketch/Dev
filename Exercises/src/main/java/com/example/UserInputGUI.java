package com.example;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserInputGUI extends Application {
    private VBox pane = new VBox();
    @Override
    public void start(Stage primaryStage) {
        TextField userInputField = drawPromptOnNewPane();
        TextField feedbackTextField = setUserMessageOnPane(userInputField);
        feedbackTextField.editableProperty().set(false);

        refreshOnEnterClicked(userInputField, feedbackTextField);

        Scene scene = new Scene(pane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("User Input GUI");
        primaryStage.show();
    }
    private String getPromptMessage(){
        return "Enter Text:";
    }
    public TextField drawPromptOnNewPane(){
        VBox promptLayout = new VBox();
        TextField userTextField = new TextField();
        userTextField.setFont(Font.font(Font.getFontNames().get(3), FontWeight.SEMI_BOLD, FontPosture.REGULAR, 12));
        promptLayout.getChildren().add(new Label(getPromptMessage()));
        promptLayout.getChildren().add(userTextField);
        pane.getChildren().add(promptLayout);
        return userTextField;
    }
    public TextField setUserMessageOnPane(TextField userInputField){
        HBox userInputPane = new HBox();
        userInputPane.setSpacing(10);

        TextField feedback = new TextField();
        feedback.setText(userInputField.getText());
        feedback.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 12));

        userInputPane.getChildren().add(new Label("User Entered: "));
        userInputPane.getChildren().add(feedback);
        userInputPane.setAlignment(Pos.CENTER);

        pane.getChildren().add(userInputPane);
        return feedback;
    }
    public void refreshOnEnterClicked(TextField userInput, TextField feedback){
        pane.setOnKeyReleased(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                String userInputString = userInput.getText();
                feedback.setText(userInputString);
                feedback.setPrefColumnCount(userInputString.length());
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
