package com.upostool.domain.views;

import com.upostool.MainApplication;
import com.upostool.Util;
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
    private Stage stage; //primaryStage

    public EnteringModule(Stage stage) {
        this.view = new GridPane();
        this.stage = stage;
        this.setView();
    }

    private void setView() {

        // Image image = new Image(MainApplication.class.getResourceAsStream("logo_transparent.png"));
        TextField login = new TextField("login");
        PasswordField passwordField = new PasswordField();
        passwordField.setText("Password");

        view.add(login, 0, 0);
        view.add(passwordField, 0, 1);
        view.add(new ImageView(Util.LOGO_TRANSPARENT), 0, 3);

        //view.setStyle("-fx-background-color: #FFFFFF;");
        view.setStyle(Util.BLACK_THEME);
        view.setAlignment(Pos.CENTER);
        view.setVgap(10);
        view.setHgap(10);
        view.setPadding(new Insets(20, 20, 20, 20));

        passwordField.textProperty().addListener((change, oldValue, newValue) -> {
            String input = login.getText().toLowerCase(Locale.ROOT).trim();

            if (input.matches("atm") & newValue.equals("166831")) {
                this.stage.close();
                openStage("UPOS SETTINGS", new MainSettingsModule().getView());

            }
        });

    }

    static void openStage(String title, Parent pane) {
        Scene scene = new Scene(pane);//485, 450
        Stage newStage = new Stage();
        newStage.setTitle(title);
        newStage.getIcons().add(Util.ATM_ICON_MAIN);
        newStage.setMaxHeight(550);
        newStage.setMaxWidth(550);
        newStage.setScene(scene);
        newStage.show();
    }



    public Parent getView() {
        return view;
    }
}
