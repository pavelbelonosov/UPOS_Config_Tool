package com.upostool;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class PinpadIniWriter {

    private final String file;
    private final Map<String, String> pinpadIniContent;

    public PinpadIniWriter(String dir) {
        this.file = dir + "/pinpad.ini";
        this.pinpadIniContent = new LinkedHashMap<>();
    }

    public PinpadIniWriter(String dir, Map settings) {
        this.file = dir + "/pinpad.ini";
        this.pinpadIniContent = settings;
    }

    public Map getSettings() throws Exception {
        Files.lines(Paths.get(file))
                .forEach(line -> {
                    String[] parts = line.split("=");
                    pinpadIniContent.put((parts[0] + "="), parts[1]);
                });
        return this.pinpadIniContent;
    }

    public void save() throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(file)) {
            for (String line : pinpadIniContent.keySet()) {
                pw.println(line + pinpadIniContent.get(line));
            }
        }
    }

    public void delete() {
        if (pinpadIniContent.isEmpty()) {
            return;
        }
        pinpadIniContent.clear();
    }

}
