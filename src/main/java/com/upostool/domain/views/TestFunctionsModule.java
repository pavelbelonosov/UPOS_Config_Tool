package com.upostool.domain.views;

import com.upostool.Util;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TestFunctionsModule {
    private List<String> loglist;
    private HBox view;
    private String uposDir;
    private String cheque;
    private final int COMPONENT_WIDTH = 100;

    public TestFunctionsModule(List log, String uposDir) {
        this.loglist = log;
        this.view = new HBox();
        this.uposDir = uposDir + "/";
        this.cheque = uposDir + "/p";
        setView();
    }

    private void setView() {
        //1.Creating components
        //1.0 Text area for displaying sbkernell.log
        TextArea sbkernellArea = new TextArea();
        sbkernellArea.textProperty().addListener((change, oldValue, newValue) -> {
            sbkernellArea.setScrollTop(Double.MAX_VALUE);
        });
        sbkernellArea.setPrefSize(400, 70);
        sbkernellArea.setStyle("-fx-aligment: center;\n" +
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
                createFunctionButton(chequeArea, sbkernellArea, "MENU", "11", ""),
                createRemoteLoadButton(activationCodeTextField, sbkernellArea),
                activationCodeTextField,
                createFunctionButton(chequeArea, sbkernellArea, "DEL KEY", "22", ""),
                createFunctionButton(chequeArea, sbkernellArea, "X-REPORT", "9", "1"),
                createFunctionButton(chequeArea, sbkernellArea, "PURCHASE", "1", "100"),
                createFunctionButton(chequeArea, sbkernellArea, "REFUND", "3", "100"),
                createFunctionButton(chequeArea, sbkernellArea, "CLOSE DAY", "7", ""),
                createFunctionButton(chequeArea, sbkernellArea, "TEST PSDB", "47", "2"),
                createFunctionButton(chequeArea, sbkernellArea, "HELP INFO", "36", "")
        );

        //1.3 Service buttons vbox
        VBox serviceButtonsBox = new VBox();
        serviceButtonsBox.setFillWidth(true);
        TextField pingIpArea = new TextField("192.168.1.50");
        pingIpArea.setMaxWidth(COMPONENT_WIDTH);
        serviceButtonsBox.getChildren().addAll(
                createActionDllButton("REG DLL", ""),
                createActionDllButton("UNREG DLL", "/U"),
                createActionAgentButton("REG AGENT", "/reg"),
                createActionAgentButton("UNREG AGENT", "/unreg"),
                createActionAgentButton("RUN AGENT", "/run"),
                createCmdCommandButton("SERVICES.MSC"),
                createCmdCommandButton("DEVMGMT.MSC"),
                createCmdWithStdoutButton("IPCONFIG","ipconfig",chequeArea,pingIpArea),
                createListenPortCheckButton(chequeArea),
                createCmdWithStdoutButton("PING", "ping", chequeArea, pingIpArea),
                pingIpArea
        );

        //1.4 Text area vbox
        VBox textAreas = new VBox();
        textAreas.setFillWidth(true);
        textAreas.getChildren().addAll(
                sbkernellArea,
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
        view.setStyle(Util.BLACK_THEME);
        view.setPrefSize(620, 550);
        view.setSpacing(10);
        view.setPadding(new Insets(10, 10, 10, 10));
    }

    private Button createFunctionButton(TextArea chequeArea, TextArea sbkernellArea, String buttonName,
                                        String loadparmFirstParameter, String loadparmSecondParameter) {
        Button btn = new Button(buttonName);
        btn.setMinWidth(COMPONENT_WIDTH);
        btn.setOnAction(e -> {
            try {
                deleteCheque();
                loglist.add(getLocatDateTime() + "PROCESSING " + buttonName + "...");
                function("loadparm.exe", loadparmFirstParameter, loadparmSecondParameter);
                loglist.add(getLocatDateTime() + "LOADPARM " + loadparmFirstParameter + " " + loadparmSecondParameter);
            } catch (Exception ex) {
                loglist.add(getLocatDateTime() + ex.getMessage());
            }
            try {
                loglist.add(getLocatDateTime() + "SB_PILOT " + loadparmFirstParameter + " " + loadparmSecondParameter);
                function("sb_pilot.exe", loadparmFirstParameter, loadparmSecondParameter);
            } catch (Exception ex) {
                loglist.add(getLocatDateTime() + ex.getMessage());
            }
            loadCheque(chequeArea);
            loadSbkernellLog(sbkernellArea);
        });
        return btn;
    }

    private Button createRemoteLoadButton(TextField activationCode, TextArea sbkernellArea) {
        Button btn = new Button("REMOTE LOAD");
        btn.setMinWidth(COMPONENT_WIDTH);
        btn.setOnAction(e -> {
            try {
                deleteCheque();
                loglist.add(getLocatDateTime() + "PROCESSING REMOTE LOAD...");
                remoteLoadFunction(activationCode.getText());
                loglist.add(getLocatDateTime() + "LOADPARM " + 21 + " " + activationCode.getText());
            } catch (Exception ex) {
                loglist.add(getLocatDateTime() + ex.getMessage());
            }
            loadSbkernellLog(sbkernellArea);
        });
        return btn;
    }

    private void function(String module, String firstParameter, String secondParameter) throws IOException, InterruptedException {
        Process process;
        if (secondParameter.equals("")) {
            process = new ProcessBuilder(uposDir + module, firstParameter).start();
            process.waitFor();
            return;
        }
        process = new ProcessBuilder(uposDir + module, firstParameter, secondParameter).start();
        process.waitFor();
    }

    private void remoteLoadFunction(String activationCode) throws IOException {
        Process process = new ProcessBuilder(uposDir + "loadparm.exe",
                "21", activationCode).start();

    }

    private Button createActionDllButton(String name, String arg) {
        Button b = new Button(name);
        b.setMinWidth(COMPONENT_WIDTH);
        b.setOnAction(e -> {
            actionDLL(arg);
        });
        return b;
    }

    private Button createActionAgentButton(String name, String arg) {
        Button b = new Button(name);
        b.setMinWidth(COMPONENT_WIDTH);
        b.setOnAction(e -> {
            actionAgent(arg);
        });
        return b;
    }

    private Button createCmdWithStdoutButton(String name, String command, TextArea chequeArea, TextField ip) {
        Button b = new Button(name);
        b.setMinWidth(COMPONENT_WIDTH);
        b.setOnAction(e -> {
            try {
                loglist.add(getLocatDateTime() + name + " " + ip.getText() + "...");
                Process p;
                switch (command) {
                    case "ping":
                        p = new ProcessBuilder("cmd.exe", "/c", command, ip.getText()).start();
                        break;
                    case "ipconfig":
                        p = new ProcessBuilder("cmd.exe", "/c", command).start();
                        break;
                    default:
                        p = new ProcessBuilder("cmd.exe", "/c").start();
                }
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(p.getInputStream(), "cp866"));
                StringBuilder commandOutput = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    commandOutput.append(line);
                    commandOutput.append(System.getProperty("line.separator"));
                }
                chequeArea.setText(commandOutput.toString());
            } catch (IOException ex) {
                loglist.add(getLocatDateTime() + ex.getMessage());
            }
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

    private Button createCmdCommandButton(String command) {
        Button b = new Button(command);
        b.setMinWidth(COMPONENT_WIDTH);
        b.setOnAction(e -> {
            cmdCommand(command);
        });
        return b;
    }

    private boolean isPortAvailable(String host, int port) {
        try (Socket s = new Socket(host, port)) {
            return true;
        } catch (IOException ex) {
            loglist.add(getLocatDateTime() + ex.getMessage());
            return false;
        }
    }

    private void actionDLL(String unreg) {
        try {
            loglist.add(getLocatDateTime() + "TRYING REGISTER SBRF.DLL&SBRFCOM.DLL...");
            if (unreg == "") {
                Process p = new ProcessBuilder("cmd.exe", "/c", "regsvr32.exe",
                        uposDir + "sbrf.dll", uposDir + "sbrfcom.dll").start();
            } else {
                loglist.add(getLocatDateTime() + "TRYING UNREGISTER SBRF.DLL&SBRFCOM.DLL...");
                Process p = new ProcessBuilder("cmd.exe", "/c", "regsvr32.exe", unreg,
                        uposDir + "sbrf.dll", uposDir + "sbrfcom.dll").start();
            }
        } catch (IOException e) {
            loglist.add(getLocatDateTime() + e.getMessage());
        }
    }

    private void actionAgent(String argument) {
        try {
            Process p = new ProcessBuilder(uposDir + "agent.exe", argument).start();
        } catch (IOException e) {
            loglist.add(getLocatDateTime() + e.getMessage());
        }
    }

    private void cmdCommand(String command) {
        try {
            loglist.add(getLocatDateTime() + "EXECUTING CMD COMMAND " + command + "...");
            Process p = new ProcessBuilder("cmd.exe", "/c", command).start();
        } catch (IOException e) {
            loglist.add(getLocatDateTime() + e.getMessage());
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
                    loglist.add(getLocatDateTime() + ex.getMessage());
                }
            }
        }
        view.setDisable(false);
    }

    private void loadSbkernellLog(TextArea sbkernellArea) {
        String sbkernellLog = uposDir + "/sbkernel" +
                getLocatDateTime().charAt(2) + getLocatDateTime().charAt(3) +
                getLocatDateTime().charAt(5) + getLocatDateTime().charAt(6) + ".log";
        //long startTime = System.currentTimeMillis();
        // long timeWait = startTime + 2000;
        StringBuilder sbkernellContent = new StringBuilder();
        view.setDisable(true);
        //while (timeWait >= System.currentTimeMillis()) {
        try {
            Files.lines(Paths.get(sbkernellLog))
                    .forEach(line -> {
                        sbkernellContent.append(line + "\n");
                    });
        } catch (IOException e) {
            loglist.add(getLocatDateTime() + e.getMessage());
        }
        sbkernellArea.setText(sbkernellContent.toString());
        sbkernellArea.appendText("");
        //}
        view.setDisable(false);
    }

    private void deleteCheque() throws IOException {
        if (Files.exists(Paths.get(cheque))) {
            Files.delete(Paths.get(cheque));
            if (!Files.exists(Paths.get(cheque))) {
                loglist.add(getLocatDateTime() + "FILE P DELETED... ");
            }
        }

    }

    private Separator createHorizSeparator() {
        Separator hSeparator = new Separator(Orientation.HORIZONTAL);
        hSeparator.setPrefHeight(10);
        return hSeparator;
    }

    private String getLocatDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
        String timeStamp = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(dtf) + ":  ";
        return timeStamp;
    }

    Parent getView() {
        return view;
    }
}
