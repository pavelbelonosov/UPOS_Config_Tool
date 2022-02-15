package com.upostool.views;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;


public class MainSettingsModule {
    private GridPane view;

    public MainSettingsModule(){
        this.view = new GridPane();
        setView();

    }

    private void setView(){
        Label version = new Label("UPOS version:");
        view.add(version,0,1);
    }

    public Parent getView(){
        return  view;
    }
}
