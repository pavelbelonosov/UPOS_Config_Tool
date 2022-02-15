package com.upostool.views;

import com.upostool.Unzip;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;


public class MainSettingsModule {
    private GridPane view;
    private String uposSelected;
    private String[] uposVersions;

    public MainSettingsModule() {
        this.view = new GridPane();
        this.uposSelected="";
        this.uposVersions=new String[]{"32.02.05",};
        setView();
    }

    private void setView() {

        Label version = new Label("UPOS version:");
        Label errorText = new Label();
        Label directoryLabel = new Label("Directory: ");
        TextField dir = new TextField("C:/sc552/");

        view.add(version, 0, 1);
        view.add(createBoundChoiceBox(), 1, 1);
        view.add(directoryLabel, 0, 2);
        view.add(dir, 1, 2);
        view.add(createBoundButton("APPLY", dir.getText(), errorText), 2, 6);
        view.add(errorText, 1, 7);

        Image image = new Image("File:atm_icon.png");

        //view.setStyle("-fx-background-color: #FFFFFF;");
        view.setStyle("-fx-background-image: url('" + image.getUrl().toString() + "'); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: stretch;");
        view.setAlignment(Pos.TOP_LEFT);
        view.setVgap(20);
        view.setHgap(20);
        view.setPadding(new Insets(20, 20, 20, 20));

    }

    private ChoiceBox createBoundChoiceBox() {
        ChoiceBox cb = new ChoiceBox();
        cb.setItems(FXCollections.observableArrayList(
                uposVersions[0], "Open ",
                new Separator(), "Save", "Save as")
        );
        cb.setOnAction((e) -> {
            uposSelected = cb.getSelectionModel().getSelectedItem().toString();
        });
        return cb;
    }

    private Button createBoundButton(String name, String toWhereUnzip, Label outputErrorMessage) {
        Button btn = new Button(name);
        btn.setOnAction((e) -> {
            try {
                new Unzip(toWhereUnzip, uposSelected + ".zip").UnzipFile();
            } catch (IOException ex) {
                outputErrorMessage.setText("Error: " + ex.getMessage());
            }
        });
        return btn;
    }


    public Parent getView() {
        return view;
    }
}
