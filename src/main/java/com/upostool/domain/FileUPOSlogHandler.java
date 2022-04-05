package com.upostool.domain;

import java.io.IOException;

public class FileUPOSlogHandler extends FileHandler {
    private UPOSlog uposLog;
    private AppLog appLog;
    private String content;

    public FileUPOSlogHandler(String dir, AppLog appLog, UPOSlog uposLog) {
        this.uposLog = uposLog;
        this.appLog = appLog;
        setDir(dir);
    }

    public String getContent(){
        setFile(this.uposLog.getFullName());
        StringBuilder sb = new StringBuilder();
        try {
            readFile().stream().forEach(line->sb.append(line+"\n"));
        } catch (IOException e) {
            appLog.addRecord(e.getMessage());
        }

        content=sb.toString();
        return content;
    }

    public String findErrors() {
        StringBuilder sb = new StringBuilder();
        try {
            appLog.addRecord("FINDING PINPAD ERRORS...");
            readFile().stream().filter(s -> s.contains("Result"))
                    .filter(ss -> !ss.contains("=0") || !ss.contains("=4354"))
                    .forEach(l -> sb.append(l + "\n"));
        } catch (IOException e) {
            appLog.addRecord(e.getMessage());
        }
        return sb.toString();
    }
}
