package com.upostool.ui;

import com.upostool.dao.ChequeDAO;
import com.upostool.dao.SettingDAO;
import com.upostool.domain.Setting;
import com.upostool.domain.FileZip;
import com.upostool.util.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.*;


public class MainSettingsModule {
    private GridPane view;
    private SettingDAO settingDAO;
    private ChequeDAO chequeDAO;

    public MainSettingsModule(SettingDAO settingDAO, ChequeDAO chequeDAO) {
        this.view = new GridPane();
        this.settingDAO = settingDAO;
        this.chequeDAO = chequeDAO;
        setView();
    }

    private void setView() {

        //1.Creating UPOS Version config components and layout
        Label version = new Label("UPOS_VERSION:");
        Label uposExtractError = new Label("STATUS");
        uposExtractError.setDisable(true);
        uposExtractError.setTextFill(Color.TRANSPARENT);
        ChoiceBox uposItems = createBoundChoiceBox(Cons.UPOS_VERSIONS);
        view.add(version, 0, 0);
        view.add(uposExtractError, 1, 0);
        view.add(uposItems, 2, 0);

        //2.Creating Driver Version config components
        Label driverVersion = new Label("USB_DRIVER:");
        Label driverExtractError = new Label("STATUS");
        driverExtractError.setDisable(true);
        driverExtractError.setTextFill(Color.TRANSPARENT);
        ChoiceBox driverItems = createBoundChoiceBox(Cons.DRIVERS_VERSIONS);

        view.add(driverVersion, 0, 1);
        view.add(driverExtractError, 1, 1);
        view.add(driverItems, 2, 1);

        //3.Creating extraction directory components
        Label directoryLabel = new Label("DIRECTORY: ");
        TextField toWhereUnzip = new TextField("C:/sc552/");
        toWhereUnzip.setPrefWidth(10);
        view.add(directoryLabel, 0, 2);
        view.add(toWhereUnzip, 1, 2);

        //4.Adding "Extract" button
        view.add(createExtractButton(toWhereUnzip, uposItems, driverItems,
                uposExtractError, driverExtractError), 2, 2);

        //5.Creating Pinpad config components
        Label header = new Label("PINPAD_INI_SETTINGS");
        view.add(createHorizSeparator(), 0, 3, 5, 1);
        view.add(createHorizSeparator(), 2, 3, 5, 1);
        view.add(header, 1, 3);

        //5.1 Creating PINPAD CONNECTION
        //5.1.1 USB/COM
        Label connection = new Label("CONNECTION:");
        ChoiceBox portNumber = createPortNumberChoiceBox();
        portNumber.setDisable(true);
        view.add(connection, 0, 5);
        view.add(portNumber, 2, 5);

        //5.1.2 ETHERNET
        Label ethAdr = new Label("IP_ADDRESS:");
        ethAdr.setDisable(true);
        view.add(ethAdr, 1, 6);
        TextField pinpadIpAddress = createPinpadIpAddressTextField();
        pinpadIpAddress.setPrefWidth(10);
        pinpadIpAddress.setDisable(true);
        view.add(pinpadIpAddress, 2, 6);
        view.add(createChoiceBoxWithConnections(portNumber, ethAdr, pinpadIpAddress), 1, 5);

        //5.2 Creating GUI components
        Label guiLabel = new Label("GUI");
        Tooltip tooltip = new Tooltip();
        tooltip.setText(SETTINGS_VALUES_EXPLANATIONS.SHOWSCREENS.getExplanation());
        guiLabel.setTooltip(tooltip);
        view.add(guiLabel, 0, 7);
        view.add(createGuiCheckBox(), 2, 7);

        //6.Adding "Save" settings button
        view.add(createSaveButton(toWhereUnzip), 2, 4);

        //7.Creating "PrinterEnd" Setting components
        Label printerEndLabel = new Label("PRINTER_CUT:");
        Tooltip tooltipPrinterEnd = new Tooltip();
        tooltipPrinterEnd.setText(SETTINGS_VALUES_EXPLANATIONS.PRINTEREND.getExplanation());
        printerEndLabel.setTooltip(tooltipPrinterEnd);
        ChoiceBox printerEndBox = createPrinterEndChoiceBox();
        view.add(printerEndLabel, 0, 8);
        view.add(printerEndBox, 2, 8);

        //8.Adding "Log" Button
        view.add(createLogButton(), 1, 9);

        //9.Adding "Reset" button
        view.add(createResetButton(), 1, 4);

        //10.Adding "Advanced settings" button
        view.add(createAdvancedSettingsButton(), 0, 9);

        //11.Adding "Load into cache" button
        view.add(createLoadFromPinpadButton(toWhereUnzip), 0, 4);

        //12.Adding "Test function" button
        view.add(createTestFunctionsButton(toWhereUnzip), 2, 9);

        //13.Styling layout
        view.setStyle(Cons.BLACK_THEME);
        view.setPrefSize(400, 350);
        view.add(new ImageView(Cons.LOGO_TRANSPARENT), 1, 10);
        view.setAlignment(Pos.CENTER);
        view.setVgap(10);
        view.setHgap(10);
        view.setPadding(new Insets(10, 0, 10, 10));

    }

    private ChoiceBox createBoundChoiceBox(String[] zipItems) {
        ChoiceBox cb = new ChoiceBox();
        cb.setPrefWidth(70);
        cb.setItems(FXCollections.observableArrayList(
                Arrays.asList(zipItems))
        );
        return cb;
    }

