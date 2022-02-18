package com.upostool.domain.views;

import com.upostool.MainApplication;
import com.upostool.domain.views.MainSettingsModule;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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

        Image image = new Image(MainApplication.class.getResourceAsStream("logo_transparent.png"));
        TextField login = new TextField("Login");
        PasswordField passwordField = new PasswordField();
        passwordField.setText("Password");

        view.add(login, 0, 0);
        view.add(passwordField, 0, 1);
        view.add(new ImageView(image), 0, 3);

        //view.setStyle("-fx-background-color: #FFFFFF;");
        view.setStyle("-fx-base: #373e43;\n" +
                "    -fx-control-inner-background: derive(-fx-base, 35%);\n" +
                "    -fx-control-inner-background-alt: -fx-control-inner-background ");
        view.setAlignment(Pos.CENTER);
        view.setVgap(10);
        view.setHgap(10);
        view.setPadding(new Insets(20, 20, 20, 20));

        passwordField.textProperty().addListener((change, oldValue, newValue) -> {
            String input = login.getText().toLowerCase(Locale.ROOT).trim();

            if (input.matches("atm") & newValue.equals("166831")) {
                this.window.close();
                openMainSettingStage("UPOS SETTINGS");
            }
        });

    }

    private void openMainSettingStage(String title) {
        Scene scene = new Scene(new MainSettingsModule().getView(), 420, 450);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.getIcons().add(new Image(MainApplication.class.getResourceAsStream("atm_icon.png")));
        stage.setMaxHeight(550);
        stage.setMaxWidth(550);
        stage.setScene(scene);
        stage.show();
    }

    public Parent getView() {
        return view;
    }
}
