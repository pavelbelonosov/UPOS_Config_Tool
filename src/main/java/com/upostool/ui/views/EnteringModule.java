package com.upostool.ui.views;

import com.upostool.DAO.ChequeDAO;
import com.upostool.DAO.SettingDAO;
import com.upostool.util.Cons;

import com.upostool.util.PropertiesHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EnteringModule {

    private GridPane view;
    private Stage stage; //primaryStage
    private SettingDAO settingDAO;
    private ChequeDAO chequeDAO;
    private PropertiesHandler propertiesHandler;

    public EnteringModule(Stage stage, SettingDAO settingDAO, ChequeDAO chequeDAO) {
        this.view = new GridPane();
        this.stage = stage;
        this.settingDAO = settingDAO;
        this.chequeDAO = chequeDAO;
        this.propertiesHandler = new PropertiesHandler();
        this.setView();
    }

    private void setView() {
        // Image image = new Image(MainApplication.class.getResourceAsStream("logo_transparent.png"));
        TextField login = new TextField("login");
        PasswordField passwordField = new PasswordField();
        passwordField.setText("Password");

        view.add(login, 0, 0);
        view.add(passwordField, 0, 1);
        view.add(new ImageView(Cons.LOGO_TRANSPARENT), 0, 3);
        view.setStyle(Cons.BLACK_THEME);
        view.setAlignment(Pos.CENTER);
        view.setVgap(10);
        view.setHgap(10);
        view.setPadding(new Insets(20, 20, 20, 20));

        passwordField.textProperty().addListener((change, oldValue, newValue) -> {
            String input = login.getText().toLowerCase().trim();
            if (input.matches(propertiesHandler.getUser()) && newValue.equals(propertiesHandler.getPassword())) {
                this.stage.close();
                openStage("UPOS SETTINGS", new MainSettingsModule(settingDAO, chequeDAO).getView());

            }
        });
    }

    static void openStage(String title, Parent pane) {
        Scene scene = new Scene(pane);
        Stage newStage = new Stage();
        newStage.setTitle(title);
        newStage.getIcons().add(Cons.ATM_ICON_MAIN);
        newStage.setMaxWidth(700);
        newStage.setMaxHeight(700);
        newStage.setScene(scene);
        newStage.show();
    }

    public Parent getView() {
        return view;
    }
}