    private ChoiceBox createChoiceBoxWithConnections(ChoiceBox portNumber, Label ethAdr,
                                                     TextField pinpadIpAddress) {
        ChoiceBox box = createBoundChoiceBox(Cons.PINPAD_CONNECTIONS);
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

    private CheckBox createGuiCheckBox() {
        CheckBox cb = new CheckBox();
        cb.selectedProperty().addListener((change, oldValue, newValue) -> {
            String settingName = "ShowScreens";
            if (newValue) {
                settingDAO.update(new Setting(settingName, "1"));
            } else {
                settingDAO.update(new Setting(settingName, "0"));
            }
        });
        return cb;
    }

    private ChoiceBox createPortNumberChoiceBox() {
        ChoiceBox cb = createBoundChoiceBox(Cons.COMPORTS);
        cb.setOnAction((e) -> {
            String setting = "ComPort";
            String bundledSetting = "Speed";
            String value = cb.getSelectionModel().getSelectedIndex() + 1 + "";
            settingDAO.removeByName("PinpadIPAddr");
            settingDAO.removeByName("PinpadIPPort");
            settingDAO.update(new Setting(setting, value));
            settingDAO.update(new Setting(bundledSetting, "115200"));
        });
        return cb;
    }

    private TextField createPinpadIpAddressTextField() {
        TextField textField = new TextField("0.0.0.0");
        String setting = "PinpadIPAddr";
        String bundledSetting = "PinpadIPPort";
        textField.textProperty().addListener((change, oldValue, newValue) -> {
            String value = newValue.trim().replaceAll("\\s|[a-zA-Z]", "");
            settingDAO.removeByName("ComPort");
            settingDAO.removeByName("Speed");
            settingDAO.update(new Setting(setting, value));
            settingDAO.update(new Setting(bundledSetting, "8888"));
        });
        return textField;
    }

    private ChoiceBox createPrinterEndChoiceBox() {
        ChoiceBox cb = createBoundChoiceBox(Cons.PRINTEREND_VALUES);
        cb.setOnAction(e -> {
            String settingName = "PrinterEnd";
            String value = cb.getSelectionModel().getSelectedItem().toString();
            settingDAO.update(new Setting(settingName, value));
        });
        return cb;
    }

    private Button createExtractButton(TextField toWhereUnzip, ChoiceBox uposItems, ChoiceBox driverItems,
                                       Label uposExtractError, Label driverExtractError) {
        Button btn = new Button("EXTRACT");
        btn.setOnAction((e) -> {
            if (uposItems.getSelectionModel().getSelectedItem() != null) {
                try {
                    settingDAO.getLog().addRecord("EXTRACTING UPOS...");
                    new FileZip(toWhereUnzip.getText(),
                            uposItems.getSelectionModel().
                                    getSelectedItem().toString() + ".zip", settingDAO.getLog()).copyZip();
                } catch (IOException ex) {
                    settingDAO.getLog().addRecord(ex.getMessage());
                    uposExtractError.setDisable(false);
                }
            }
            if (driverItems.getSelectionModel().getSelectedItem() != null) {
                try {
                    settingDAO.getLog().addRecord("EXTRACTING DRIVER...");
                    new FileZip(toWhereUnzip.getText(),
                            driverItems.getSelectionModel().
                                    getSelectedItem().toString() + ".zip", settingDAO.getLog()).copyZip();
                } catch (IOException ex) {
                    settingDAO.getLog().addRecord(ex.getMessage());
                    driverExtractError.setDisable(false);
                }
            }
        });
        return btn;
    }

    private Button createAdvancedSettingsButton() {
        Button btn = new Button("ADVANCED");
        btn.setOnAction(e -> {
            EnteringModule.openStage("UPOS ADVANCED SETTINGS",
                    new AdvancedSettingsModule(settingDAO).getView());
        });
        return btn;
    }

    private Button createSaveButton(TextField toWhereUnzip) {
        Button btn = new Button("SAVE FILE");
        btn.setOnAction(e -> {
            settingDAO.save(toWhereUnzip.getText());
        });
        return btn;
    }

    private Button createLoadFromPinpadButton(TextField toWhereUnzip) {
        Button btn = new Button("LOAD FROM FILE");
        btn.setOnAction(e -> {
            settingDAO.findAll(toWhereUnzip.getText());
        });
        return btn;
    }

    private Button createResetButton() {
        Button btn = new Button("CLEAR CACHE");
        btn.setOnAction(e -> {
            settingDAO.deleteAll();
        });
        return btn;
    }

    private Button createLogButton() {
        Button btn = new Button("LOG&INFO");
        btn.setOnAction(e -> {
            EnteringModule.openStage("LOG&INFO",
                    new LogModule(settingDAO.getLog()).getView());
        });
        return btn;
    }

    private Button createTestFunctionsButton(TextField toWhereUnzip) {
        Button btn = new Button("FUNCTIONS");
        btn.setOnAction(e -> {
            EnteringModule.openStage("UPOS TEST",
                    new TestFunctionsModule(settingDAO.getLog(), toWhereUnzip.getText(), chequeDAO).getView());
        });
        return btn;
    }

    private Separator createHorizSeparator() {
        Separator hSeparator = new Separator(Orientation.HORIZONTAL);
        hSeparator.setPrefHeight(10);
        return hSeparator;
    }

    protected Parent getView() {
        return view;
    }
}
