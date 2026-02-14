package com.example;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MyCalender extends Application{
    private GridPane calenderPane = new GridPane();
    private static int year = GregorianCalendar.getInstance().get(Calendar.YEAR);
    private static int month = GregorianCalendar.getInstance().get(Calendar.MONTH);

    public void start(Stage primaryStage){
        Calendar calendar = new GregorianCalendar(year,month,1);
        String[] monthName = {"January","Febuary","March","April","May","June","July","August","September","October","November","December"};
        paintCalender(calendar);
        Label date = new Label("" + monthName[month] + " ,"+ year);
        
        Button priorButton = new Button("Prior");
        Button nextButton = new Button("Next");
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(priorButton,nextButton);

        priorButton.setOnAction(e -> {
            if (month == 0){
                calendar.set(Calendar.YEAR,--year);
                month = 11 + 1;
            }
            calendar.set(Calendar.MONTH, --month);
            date.setText("" + monthName[month] + " ,"+ year);
            paintCalender(calendar);
        });

        nextButton.setOnAction(e -> {
            if (month == 11) {
                calendar.set(Calendar.YEAR,++year);
                month = 0 - 1;
            }
            calendar.set(Calendar.MONTH, ++month);
            date.setText("" + monthName[month] + " ,"+ year);
            paintCalender(calendar);
        });
        calenderPane.setAlignment(Pos.CENTER);
        buttonBox.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setTop(new StackPane(date));
        root.setCenter(calenderPane);
        root.setBottom(buttonBox);

        Scene scene = new Scene(root,400,400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("User Input GUI");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    public void paintCalender(Calendar calendar){
        calenderPane.getChildren().clear();
        int startDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int dayOfMonth = 1;
        calenderPane.setVgap(10);
        calenderPane.setHgap(10);
        String[] dayName = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday",};
        for (int i = 0; i < dayName.length; i++) {
            calenderPane.add(new Text(dayName[i]), i,0);
        }
        boolean isFull = false;
        Text dayOfMonthText = new Text();
        Color textColor = Color.BLACK;
        for (int rowIndex = 1; rowIndex <= dayName.length; rowIndex++) {
            int startPadding = getNumOfDaysInMonth(year, month-1) - startDayOfWeek + 2;
            for (int colomnIndex = 0; colomnIndex < dayName.length; colomnIndex++) {
                if (rowIndex == 1 && colomnIndex+1 < startDayOfWeek) {
                    dayOfMonthText = new Text(""+ startPadding++);
                    dayOfMonthText.setFill(Color.GRAY);
                    calenderPane.add(dayOfMonthText, colomnIndex, rowIndex);
                }
                else{
                    if (dayOfMonth  > getNumOfDaysInMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH))){
                        dayOfMonth = 1;
                        textColor = Color.GREY;
                        isFull = true;
                    }
                    dayOfMonthText = new Text(dayOfMonth++ + "");
                    dayOfMonthText.setFill(textColor);
                    calenderPane.add(dayOfMonthText, colomnIndex, rowIndex);
                }
            }
            if (isFull) break;
        }
    }
    public int getNumOfDaysInMonth(int year,int month){
        month %= 11;
        if (month == 3 || month == 5 || month == 8 || month == 10 ) return 30;
        if (month == 1) return isLeapYear(year) ? 29 : 28;
        return 31;
    }
    public boolean isLeapYear(int year){
        return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0);
    }
}
