package com.upostool.ui.views;

import com.upostool.domain.AppLog;
import com.upostool.util.Cons;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class LogModule {
    private GridPane view;
    private AppLog log;

    public LogModule(AppLog log) {
        this.view = new GridPane();
        this.log = log;
        setView();
    }

    private void setView() {
        //Creating/adding components
        Label thisAppVersion = new Label("UPOS CONFIG TOOL v.0.3");
        Label contact = new Label("CONTACT:  belonosov.kamensk@atm72.ru");
        view.add(thisAppVersion, 0, 0);
        view.add(contact, 0, 1);
        view.add(createLogTextArea(), 0, 3);
        view.add(new ImageView(Cons.LOGO_TRANSPARENT), 0, 4);

        //Styling
        view.setStyle(Cons.BLACK_THEME);
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
        area.setText(this.log.toString());
        area.end();
        return area;
    }

    public Parent getView() {
        return view;
    }
}
