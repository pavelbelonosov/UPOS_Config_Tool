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
        view.add(lostWaitPackets, 1, 14);
        view.add(createBoundWithTextFieldCheckBox(lostWaitPackets, "LostWaitPackets="), 2, 14);

        //15.MerchantID
        TextField merchantIDTextField = new TextField("12345678900");
        view.add(createLabelWithTooltip("MerchantID=",
                Util.PINPADINI_VALUES_EXPLANATIONS.MERCHANTID.getExplanation()), 0, 15);
        view.add(merchantIDTextField, 1, 15);
        view.add(createBoundWithTextFieldCheckBox(merchantIDTextField, "MerchantID="), 2, 15);

        //16.OnlyDos
        view.add(createLabelWithTooltip("OnlyDos=",
                Util.PINPADINI_VALUES_EXPLANATIONS.ONLYDOS.getExplanation()), 0, 16);
        view.add(createHorizSeparator(), 1, 16);
        view.add(create1or0CheckBox("OnlyDos="), 2, 16);

        //17.OptimalSpeed
        ChoiceBox optimalSpeedChoiceBox = createChoiceBox(Util.PINPAD_SPEED);
        view.add(createLabelWithTooltip("OptimalSpeed=",
                Util.PINPADINI_VALUES_EXPLANATIONS.OPTIMALSPEED.getExplanation()), 4, 0);
        view.add(optimalSpeedChoiceBox, 5, 0);
        view.add(createBoundWithChoiceBoxCheckBox(optimalSpeedChoiceBox, "OptimalSpeed="), 6, 0);

        //18.PilotNtSeparateCancelAndRefund
        view.add(createLabelWithTooltip("PilotNtSeparateCancelAndRefund=",
                Util.PINPADINI_VALUES_EXPLANATIONS.PILOTNTSEPARATECANCELANDREFUND.getExplanation()), 4, 1);
        view.add(createHorizSeparator(), 5, 1);
        view.add(create1or0CheckBox("PilotNtSeparateCancelAndRefund="), 6, 1);

        //19.PinpadIPPort
        TextField pinpadIPportTextField = new TextField("8888");
        view.add(createLabelWithTooltip("PinpadIPPort=",
                Util.PINPADINI_VALUES_EXPLANATIONS.PINPADIPPORT.getExplanation()), 4, 2);
        view.add(pinpadIPportTextField, 5, 2);
        view.add(createBoundWithTextFieldCheckBox(pinpadIPportTextField, "PinpadIPPort="), 6, 2);

        //20.PinpadLog
        view.add(createLabelWithTooltip("PinpadLog=",
                Util.PINPADINI_VALUES_EXPLANATIONS.PINPADLOG.getExplanation()), 4, 3);
        view.add(createHorizSeparator(), 5, 3);
        view.add(create1or0CheckBox("PinpadLog="), 6, 3);

        //21.PrinterBold
        TextField printerBoldTextField = new TextField("010D0A");
        view.add(createLabelWithTooltip("printerBoldTextField=",
                Util.PINPADINI_VALUES_EXPLANATIONS.PRINTERBOLD.getExplanation()), 4, 4);
        view.add(printerBoldTextField, 5, 4);
        view.add(createBoundWithTextFieldCheckBox(printerBoldTextField, "printerBoldTextField="), 6, 4);

        //22.PrinterFile
        TextField printerFileTextField = new TextField("p");
        view.add(createLabelWithTooltip("PrinterFile=",
                Util.PINPADINI_VALUES_EXPLANATIONS.PRINTERFILE.getExplanation()), 4, 5);
        view.add(printerFileTextField, 5, 5);
        view.add(createBoundWithTextFieldCheckBox(printerFileTextField, "PrinterFile="), 6, 5);

        //23.PrinterHigh
        TextField printerHighTextField = new TextField("1D43");
        view.add(createLabelWithTooltip("PrinterHigh",
                Util.PINPADINI_VALUES_EXPLANATIONS.PRINTERHIGH.getExplanation()), 4, 6);
        view.add(printerHighTextField, 5, 6);
        view.add(createBoundWithTextFieldCheckBox(printerHighTextField, "PrinterHigh="), 6, 6);

        //24.PrinterInit
        TextField printerInitTextField = new TextField("1B6701");
        view.add(createLabelWithTooltip("PrinterInit=",
                Util.PINPADINI_VALUES_EXPLANATIONS.PRINTERINIT.getExplanation()), 4, 7);
        view.add(printerInitTextField, 5, 7);
        view.add(createBoundWithTextFieldCheckBox(printerInitTextField, "PrinterInit="), 6, 7);

        //25.PrinterInverse
        TextField printerInverseTextField = new TextField("1D42");
        view.add(createLabelWithTooltip("PrinterInverse=",
                Util.PINPADINI_VALUES_EXPLANATIONS.PRINTERINVERSE.getExplanation()), 4, 8);
        view.add(printerInverseTextField, 5, 8);
        view.add(createBoundWithTextFieldCheckBox(printerInverseTextField, "PrinterInverse="), 6, 8);

        //26.PrinterType
        ChoiceBox printerTypeCBox = createChoiceBox(Util.PRINTER_TYPE);
        view.add(createLabelWithTooltip("PrinterType=",
                Util.PINPADINI_VALUES_EXPLANATIONS.PRINTERTYPE.getExplanation()), 4, 9);
        view.add(printerTypeCBox, 5, 9);
        view.add(createBoundWithChoiceBoxCheckBox(printerTypeCBox, "PrinterType="), 6, 9);

        //27.PrinterWide
        TextField printerWideTextField = new TextField("1B2121");
        view.add(createLabelWithTooltip("PrinterWide=",
                Util.PINPADINI_VALUES_EXPLANATIONS.PRINTERWIDE.getExplanation()), 4, 10);
        view.add(printerWideTextField, 5, 10);
        view.add(createBoundWithTextFieldCheckBox(printerWideTextField, "PrinterWide="), 6, 10);

        //28.ScheduledCommand
        TextField shedCommandTextField = new TextField("7");
        view.add(createLabelWithTooltip("ScheduledCommand=",
                Util.PINPADINI_VALUES_EXPLANATIONS.SCHEDULEDCOMMAND.getExplanation()), 4, 11);
        view.add(shedCommandTextField, 5, 11);
        view.add(createBoundWithTextFieldCheckBox(shedCommandTextField, "ScheduledCommand="), 6, 11);

        //29.ScheduledTime
        TextField shedTimeTextField = new TextField("23:50:30");
        view.add(createLabelWithTooltip("ScheduledTime=",
                Util.PINPADINI_VALUES_EXPLANATIONS.SCHEDULEDTIME.getExplanation()), 4, 12);
        view.add(shedTimeTextField, 5, 12);
        view.add(createBoundWithTextFieldCheckBox(shedTimeTextField, "ScheduledTime="), 6, 12);

        //30.TerminalID
        TextField terminalIdTextField = new TextField("12345678");
        view.add(createLabelWithTooltip("TerminalID=",
                Util.PINPADINI_VALUES_EXPLANATIONS.TERMINALID.getExplanation()), 4, 13);
        view.add(terminalIdTextField, 5, 13);
        view.add(createBoundWithTextFieldCheckBox(terminalIdTextField, "TerminalID="), 6, 13);

        //31.VivoLog
        view.add(createLabelWithTooltip("VivoLog=",
                Util.PINPADINI_VALUES_EXPLANATIONS.VIVOLOG.getExplanation()), 4, 14);
        view.add(createHorizSeparator(), 5, 14);
        view.add(create1or0CheckBox("VivoLog="), 6, 14);

        //32.WorkingDir
        TextField workDirTextField = new TextField("С:/sc552/data/");
        view.add(createLabelWithTooltip("WorkingDir=",
                Util.PINPADINI_VALUES_EXPLANATIONS.WORKINGDIR.getExplanation()), 4, 15);
        view.add(workDirTextField, 5, 15);
        view.add(createBoundWithTextFieldCheckBox(workDirTextField, "WorkingDir="), 6, 15);

        //33.ForceTopMost
        view.add(createLabelWithTooltip("ForceTopMost=",
                Util.PINPADINI_VALUES_EXPLANATIONS.FORCETOPMOST.getExplanation()), 4, 16);
        view.add(createHorizSeparator(), 5, 16);
        view.add(create1or0CheckBox("ForceTopMost="), 6, 16);

        //34.WaitPinpadSeconds
        TextField waitPinpadSecTextField = new TextField("3");
        view.add(createLabelWithTooltip("WaitPinpadSeconds=",
                Util.PINPADINI_VALUES_EXPLANATIONS.WAITPINPADSECONDS.getExplanation()), 4, 17);
        view.add(waitPinpadSecTextField,5,17);
        view.add(createBoundWithTextFieldCheckBox(waitPinpadSecTextField,"WaitPinpadSeconds="),6,17);

        //35.CommTimeouts
        TextField commTimeoutsTextField = new TextField("4294967295-0-0");
        view.add(createLabelWithTooltip("commTimeoutsTextField=",
                Util.PINPADINI_VALUES_EXPLANATIONS.COMMTIMEOUTS.getExplanation()),0,17);
        view.add(commTimeoutsTextField,1,17);
        view.add(createBoundWithTextFieldCheckBox(commTimeoutsTextField,"commTimeoutsTextField"),2,17);

        //Styling view
        for (int i = 0; i < view.getRowCount(); i++) {
            view.add(createVerticalSeparator(), 3, i);
        }
        view.setStyle(Util.BLACK_THEME);
        view.setPrefSize(650, 500);
        view.setAlignment(Pos.TOP_LEFT);
        view.setVgap(5);
        view.setHgap(10);
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

    private Separator createVerticalSeparator() {
        Separator vSeparator = new Separator(Orientation.VERTICAL);
        vSeparator.setPrefHeight(10);
        return vSeparator;
    }

    public Parent getView() {
        return this.view;
    }
}
