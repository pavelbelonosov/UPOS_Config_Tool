package com.upostool.domain.views;

import com.upostool.Util;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
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
        ChoiceBox departmentsChoiceBox = createChoiceBox(Util.DEPARTMENTS);
        view.add(createLabelWithTooltip("Department=",
                Util.PINPADINI_VALUES_EXPLANATIONS.DEPARTMENT.getExplanation()), 0, 3);
        view.add(departmentsChoiceBox, 1, 3);
        view.add(createBoundWithChoiceBoxCheckBox(departmentsChoiceBox, "Department="), 2, 3);

        //4.DocLimit
        TextField docLimitTextField = new TextField("4096");
        view.add(createLabelWithTooltip("DocLimit=",
                Util.PINPADINI_VALUES_EXPLANATIONS.DOCLIMIT.getExplanation()), 0, 4);
        view.add(docLimitTextField, 1, 4);
        view.add(createBoundWithTextFieldCheckBox(docLimitTextField, "DocLimit="), 2, 4);

        //5.EnableUSB
        view.add(createLabelWithTooltip("EnableUSB=",
                Util.PINPADINI_VALUES_EXPLANATIONS.ENABLEUSB.getExplanation()), 0, 5);
        view.add(createHorizSeparator(), 1, 5);
        view.add(create1or0CheckBox("EnableUSB="), 2, 5);

        //6.ExtraDelay
        TextField extraDelayTextField = new TextField("1000");
        view.add(createLabelWithTooltip("ExtraDelay=",
                Util.PINPADINI_VALUES_EXPLANATIONS.EXTRADELAY.getExplanation()), 0, 6);
        view.add(extraDelayTextField, 1, 6);
        view.add(createBoundWithTextFieldCheckBox(extraDelayTextField, "ExtraDelay="), 2, 6);

        //7.FakeModel
        ChoiceBox fakeModelChoiceBox = createChoiceBox(Util.PINPAD_MODELS);
        view.add(createLabelWithTooltip("FakeModel="
                , Util.PINPADINI_VALUES_EXPLANATIONS.FAKEMODEL.getExplanation()), 0, 7);
        view.add(fakeModelChoiceBox, 1, 7);
        view.add(createBoundWithChoiceBoxCheckBox(fakeModelChoiceBox, "FakeModel="), 2, 7);

        //8.FakeSerialNumber
        TextField fakeSerialNumTextField = new TextField(("G00321456987"));
        view.add(createLabelWithTooltip("FakeSerialNumber=",
                Util.PINPADINI_VALUES_EXPLANATIONS.FAKESERIALNUMBER.getExplanation()), 0, 8);
        view.add(fakeSerialNumTextField, 1, 8);
        view.add(createBoundWithTextFieldCheckBox(fakeSerialNumTextField, "FakeSerialNumber="), 2, 8);

        //9.ForceHostAddr
        TextField forceHostAddrTField = new TextField("0.0.0.0");
        view.add(createLabelWithTooltip("ForceHostAddr=",
                Util.PINPADINI_VALUES_EXPLANATIONS.FORCEHOSTADDR.getExplanation()), 0, 9);
        view.add(forceHostAddrTField, 1, 9);
        view.add(createBoundWithTextFieldCheckBox(forceHostAddrTField, "ForceHostAddr="), 2, 9);

        //10.ForceHostPort
        TextField forceHostPortTField = new TextField("YYY=ZZZ");
        view.add(createLabelWithTooltip("ForceHostPort",
                Util.PINPADINI_VALUES_EXPLANATIONS.FORCEHOSTPORT.getExplanation()), 0, 10);
        view.add(forceHostPortTField, 1, 10);
        view.add(createBoundWithTextFieldCheckBox(forceHostPortTField, "ForceHostPort"), 2, 10);

        //11.Header
        TextField headerTextField = new TextField("Новый магазин|Спасибо за покупку!");
        view.add(createLabelWithTooltip("Header=",
                Util.PINPADINI_VALUES_EXPLANATIONS.HEADER.getExplanation()), 0, 11);
        view.add(headerTextField, 1, 11);
        view.add(createBoundWithTextFieldCheckBox(headerTextField, "Header="), 2, 11);

        //12.ImageOutputFormat
        ChoiceBox imageOutputFormatChoiveBox = createChoiceBox(new String[]{"png", "bmp"});
        view.add(createLabelWithTooltip("ImageOutputFormat=",
                Util.PINPADINI_VALUES_EXPLANATIONS.IMAGEOUTPUTFORMAT.getExplanation()), 0, 12);
        view.add(imageOutputFormatChoiveBox, 1, 12);
        view.add(createBoundWithChoiceBoxCheckBox(imageOutputFormatChoiveBox, "ImageOutputFormat="), 2, 12);

        //13.LeftTopCorner
        view.add(createLabelWithTooltip("LeftTopCorner=",
                Util.PINPADINI_VALUES_EXPLANATIONS.LEFTTOPCORNER.getExplanation()), 0, 13);
        view.add(createHorizSeparator(), 1, 13);
        view.add(create1or0CheckBox("LeftTopCorner="), 2, 13);

        //14.LostWaitPackets
        TextField lostWaitPackets = new TextField("3");
        view.add(createLabelWithTooltip("LostWaitPackets=",
                Util.PINPADINI_VALUES_EXPLANATIONS.LOSTWAITPACKETS.getExplanation()), 0, 14);
        view.add(lostWaitPackets,1,14);
        view.add(createBoundWithTextFieldCheckBox(lostWaitPackets,"LostWaitPackets="),2,14);

        //15.MerchantID
        TextField merchantIDTextField = new TextField("12345678900");
        view.add(createLabelWithTooltip("MerchantID=",
                Util.PINPADINI_VALUES_EXPLANATIONS.MERCHANTID.getExplanation()),0,15);
        view.add(merchantIDTextField,1,15);
        view.add(createBoundWithTextFieldCheckBox(merchantIDTextField,"MerchantID="),2,15);


        //Styling view
        view.setStyle(Util.BLACK_THEME);
        view.setPrefSize(400, 500);
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

    private ChoiceBox createChoiceBox(String[] values) {
        ChoiceBox cb = new ChoiceBox();
        cb.setItems(FXCollections.observableArrayList(
                Arrays.asList(values))
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

    private CheckBox create1or0CheckBox(String settingName) {
        CheckBox cb = new CheckBox();
        cb.selectedProperty().addListener((change, oldValue, newValue) -> {
            if (newValue) {
                this.pinpadSettings.put(settingName, "1");
                insertSettingsStateIntoLogList(settingName);
            } else {
                pinpadSettings.put(settingName, "0");
                insertSettingsStateIntoLogList(settingName);
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

    private Separator createHorizSeparator() {
        Separator hSeparator = new Separator(Orientation.HORIZONTAL);
        hSeparator.setPrefHeight(10);
        return hSeparator;
    }

    public Parent getView() {
        return this.view;
    }
}
