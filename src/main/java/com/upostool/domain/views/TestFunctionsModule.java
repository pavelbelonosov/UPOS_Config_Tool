package com.upostool.domain.views;

import com.upostool.Util;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class TestFunctionsModule {
    private List<String> loglist;
    private HBox view;
    private String uposDir;
    private String cheque;
    private final int COMPONENT_WIDTH = 100;

    public TestFunctionsModule(){
        this.loglist = new ArrayList<>();
        this.view = new HBox();
        this.uposDir = "C:/sc552/";
        this.cheque = uposDir + "/p";
        setView();
    }

    public TestFunctionsModule(List log, String uposDir) {
        this.loglist = log;
        this.view = new HBox();
        this.uposDir = uposDir + "/";
        this.cheque = uposDir + "/p";
        setView();
    }

    private void setView() {
        //1.Creating components
        //1.1 Cheque area for displaying file p
        TextArea chequeArea = new TextArea();
        chequeArea.setPrefSize(400, 270);
        chequeArea.setStyle("-fx-aligment: center");

        //1.2 Operation buttons vbox
        VBox functionButtonBox = new VBox();
        functionButtonBox.setFillWidth(true);
        TextField actCodeTextField = new TextField("Activation code");
        actCodeTextField.setMaxWidth(COMPONENT_WIDTH);
        functionButtonBox.getChildren().addAll(
                createRemoteLoadButton(actCodeTextField),
                actCodeTextField,
                createFunctionButton(chequeArea, "DEL KEY", "22", ""),
                createFunctionButton(chequeArea, "X-REPORT", "9", "1"),
                createFunctionButton(chequeArea, "PURCHASE", "1", "100"),
                createFunctionButton(chequeArea, "REFUND", "3", "100"),
                createFunctionButton(chequeArea, "CLOSE DAY", "7", ""),
                createFunctionButton(chequeArea, "TEST PSDB", "47", "2"),
                createFunctionButton(chequeArea, "HELP INFO", "36", "")
        );

        //1.3 Service buttons vbox
        VBox serviceButtonsBox = new VBox();
        serviceButtonsBox.setFillWidth(true);
        serviceButtonsBox.getChildren().addAll(
                createActionDllButton("REG DLL", ""),
                createActionDllButton("UNREG DLL", "/U"),
                createActionAgentButton("REG AGENT", "/reg"),
                createActionAgentButton("UNREG AGENT", "/unreg"),
                createActionAgentButton("RUN AGENT", "/run"),
                createCmdCommandButton("SERVICES.MSC"),
                createCmdCommandButton("DEVMGMT.MSC"),
                createIpConfigButton(chequeArea),
                createListenPortCheckButton(chequeArea)
        );

        //2.Creating view hbox layout
        view.getChildren().add(functionButtonBox);
        view.getChildren().add(chequeArea);
        view.getChildren().add(serviceButtonsBox);

        //3.Styling
        functionButtonBox.setSpacing(5);
        serviceButtonsBox.setSpacing(5);
        view.setStyle(Util.BLACK_THEME);
        view.setPrefSize(620, 550);
        view.setSpacing(10);
        view.setPadding(new Insets(10, 10, 10, 10));
    }

    private Button createFunctionButton(TextArea chequeArea, String buttonName, String loadparmFirstParameter,
                                        String loadparmSecondParameter) {
        Button btn = new Button(buttonName);
        btn.setMinWidth(COMPONENT_WIDTH);
        btn.setOnAction(e -> {
            try {
                deleteCheque();
                loglist.add(getLocatDateTime() + "PROCESSING " + buttonName + "...");
                function(loadparmFirstParameter, loadparmSecondParameter);
                //loglist.add(getLocatDateTime() + "LOADPARM " + loadparmFirstParameter + " " + loadparmSecondParameter);
            } catch (Exception ex) {
                loglist.add(getLocatDateTime() + ex.getMessage());
            }
            loadCheque(chequeArea);

        });
        return btn;
    }

    private Button createRemoteLoadButton(TextField activationCode) {
        Button btn = new Button("REMOTE LOAD");
        btn.setMinWidth(COMPONENT_WIDTH);
        btn.setOnAction(e -> {
            try {
                deleteCheque();
                loglist.add(getLocatDateTime() + "PROCESSING REMOTE LOAD...");
                remoteLoadFunction(activationCode.getText());
                //loglist.add(getLocatDateTime() + "LOADPARM " + 21 + " " + activationCode.getText());
            } catch (Exception ex) {
                loglist.add(getLocatDateTime() + ex.getMessage());
            }
        });
        return btn;
    }

    private void function(String firstParameter, String secondParameter) throws IOException, InterruptedException {
        Process process;
        if (secondParameter.equals("")) {
            process = new ProcessBuilder(uposDir + "loadparm.exe", firstParameter).start();
            process.waitFor();
            return;
        }
        process = new ProcessBuilder(uposDir + "loadparm.exe", firstParameter, secondParameter).start();
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

    private Button createIpConfigButton(TextArea chequeArea) {
        Button b = new Button("IPCONFIG");
        b.setMinWidth(COMPONENT_WIDTH);
        b.setOnAction(e -> {
            try {
                loglist.add(getLocatDateTime() + "GETTING IPCONFIG...");
                Process ipconfig = new ProcessBuilder("cmd.exe", "/c", "ipconfig").start();
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(ipconfig.getInputStream(), "cp866"));
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
                builder.append("AGENT\n"
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

    private void deleteCheque() throws IOException {
        if (Files.exists(Paths.get(cheque))) {
            Files.delete(Paths.get(cheque));
            if (!Files.exists(Paths.get(cheque))) {
                loglist.add(getLocatDateTime() + "FILE P DELETED... ");
            }
        }

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
