package com.upostool.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CMDcommandHandler {
    private CMDcommand cmdCommand;
    private Process process;

    public CMDcommandHandler() {
        this.cmdCommand = new CMDcommand();
    }

    public Boolean executeCommand() {
        try {
            process = new ProcessBuilder(cmdCommand.getCmd()).start();
            cmdCommand.setArgs(null);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns - if not empty - standart output stream
     * of defined process.
     */
    public String getStdOut() throws IOException {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(process.getInputStream(), "cp866"));
        StringBuilder commandOutput = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            commandOutput.append(line);
            commandOutput.append("\n");
        }
        return commandOutput.toString();
    }

    public CMDcommand getCmdCommand() {
        return cmdCommand;
    }

}
