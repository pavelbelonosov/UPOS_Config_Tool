package com.upostool.domain;

import java.io.IOException;
import java.net.Socket;

public class SocketHandler {
    private AppLog log;
    private String mainHost;
    private String agentHost;
    private StringBuilder checkPortsInfo;

    public SocketHandler(AppLog log) {
        mainHost = "194.54.14.89";
        agentHost = "185.157.96.41";
        checkPortsInfo = new StringBuilder();
        this.log = log;
    }

    /**
     * The method checks listen-status of SB sockets
     * @return String, containing info, whether sockets are available or not.
     */
    public String checkPortsInfo() {
        log.addRecord("CHECKING PORTS...");
        checkPort650();
        checkPort666();
        checkPort670();
        checkPort690();
        checkAgentPort80();
        return checkPortsInfo.toString();
    }

    private void checkPort650() {
        if (isPortAvailable(mainHost, 650)) {
            checkPortsInfo.append(mainHost + " ON 650 AVAILABLE\n");
        } else {
            checkPortsInfo.append(mainHost + " ON 650 NOT AVAILABLE\n");
        }
    }

    private void checkPort666() {
        if (isPortAvailable(mainHost, 666)) {
            checkPortsInfo.append(mainHost + " ON 666 AVAILABLE\n");
        } else {
            checkPortsInfo.append(mainHost + " ON 666 NOT AVAILABLE\n");
        }
    }

    private void checkPort670() {
        if (isPortAvailable(mainHost, 670)) {
            checkPortsInfo.append(mainHost + " ON 670 AVAILABLE\n");
        } else {
            checkPortsInfo.append(mainHost + " ON 670 NOT AVAILABLE\n");
        }
    }

    private void checkPort690() {
        if (isPortAvailable(mainHost, 690)) {
            checkPortsInfo.append(mainHost + " ON 690 AVAILABLE\n");
        } else {
            checkPortsInfo.append(mainHost + " ON 690 NOT AVAILABLE\n");
        }
    }

    public void checkAgentPort80() {
        if (isPortAvailable(agentHost, 80)) {
            checkPortsInfo.append("\nAGENT\n"
                    + agentHost + " ON 80 AVAILABLE");
        } else {
            checkPortsInfo.append("AGENT\n"
                    + agentHost + " ON 80 NOT AVAILABLE");
        }
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
