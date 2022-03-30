package com.upostool.DAO;

import com.upostool.domain.Setting;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DAOFileService {

    private String file;
    private List<Setting> settings;

    public DAOFileService(List<Setting> settings) {
        this.settings=settings;
    }

    public void setFile(String dir) {
        this.file = dir + "/pinpad.ini";
    }

    public List getSettings() throws Exception {
        Files.lines(Paths.get(file))
                .forEach(line -> {
                    String[] parts = line.split("=");
                    this.settings.add(new Setting(parts[0],parts[1]));
                });
        return this.settings;
    }

    public void save() throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(file)) {
            this.settings.stream().forEach(s->pw.println(s));
        }
    }


}