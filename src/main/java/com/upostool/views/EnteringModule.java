package com.upostool.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

import java.util.Locale;

public class EnteringModule {
    private GridPane view;
    private Stage window; //primaryStage

    public EnteringModule(Stage stage) {
        this.view = new GridPane();
        this.window = stage;
        this.setView();
    }

    private void setView() {
        Image image = new Image("File:atm_icon.png");
        TextField login = new TextField("Login");
        PasswordField passwordField = new PasswordField();
        passwordField.setText("Password");

        view.add(login, 0, 0);
        view.add(passwordField, 0, 1);
        view.add(new ImageView(image), 0, 3);

        view.setStyle("-fx-background-color: #FFFFFF;");
        view.setAlignment(Pos.CENTER);
        view.setVgap(10);
        view.setHgap(10);
        view.setPadding(new Insets(20, 20, 20, 20));

        passwordField.textProperty().addListener((change, oldValue, newValue) -> {
            String input = login.getText().toLowerCase(Locale.ROOT).trim();
            Scene scene = new Scene(new MainSettingsModule().getView(), 400, 400);
            Stage stage = new Stage();
            if (input.matches("atm") & newValue.equals("166831")) {
                this.window.close();
                stage.setTitle("UPOS Settings");
                stage.setScene(scene);
                stage.show();
            }
        });

    }

    public Parent getView() {
        return view;
    }
}
