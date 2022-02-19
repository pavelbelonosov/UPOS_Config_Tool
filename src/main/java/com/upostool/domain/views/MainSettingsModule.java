package com.upostool.domain.views;

import com.upostool.ExtractFile;
import com.upostool.MainApplication;
import com.upostool.PinpadIniWriter;
import com.upostool.Util;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class MainSettingsModule {
    private GridPane view;
    private String upos;
    private String driver;
    private List<String> logList;
    private Map<String, String> pinpadSettings;

    public MainSettingsModule() {
        this.view = new GridPane();
        this.upos = "";
        this.driver = "";
        this.pinpadSettings = new HashMap<>();
        this.logList = new ArrayList<>();
        setView();
    }

    private void setView() {

        //1.Creating upos version config components and layout
        Label version = new Label("UPOS_VERSION:");
        Label uposExtractError = new Label("STATUS");
        uposExtractError.setDisable(true);
        uposExtractError.setTextFill(Color.TRANSPARENT);
        ChoiceBox uposItems = createBoundChoiceBox(Util.UPOS_VERSIONS);
        uposItems.setOnAction((e) -> {
            this.upos = uposItems.getSelectionModel().getSelectedItem().toString();
        });
        view.add(version, 0, 0);
        view.add(uposExtractError, 1, 0);
        view.add(uposItems, 2, 0);

        //2.Creating driver version config components
        Label driverVersion = new Label("USB_DRIVER:");
        Label driverExtractError = new Label("STATUS");
        driverExtractError.setDisable(true);
        driverExtractError.setTextFill(Color.TRANSPARENT);
        ChoiceBox driverItems = createBoundChoiceBox(Util.DRIVERS_VERSIONS);
        driverItems.setOnAction((e) -> {
            this.driver = driverItems.getSelectionModel().getSelectedItem().toString();
        });
        view.add(driverVersion, 0, 1);
        view.add(driverExtractError, 1, 1);
        view.add(driverItems, 2, 1);

        //3.Creating extraction directory components
        Label directoryLabel = new Label("DIRECTORY: ");
        TextField toWhereUnzip = new TextField("C:/sc552/");
        view.add(directoryLabel, 0, 2);
        view.add(toWhereUnzip, 2, 2);

        //4.Adding "Apply" button
        view.add(createApplayButton("APPLY", toWhereUnzip, uposExtractError,
                driverExtractError), 2, 3);

        //5.Creating pinpad config components
        Label header = new Label("PINPAD_SETTINGS");
        view.add(createHorizSeparator(), 0, 4, 5, 1);
        view.add(createHorizSeparator(), 2, 4, 5, 1);
        view.add(header, 1, 4);

        //5.1 Creating PINPAD CONNECTION
        //5.1.1 USB/COM
        Label connection = new Label("CONNECTION:");
        ChoiceBox portNumber = createBoundChoiceBox(new String[]{"1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"});
        portNumber.setOnAction((e) -> {
            this.pinpadSettings.put("ComPort=", (portNumber.getSelectionModel().getSelectedIndex() + 1 + ""));
            this.pinpadSettings.put("Speed=", "115200");
        });
        portNumber.setDisable(true);
        view.add(connection, 0, 5);
        view.add(portNumber, 2, 5);

        //5.1.2 ETHERNET
        Label ethAdr = new Label("ENTER_IP_ADDRESS:");
        ethAdr.setDisable(true);
        view.add(ethAdr, 1, 6);
        TextField pinpadIpAddress = new TextField("0.0.0.0");
        pinpadIpAddress.setDisable(true);
        pinpadIpAddress.textProperty().addListener((change, oldValue, newValue) -> {
            this.pinpadSettings.put("PinpadIPAddr=", newValue);
            this.pinpadSettings.put("PinpadIPPort=", "8888");
        });
        view.add(pinpadIpAddress, 2, 6);
        view.add(createChoiceBoxWithConnections(portNumber,ethAdr,pinpadIpAddress), 1, 5);

        //5.2 Creating GUI components
        Label guiLabel = new Label("GUI");
        Tooltip tooltip = new Tooltip();
        tooltip.setText(Util.PINPADINI_VALUES_EXPLANATIONS.SHOWSCREENS.getExplanation());
        guiLabel.setTooltip(tooltip);

        CheckBox checkGuiBox = new CheckBox();
        checkGuiBox.selectedProperty().addListener((change, oldValue, newValue) -> {
            if (newValue) {
                this.pinpadSettings.put("ShowScreens=", "1");
            } else pinpadSettings.put("ShowScreens=", "0");
        });
        view.add(guiLabel, 0, 7);
        view.add(checkGuiBox, 2, 7);

        //6.Adding "Save" settings button
        view.add(createSaveButton(toWhereUnzip), 2, 9);

        //7.Creating "PrinterEnd" Setting components
        Label printerEndLabel = new Label("PRINTER_CUT_COMMAND:");
        Tooltip tooltipPrinterEnd = new Tooltip();
        tooltipPrinterEnd.setText(Util.PINPADINI_VALUES_EXPLANATIONS.PRINTEREND.getExplanation());
        printerEndLabel.setTooltip(tooltipPrinterEnd);
        ChoiceBox printerEndBox = createBoundChoiceBox(Util.PRINTEREND_VALUES);
        printerEndBox.setOnAction(e -> {
            this.pinpadSettings.put("PrinterEnd=", printerEndBox.getSelectionModel().getSelectedItem().toString());
        });
        view.add(printerEndLabel, 0, 8);
        view.add(printerEndBox, 2, 8);

        //8.Adding "Log" Button
        view.add(createLogButton(), 2, 10);

        //9.Adding "Reset" button
        view.add(createResetButton(), 1, 9);

        //Adding "Advanced settings" button
        view.add(createAdvancedSettingsButton(), 0, 9);

        //10.Styling layout
        ColumnConstraints constraints = new ColumnConstraints();
        constraints.setHgrow(Priority.ALWAYS);
        view.getColumnConstraints().add(constraints);

        view.setStyle(Util.BLACK_THEME);
        view.setPrefSize(485, 450);
        view.add(new ImageView(Util.LOGO_TRANSPARENT), 0, 10);
        view.setAlignment(Pos.BASELINE_CENTER);
        view.setVgap(15);
        view.setHgap(10);
        view.setPadding(new Insets(10, 10, 10, 10));

    }

    private ChoiceBox createBoundChoiceBox(String[] zipItems) {
        ChoiceBox cb = new ChoiceBox();
        //cb.setMinWidth(20);
        cb.setItems(FXCollections.observableArrayList(
                Arrays.asList(zipItems))
        );
        return cb;
    }

    private ChoiceBox createChoiceBoxWithConnections(ChoiceBox portNumber,Label ethAdr,TextField pinpadIpAddress){
        ChoiceBox box = createBoundChoiceBox(Util.PINPAD_CONNECTIONS);
        box.setOnAction((e) -> {
            if (box.getSelectionModel().getSelectedIndex() == 0) {
                portNumber.setDisable(false);
                ethAdr.setDisable(true);
                pinpadIpAddress.setDisable(true);
            }
            if (box.getSelectionModel().getSelectedIndex() == 1) {
                portNumber.setDisable(true);
                ethAdr.setDisable(false);
                pinpadIpAddress.setDisable(false);
            }
        });
        return box;
    }

    private Button createApplayButton(String name, TextField toWhereUnzip,
                                      Label uposExtractError, Label driverExtractError) {
        Button btn = new Button(name);
        btn.setOnAction((e) -> {
            if (!this.upos.equals("NULL")) {
                try {
                    logList.add(getLocatDateTime() + "EXTRACTING UPOS...");
                    new ExtractFile(toWhereUnzip.getText(), this.upos + ".zip").copyFile();
                } catch (IOException ex) {
                    logList.add(getLocatDateTime() + ex.getMessage());
                    uposExtractError.setDisable(false);
                    uposExtractError.setText("ERROR!");
                }
            }
            if (!this.driver.equals("NULL")) {
                try {
                    logList.add(getLocatDateTime() + "EXTRACTING DRIVER...");
                    new ExtractFile(toWhereUnzip.getText(), this.driver + ".zip").copyFile();
                } catch (IOException ex) {
                    logList.add(getLocatDateTime() + ex.getMessage());
                    driverExtractError.setDisable(false);
                    driverExtractError.setText("ERROR!");
                }
            }
        });
        return btn;
    }

    private Button createAdvancedSettingsButton() {
        Button btn = new Button("ADVANCED");
        btn.setOnAction(e -> {
            EnteringModule.openStage("UPOS ADVANCED SETTINGS",
                    new AdvancedSettingsModule(this.pinpadSettings).getView());
        });
        return btn;
    }

    private Button createSaveButton(TextField toWhereUnzip) {
        Button btn = new Button("SAVE PINPAD.INI");
        btn.setOnAction(e -> {
            try {
                logList.add(getLocatDateTime() + "SAVING PINPAD.INI...");
                new PinpadIniWriter(toWhereUnzip.getText(), pinpadSettings).save();
            } catch (FileNotFoundException ex) {
                logList.add(getLocatDateTime() + ex.getMessage());
            }
        });
        return btn;
    }

    private Button createResetButton() {
        Button btn = new Button("CLEAR CACHE");
        btn.setOnAction(e -> {
            logList.add(getLocatDateTime() + "RESETTING SETTINGS FOR PINPAD.INI...");
            this.pinpadSettings.clear();
            if (!this.pinpadSettings.isEmpty()) {
                logList.add(getLocatDateTime() + "ERROR RESET!");
            }
        });
        return btn;
    }

    private Button createLogButton() {
        /*BackgroundImage bImage = new BackgroundImage(Util.BUTTON_ICON_30x30, null, null,
                null, null);
        Background background = new Background(bImage);*/
        Button btn = new Button("LOG");
        //btn.setPadding(new Insets(-1,-1,-1,-1));
        //btn.setPrefSize(30,30);
        // btn.setBackground(background);
        btn.setOnAction(e -> {
            EnteringModule.openStage("LOG&INFO", new LogModule(this.logList).getView());
        });
        return btn;
    }

    private Separator createHorizSeparator() {
        Separator hSeparator = new Separator(Orientation.HORIZONTAL);
        hSeparator.setPrefHeight(10);
        return hSeparator;
    }

    private String getLocatDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
        String timeStamp = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(dtf) + ":    ";
        return timeStamp;
    }


    public Parent getView() {
        return view;
    }
}
