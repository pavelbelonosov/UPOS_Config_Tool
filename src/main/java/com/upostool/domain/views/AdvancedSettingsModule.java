package com.upostool.domain.views;

import com.upostool.Util;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Map;

public class AdvancedSettingsModule {
    private Map<String, String> pinpadSettings;
    private GridPane view;

    public AdvancedSettingsModule(Map pinpadSettings) {
        this.view = new GridPane();
        this.pinpadSettings = pinpadSettings;
        setView();
    }

    private void setView() {
        //Creating and adding components to the view
        //1.CardHolderSignatureImage
        TextField pathToSignatureImageField = new TextField("C:/sc552/sign.png");
        view.add(createLabelWithTooltip("CardHolderSignatureImage",
                Util.PINPADINI_VALUES_EXPLANATIONS.CARD_HOLDER_SIGNATURE_IMAGE.getExplanation()), 0, 0);
        view.add(pathToSignatureImageField, 1, 0);
        view.add(createCBoxSignatureImagePath(pathToSignatureImageField),2,0);


        //Styling view
        view.setStyle(Util.BLACK_THEME);
        view.setPrefSize(380, 500);
        view.setAlignment(Pos.BASELINE_CENTER);
        view.setVgap(10);
        view.setHgap(20);
        view.setPadding(new Insets(10, 10, 10, 10));
    }

    private Label createLabelWithTooltip(String name, String explanationTooltip) {
        Label label = new Label(name);
        Tooltip tooltip = new Tooltip();
        tooltip.setText(explanationTooltip);
        label.setTooltip(tooltip);
        return label;
    }

    private CheckBox createCBoxSignatureImagePath(TextField pathToSignatureImageField ) {
        CheckBox cb = new CheckBox();
        cb.selectedProperty().addListener((change, oldValue, newValue) -> {
            if (newValue) {
                pinpadSettings.put("CardHolderSignatureImage=", pathToSignatureImageField.getText());
            } else {
                pinpadSettings.entrySet().removeIf(entries->entries.getKey().equals("CardHolderSignatureImage="));
            }
        });
        return cb;
    }


    public Parent getView() {
        return view;
    }
}
