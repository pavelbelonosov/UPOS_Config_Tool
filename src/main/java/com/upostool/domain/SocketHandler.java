package com.upostool.domain;

import java.io.IOException;
import java.net.Socket;

public class SocketHandler {
    private AppLog log;

    public SocketHandler(AppLog log){
        this.log=log;
    }

    public String checkPortsInfo(){
        StringBuilder builder = new StringBuilder();
        String mainServer = "194.54.14.89";
        String agentServer = "185.157.96.41";
        log.addRecord("CHECKING PORTS...");
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
        return builder.toString();
    }

    private boolean isPortAvailable(String host, int port) {
        try (Socket s = new Socket(host, port)) {
            return true;
        } catch (IOException ex) {
            log.addRecord(ex.getMessage());
            return false;
        }
    }



}
