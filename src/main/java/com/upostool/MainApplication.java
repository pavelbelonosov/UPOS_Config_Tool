package com.upostool;


import com.upostool.views.EnteringModule;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(final Stage primaryStage) {
        Scene scene = new Scene(new EnteringModule(primaryStage).getView(), 300,300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("UPOS Config Tool v.0.1");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}