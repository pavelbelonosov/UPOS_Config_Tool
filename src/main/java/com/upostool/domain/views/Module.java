package com.upostool.domain.views;

import com.upostool.Util;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public abstract class Module {
    private GridPane view;

    public Module(){
        this.view=new GridPane();
        setView();
    }

    private void setView(){
        view.setStyle(Util.BLACK_THEME);
    }

   public void openStage(String title, Parent pane) {
        Scene scene = new Scene(pane, 485, 450);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.getIcons().add(Util.ATM_ICON_MAIN);
        stage.setMaxHeight(550);
        stage.setMaxWidth(550);
        stage.setX(100);
        stage.setY(50);
        stage.setScene(scene);
        stage.show();
    }


    public void moveStage(Stage thisStage, Stage other){
        other.setX(thisStage.getX()+30);
        other.setY(thisStage.getY()+20);
    }

    private Parent getView(){
        return view;
    }
}
