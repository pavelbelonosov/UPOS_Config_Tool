package com.upostool.domain.views;

import com.upostool.Util;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.List;

public class LogModule {
    private GridPane view;
    private List<String> log;

    public LogModule(List log) {
        this.view = new GridPane();
        this.log = log;
        setView();
    }

    private void setView() {
        //Creating and adding components
        Label thisAppVersion = new Label("UPOS CONFIG TOOL v.0.2");
        Label contact = new Label("CONTACT:  belonosov.kamensk@atm72.ru");
        view.add(thisAppVersion, 0, 0);
        view.add(contact, 0, 1);
        view.add(createLogTextArea(), 0, 3);
        view.add(new ImageView(Util.LOGO_TRANSPARENT), 0, 4);

        //Styling
        view.setStyle(Util.BLACK_THEME);
        view.setPrefSize(400, 420);
        view.setAlignment(Pos.TOP_CENTER);
        view.setVgap(10);
        view.setHgap(0);
        view.setPadding(new Insets(20, 20, 20, 20));
    }

    private TextArea createLogTextArea() {
        TextArea area = new TextArea();
        area.setWrapText(true);
        area.setMinSize(200, 300);
        StringBuilder str = new StringBuilder();
        this.log.stream().forEach(line -> str.append(line + "\n"));
        area.setText(str.toString());
        area.end();
        return area;
    }

    public Parent getView() {
        return view;
    }
}
