package com.upostool.domain.views;

import com.upostool.Util;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class AdvancedSettingsModule {
    private Map<String, String> pinpadSettings;
    private List<String> logList;
    private GridPane view;

    public AdvancedSettingsModule(Map pinpadSettings, List logList) {
        this.view = new GridPane();
        this.logList = logList;
        this.pinpadSettings = pinpadSettings;
        setView();
    }

    private void setView() {
        //Creating and adding components to the view
        //1.CardHolderSignatureImage
        TextField pathToSignatureImageTextField = new TextField("C:/sc552/sign.png");
        view.add(createLabelWithTooltip("CardHolderSignatureImage=",
                Util.PINPADINI_VALUES_EXPLANATIONS.CARD_HOLDER_SIGNATURE_IMAGE.getExplanation()), 0, 0);
        view.add(pathToSignatureImageTextField, 1, 0);
        view.add(createBoundWithTextFieldCheckBox(pathToSignatureImageTextField,
                "CardHolderSignatureImage="), 2, 0);

        //2.DayliReport
        TextField dayliReportTextField = new TextField("%s_info.txt");
        view.add(createLabelWithTooltip("DayliReport=",
                Util.PINPADINI_VALUES_EXPLANATIONS.DAYLIREPORT.getExplanation()), 0, 1);
        view.add(dayliReportTextField, 1, 1);
        view.add(createBoundWithTextFieldCheckBox(dayliReportTextField, "DayliReport="), 2, 1);

        //3.Department
        view.add(createLabelWithTooltip("Department=",
                Util.PINPADINI_VALUES_EXPLANATIONS.DEPARTMENT.getExplanation()), 0, 3);
        ChoiceBox cb = createNumericCBox();
        view.add(cb, 1, 3);
        view.add(createBoundWithChoiceBoxCheckBox(cb, "Department="), 2, 3);

        //Styling view
        view.setStyle(Util.BLACK_THEME);
        view.setPrefSize(380, 500);
        view.setAlignment(Pos.BASELINE_CENTER);
        view.setVgap(5);
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

    private ChoiceBox createNumericCBox() {
        Integer[] numbers = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ChoiceBox<Integer> cb = new ChoiceBox();
        cb.setItems(FXCollections.observableArrayList(
                numbers)
        );
        return cb;
    }

    private CheckBox createBoundWithChoiceBoxCheckBox(ChoiceBox choiceBox, String settingName) {
        CheckBox cb = new CheckBox();
        cb.selectedProperty().addListener((change, oldValue, newValue) -> {
            if (newValue) {
                this.pinpadSettings
                        .put(settingName, choiceBox.getSelectionModel().getSelectedItem().toString());
                insertSettingsStateIntoLogList(settingName);
            } else {
                this.pinpadSettings.entrySet()
                        .removeIf(entries -> entries.getKey().equals(settingName));
            }
        });
        return cb;
    }

    private CheckBox createBoundWithTextFieldCheckBox(TextField field, String settingName) {
        CheckBox cb = new CheckBox();
        cb.selectedProperty().addListener((change, oldValue, newValue) -> {
            if (newValue) {
                this.pinpadSettings
                        .put(settingName, field.getText());
                insertSettingsStateIntoLogList(settingName);
            } else {
                this.pinpadSettings.entrySet()
                        .removeIf(entries -> entries.getKey().equals(settingName));
            }
        });
        return cb;
    }


    private void insertSettingsStateIntoLogList(String settingName) {
        this.logList.add(getLocatDateTime() + "ADDING " + settingName
                + pinpadSettings.get(settingName) + " INTO CACHE...");
    }

    String getLocatDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
        String timeStamp = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(dtf) + ":    ";
        return timeStamp;
    }

    public Parent getView() {
        return this.view;
    }
}
