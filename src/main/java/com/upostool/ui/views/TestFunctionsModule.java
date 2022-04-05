package com.upostool.ui.views;

import com.upostool.domain.*;
import com.upostool.util.Cons;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TestFunctionsModule {

    private ModuleSbPilotProcess sbPilotProcess;
    private ModuleLoadParmProcess loadParmProcess;
    private CMDengineerHandler cmdEngineerHandler;
    private FileUPOSlogHandler fileUPOSlogHandler;
    private UPOSlog uposLog;
    private HBox view;
    private String uposDir;
    private String cheque;
    private final int COMPONENT_WIDTH = 100;

    public TestFunctionsModule(AppLog log, String uposDir) {
        this.view = new HBox();
        this.uposDir = uposDir + "/";
        this.cheque = uposDir + "/p";
        this.loadParmProcess = new ModuleLoadParmProcess(uposDir, log);
        this.sbPilotProcess = new ModuleSbPilotProcess(uposDir, log);
        this.cmdEngineerHandler = new CMDengineerHandler(uposDir, log);
        this.uposLog = new UPOSlog();
        this.fileUPOSlogHandler = new FileUPOSlogHandler(uposDir, log, uposLog);
        setView();
    }

    private void setView() {
        //1.Creating components
        //1.0 Text area for displaying sbkernell.log
        TextArea uposLogArea = new TextArea();
        uposLogArea.textProperty().addListener((change, oldValue, newValue) -> {
            uposLogArea.setScrollTop(Double.MAX_VALUE);
        });
        uposLogArea.setPrefSize(400, 70);
        uposLogArea.setStyle("-fx-aligment: center;\n" +
                "-fx-font-size: 10;");

        //1.1 Text area for displaying file p
        TextArea chequeArea = new TextArea();
        chequeArea.setPrefSize(400, 460);
        chequeArea.setStyle("-fx-aligment: center;\n" +
                "-fx-font-size: 11;");

        //1.2 Operation buttons vbox
        VBox functionButtonBox = new VBox();
        functionButtonBox.setFillWidth(true);
        TextField activationCodeTextField = new TextField("Activation code");
        activationCodeTextField.setMaxWidth(COMPONENT_WIDTH);
        functionButtonBox.getChildren().addAll(
                createFunctionButton(chequeArea, uposLogArea, activationCodeTextField,
                        ModuleLoadParmProcess.Operation.MENU),
                createFunctionButton(chequeArea, uposLogArea, activationCodeTextField,
                        ModuleLoadParmProcess.Operation.REMOTE_LOAD),
                activationCodeTextField,
                createFunctionButton(chequeArea, uposLogArea, activationCodeTextField,
                        ModuleLoadParmProcess.Operation.DEL_KEY),
                createFunctionButton(chequeArea, uposLogArea, activationCodeTextField,
                        ModuleLoadParmProcess.Operation.XREPORT),
                createFunctionButton(chequeArea, uposLogArea, activationCodeTextField,
                        ModuleLoadParmProcess.Operation.PURCHASE),
                createFunctionButton(chequeArea, uposLogArea, activationCodeTextField,
                        ModuleLoadParmProcess.Operation.REFUND),
                createFunctionButton(chequeArea, uposLogArea, activationCodeTextField,
                        ModuleLoadParmProcess.Operation.CLOSE_DAY),
                createFunctionButton(chequeArea, uposLogArea, activationCodeTextField,
                        ModuleLoadParmProcess.Operation.TEST_PSDB),
                createFunctionButton(chequeArea, uposLogArea, activationCodeTextField,
                        ModuleLoadParmProcess.Operation.HELP_INFO)
        );

        //1.3 Service buttons vbox
        VBox serviceButtonsBox = new VBox();
        serviceButtonsBox.setFillWidth(true);
        TextField pingIpArea = new TextField("192.168.1.50");
        pingIpArea.setMaxWidth(COMPONENT_WIDTH);
        serviceButtonsBox.getChildren().addAll(
                createCmdButton("REG DLL", CMDengineerHandler.Command.REG_DLL),
                createCmdButton("UNREG DLL", CMDengineerHandler.Command.UNREG_DLL),
                createCmdButton(CMDengineerHandler.Command.REG_AGENT, chequeArea, pingIpArea),
                createCmdButton(CMDengineerHandler.Command.UNREG_AGENT, chequeArea, pingIpArea),
                createCmdButton("RUN AGENT", CMDengineerHandler.Command.RUN_AGENT),
                createCmdButton(CMDengineerHandler.Command.SERVICES),
                createCmdButton(CMDengineerHandler.Command.DEVMGMT),
                createCmdButton(CMDengineerHandler.Command.IPCONFIG, chequeArea, pingIpArea),
                createListenPortCheckButton(chequeArea),
                createCmdButton(CMDengineerHandler.Command.PING, chequeArea, pingIpArea),
                pingIpArea
        );

        //1.4 Text area vbox
        VBox textAreas = new VBox();
        textAreas.setFillWidth(true);
        textAreas.getChildren().addAll(
                uposLogArea,
                createHorizSeparator(),
                chequeArea
        );

        //2.Creating view hbox layout
        view.getChildren().add(functionButtonBox);
        view.getChildren().add(textAreas);
        view.getChildren().add(serviceButtonsBox);

        //3.Styling
        functionButtonBox.setSpacing(5);
        serviceButtonsBox.setSpacing(5);
        view.setStyle(Cons.BLACK_THEME);
        view.setPrefSize(620, 550);
        view.setSpacing(10);
        view.setPadding(new Insets(10, 10, 10, 10));
    }

    private Button createFunctionButton(TextArea chequeArea, TextArea sbkernellArea,
                                        TextField activationCode, ModuleLoadParmProcess.Operation operation) {
        Button btn = new Button(operation.toString());
        btn.setMinWidth(COMPONENT_WIDTH);
        btn.setOnAction(e -> {
            try {
                deleteCheque();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            loadParmProcess.execute(operation, activationCode.getText());
            sbPilotProcess.execute(operation, activationCode.getText());

            loadCheque(chequeArea);
            loadUPOSLog("sbkernel", sbkernellArea);
            loadUPOSLog("upos", sbkernellArea);
        });
        return btn;
    }

    private Button createCmdButton(CMDengineerHandler.Command command) {
        Button b = new Button(command.toString());
        b.setMinWidth(COMPONENT_WIDTH);
        b.setOnAction(e -> {
            cmdEngineerHandler.executeCMD(command, "");
        });
        return b;
    }

    private Button createCmdButton(String name, CMDengineerHandler.Command command) {
        Button b = new Button(name);
        b.setMinWidth(COMPONENT_WIDTH);
        b.setOnAction(e -> {
            cmdEngineerHandler.executeCMD(command, "");
        });
        return b;
    }

    private Button createCmdButton(CMDengineerHandler.Command command, TextArea chequeArea, TextField ip) {
        Button b = new Button(command.toString());
        b.setMinWidth(COMPONENT_WIDTH);
        b.setOnAction(e -> {
            cmdEngineerHandler.executeCMD(command, ip.getText());
            chequeArea.setText(cmdEngineerHandler.getStdOut());
        });
        return b;
    }


    private Button createListenPortCheckButton(TextArea chequeArea) {
        Button b = new Button("CHECK PORTS");
        b.setMinWidth(COMPONENT_WIDTH);
        b.setOnAction(e -> {
            StringBuilder builder = new StringBuilder();
            String mainServer = "194.54.14.89";
            String agentServer = "185.157.96.41";
            if (isPortAvailable(mainServer, 650)) {
                builder.append(mainServer + " ON 650 AVAILABLE\n");
            } else {
                builder.append(mainServer + " ON 650 NOT AVAILABLE\n");
            }
            if (isPortAvailable(mainServer, 666)) {
                builder.append(mainServer + " ON 666 AVAILABLE\n");
            } else {
                builder.append(mainServer + " ON 666 NOT AVAILABLE\n");
            }
            if (isPortAvailable(mainServer, 670)) {
                builder.append(mainServer + " ON 670 AVAILABLE\n");
            } else {
                builder.append(mainServer + " ON 670 NOT AVAILABLE\n");
            }
            if (isPortAvailable(mainServer, 690)) {
                builder.append(mainServer + " ON 690 AVAILABLE\n");
            } else {
                builder.append(mainServer + " ON 690 NOT AVAILABLE\n");
            }
            if (isPortAvailable(agentServer, 80)) {
                builder.append("\nAGENT\n"
                        + agentServer + " ON 80 AVAILABLE");
            } else {
                builder.append("AGENT\n"
                        + agentServer + " ON 80 NOT AVAILABLE");
            }
            chequeArea.setText(builder.toString());
        });
        return b;
    }


    private boolean isPortAvailable(String host, int port) {
        try (Socket s = new Socket(host, port)) {
            return true;
        } catch (IOException ex) {
            //loglist.add(getLocatDateTime() + ex.getMessage());
            return false;
        }
    }

    private void loadCheque(TextArea chequeArea) {
        long startTime = System.currentTimeMillis();
        long timeWait = startTime + 2000;
        view.setDisable(true);
        while (timeWait >= System.currentTimeMillis()) {
            if (Files.exists(Paths.get(cheque))) {
                try (FileInputStream fin = new FileInputStream(cheque)) {
                    byte[] buffer = new byte[fin.available()];
                    fin.read(buffer, 0, fin.available());
                    chequeArea.setText(new String(buffer, "cp866"));
                } catch (Exception ex) {
                    // loglist.add(getLocatDateTime() + ex.getMessage());
                }
            }
        }
        view.setDisable(false);
    }

    private void loadUPOSLog(String log, TextArea uposLogArea) {
        view.setDisable(true);
        uposLog.setFullName(log);
        uposLogArea.setText(fileUPOSlogHandler.getContent());
        uposLogArea.appendText("\n**************ERRORS***************\n");
        uposLogArea.appendText(fileUPOSlogHandler.findErrors());
        view.setDisable(false);
    }

    private void deleteCheque() throws IOException {
        if (Files.exists(Paths.get(cheque))) {
            Files.delete(Paths.get(cheque));
            if (!Files.exists(Paths.get(cheque))) {
                //loglist.add(getLocatDateTime() + "FILE P DELETED... ");
            }
        }

    }

    private Separator createHorizSeparator() {
        Separator hSeparator = new Separator(Orientation.HORIZONTAL);
        hSeparator.setPrefHeight(10);
        return hSeparator;
    }

    Parent getView() {
        return view;
    }
}
