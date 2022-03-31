package com.upostool.service;

import com.upostool.domain.Setting;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class PinpadIniHandler extends FileHandler {
    private List<Setting> settings;

    public PinpadIniHandler(List settings) {
        setFile("pinpad.ini");
        this.settings = settings;
    }

    @Override
    public List readFile() throws Exception {
        super.readFile().stream().forEach(line -> {
            String[] parts = line.split("=");
            this.settings.add(new Setting(parts[0], parts[1]));
        });
        return this.settings;
    }

    public void saveFile() throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(getDir() + "/" + getFile())) {
            this.settings.stream().forEach(s -> pw.println(s));
        }
    }
}
