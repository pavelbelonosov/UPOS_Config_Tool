package com.upostool.domain;

import java.io.IOException;

public class FileUPOSlogHandler extends FileHandler {
    private UPOSlog uposLog;
    private AppLog appLog;
    private String content;
    private String errors;

    public FileUPOSlogHandler(String dir, AppLog appLog, UPOSlog uposLog) {
        this.uposLog = uposLog;
        this.appLog = appLog;
        setDir(dir);
    }

    public String getContent() {
        setFile(this.uposLog.getFullName());
        StringBuilder sb = new StringBuilder();
        try {
            readFile().stream().forEach(line -> sb.append(line + "\n"));
            appLog.addRecord("READING " + this.uposLog.getFullName() + " ...");
            content = sb.toString();
        } catch (IOException e) {
            appLog.addRecord("NOT FOUND " + e.getMessage());
        }
        return content;
    }

    public String findErrors() {
        setFile(this.uposLog.getFullName());
        StringBuilder sb = new StringBuilder();
        try {
            readFile().stream().filter(s -> s.contains("Result"))
                    .filter(ss -> !ss.contains("= 0") && !ss.contains("= 4353"))
                    .forEach(l -> sb.append(l + "\n"));
            appLog.addRecord("FINDING PINPAD ERRORS...");
            errors = sb.toString();
        } catch (IOException e) {
            appLog.addRecord("NOT FOUND " + e.getMessage());
        }
        return errors;
    }
}
