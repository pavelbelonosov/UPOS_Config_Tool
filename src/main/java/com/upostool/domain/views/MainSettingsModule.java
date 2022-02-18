package com.upostool.domain.views;

import com.upostool.ExtractFile;
import com.upostool.MainApplication;
import com.upostool.PinpadIniWriter;
import com.upostool.UserChoiceBox;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

import java.io.IOException;
import java.util.*;


public class MainSettingsModule {
    private GridPane view;
    private String upos;
    private String driver;
    private String[] uposVersions;
    private String[] driverVersions;
    // private File logfile;
    private Map<String, String> pinpadSettings;
    private String[] pinpadConnections;

    public MainSettingsModule() {
        this.view = new GridPane();
        this.upos = "";
        this.driver = "";
        this.pinpadSettings = new HashMap<>();
        this.uposVersions = new String[]{"32.02.05", "32.01.15", "31.00.18", "30.26.04", "30.01.05"};
        this.driverVersions = new String[]{"Verifone_5.0.5.2_B3", "Pax_2.28", "Ingenico_3.26"};
        this.pinpadConnections = new String[]{"USB/COM", "ETHERNET"};
        //this.logfile=new File(MainApplication.class.getResourceAsStream("logfile.txt"));
        setView();
    }

    private void setView() {

        //1.Creating upos version config components and layout
        Label version = new Label("UPOS VERSION:");
        Label uposExtractError = new Label("STATUS");
        uposExtractError.setDisable(true);
        uposExtractError.setTextFill(Color.TRANSPARENT);
        ChoiceBox uposItems = createBoundChoiceBox(uposVersions);
        uposItems.setOnAction((e) -> {
            upos = uposItems.getSelectionModel().getSelectedItem().toString();
        });
        view.add(version, 0, 0);
        view.add(uposExtractError, 1, 0);
        view.add(uposItems, 2, 0);

        //2.Creating driver version config components
        Label driverVersion = new Label("USB DRIVER:");
        Label driverExtractError = new Label("STATUS");
        driverExtractError.setDisable(true);
        driverExtractError.setTextFill(Color.TRANSPARENT);
        ChoiceBox driverItems = createBoundChoiceBox(driverVersions);
        driverItems.setOnAction((e) -> {
            driver = driverItems.getSelectionModel().getSelectedItem().toString();
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
        view.add(createBoundButton("APPLY", toWhereUnzip, uposExtractError, driverExtractError), 2, 3);

        //5.Creating pinpad config components
        //5.1 Creating PINPAD CONNECTION
        //5.1.1 USB/COM
        Label header = new Label("PINPAD SETTINGS");
        header.setTextFill(Color.BLACK);
        Label connection = new Label("CONNECTION:");
        ChoiceBox portNumber = createBoundChoiceBox(new String[]{"1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"});
        portNumber.setOnAction((e) -> {
            pinpadSettings.put("ComPort=", (portNumber.getSelectionModel().getSelectedIndex() + 1 + ""));
            pinpadSettings.put("Speed=", "115200");
        });
        portNumber.setDisable(true);
        view.add(header, 1, 4);
        view.add(connection, 0, 5);
        view.add(portNumber, 2, 5);

        //5.1.2 ETHERNET
        Label ethAdr = new Label("ENTER IP ADDRESS:");
        ethAdr.setDisable(true);
        view.add(ethAdr, 1, 6);
        TextField pinpadIpAddress = new TextField("000.000.000.000");
        pinpadIpAddress.setDisable(true);
        pinpadIpAddress.textProperty().addListener((change, oldValue, newValue) -> {
            pinpadSettings.put("PinpadIPAddr=", newValue);
            pinpadSettings.put("PinpadIPPort=", "8888");
        });
        view.add(pinpadIpAddress, 2, 6);

        ChoiceBox cBoxWithConnections = createBoundChoiceBox(pinpadConnections);
        cBoxWithConnections.setOnAction((e) -> {
            if (cBoxWithConnections.getSelectionModel().getSelectedIndex() == 0) {
                portNumber.setDisable(false);
                ethAdr.setDisable(true);
                pinpadIpAddress.setDisable(true);
            }
            if (cBoxWithConnections.getSelectionModel().getSelectedIndex() == 1) {
                portNumber.setDisable(true);
                ethAdr.setDisable(false);
                pinpadIpAddress.setDisable(false);
            }

        });
        view.add(cBoxWithConnections, 1, 5);

        //5.2 Creating GUI components
        Label guiLabel = new Label("GUI");
        Tooltip tooltip = new Tooltip();
        tooltip.setText("\nГрафическое отображение UPOS\n" +
                "на экране монитора\n");
        guiLabel.setTooltip(tooltip);

        CheckBox checkGuiBox = new CheckBox();
        checkGuiBox.selectedProperty().addListener((change, oldValue, newValue) -> {
            if (newValue) {
                pinpadSettings.put("ShowScreens=", "1");
            } else pinpadSettings.put("ShowScreens=", "0");
        });
        view.add(guiLabel, 0, 7);
        view.add(checkGuiBox, 2, 7);

        //6.Adding "Save" settings button
        Button savePinpadSettinds = new Button("SAVE");
        savePinpadSettinds.setOnAction(e -> {
            new PinpadIniWriter(toWhereUnzip.getText(), pinpadSettings).save();
        });
        view.add(savePinpadSettinds, 2, 8);

        //7.Adding "Log" Button
        view.add(createImageButton(), 2, 9);

        //8.Adding "Reset" button
        Button btn = new Button("RESET SETTINGS");
        btn.setOnAction(e -> {
            pinpadSettings.clear();
        });
        view.add(btn,0,9);

        //Styling layout
        view.setStyle("-fx-base: #373e43;\n" +
                "    -fx-control-inner-background: derive(-fx-base, 35%);\n" +
                "    -fx-control-inner-background-alt: -fx-control-inner-background ");
        Image image = new Image(MainApplication.class.getResourceAsStream("logo_transparent120x60.png"));
        view.add(new ImageView(image), 1, 9);
        view.setAlignment(Pos.BASELINE_CENTER);
        view.setVgap(20);
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

    private Button createImageButton() {
        Image image = new Image(MainApplication.class.getResourceAsStream("icon_button30x30.png"));
        BackgroundImage bImage = new BackgroundImage(image, null, null,
                null, null);
        Background background = new Background(bImage);
        Button btn = new Button("LOG");
        //btn.setPadding(new Insets(-1,-1,-1,-1));
        //btn.setPrefSize(30,30);
        btn.setBackground(background);
        return btn;
    }

    private Button createBoundButton(String name, TextField toWhereUnzip,
                                     Label uposExtractError, Label driverExtractError) {
        Button btn = new Button(name);
        btn.setOnAction((e) -> {
            try {
                new ExtractFile(toWhereUnzip.getText(), upos + ".zip").copyFile();
            } catch (IOException ex) {
                uposExtractError.setDisable(false);
                uposExtractError.setText("ERROR!");
            }

            try {
                new ExtractFile(toWhereUnzip.getText(), driver + ".zip").copyFile();
            } catch (IOException ex) {
                driverExtractError.setDisable(false);
                driverExtractError.setText("ERROR!");
            }
        });
        return btn;
    }


    public Parent getView() {
        return view;
    }
}
