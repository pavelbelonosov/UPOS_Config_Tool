package com.upostool;


import com.upostool.domain.views.EnteringModule;
import com.upostool.domain.views.TestFunctionsModule;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(final Stage primaryStage) {
            Scene scene = new Scene(new EnteringModule(primaryStage).getView(), 300, 300);
            primaryStage.setScene(scene);
            primaryStage.getIcons().add(new Image(MainApplication.class.getResourceAsStream("atm_icon.png")));
            primaryStage.setTitle("UPOS Config Tool v.0.2");
            primaryStage.show();
        }


    public static void main(String[] args) {
        launch(args);
    }
}