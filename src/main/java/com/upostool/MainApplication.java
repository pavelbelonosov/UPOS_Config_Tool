package com.upostool;

import com.upostool.DAO.ChequeDAO;
import com.upostool.DAO.FileChequeDAO;
import com.upostool.DAO.FileSettingDAO;
import com.upostool.DAO.SettingDAO;
import com.upostool.ui.EnteringModule;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class MainApplication extends Application {

    private SettingDAO settingDAO;
    private ChequeDAO chequeDAO;

    @Override
    public void init() {
        settingDAO = new FileSettingDAO();
        chequeDAO = new FileChequeDAO(settingDAO.getLog());
    }

    @Override
    public void start(final Stage primaryStage) {
        Scene scene = new Scene(new EnteringModule(primaryStage, settingDAO, chequeDAO).getView(), 300, 300);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(MainApplication.class.getResourceAsStream("atm_icon.png")));
        primaryStage.setTitle("UPOS Config Tool v.0.3");
        primaryStage.show();
    }

    @Override
    public void stop() {
        chequeDAO.save();
    }

    public static void main(String[] args) {
        launch(args);
    }
}