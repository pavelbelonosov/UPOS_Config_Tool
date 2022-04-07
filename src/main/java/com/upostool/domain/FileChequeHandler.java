package com.upostool.domain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileChequeHandler extends FileHandler {
    private AppLog log;
    private String content;

    public FileChequeHandler(String dir, AppLog log) {
        setFile("p");
        setDir(dir);
        this.log = log;
    }

    @Override
    public List readFile() {
        String content = "";
        long startTime = System.currentTimeMillis();
        long endTime = startTime + 2000L;
        while (endTime >= System.currentTimeMillis()) {
            if (isExist()) {
                log.addRecord("READING CHEQUE...");
                try (FileInputStream fis = new FileInputStream(getDir() + "/" + getFile())) {
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer, 0, fis.available());
                    content = new String(buffer, "cp866");
                } catch (FileNotFoundException e) {
                    log.addRecord(e.getMessage());
                } catch (IOException e) {
                    log.addRecord(e.getMessage());
                }
            }
        }
        return Arrays.asList(content.split("\n"));
    }

    public String getContent() {
        StringBuilder sb = new StringBuilder();
        readFile().stream().forEach(line -> sb.append(line + "\n"));
        content = sb.toString();
        return content;
    }
}
