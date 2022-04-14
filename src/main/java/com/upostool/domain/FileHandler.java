package com.upostool.domain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileHandler {
    private String dir;
    private String file;

    public List<String> readFile() throws IOException {
        List<String> content = new ArrayList<>();
        Files.lines(Paths.get(dir + "/" + file))
                .forEach(line -> {
                    content.add(line);
                });
        return content;
    }

    public Boolean isExist() {
        File file = new File(this.dir + "/" + this.file);
        return file.exists();
    }

    public void deleteFile() {
        File file = new File(this.dir + "/" + this.file);
        if (!file.exists()) {
            return;
        }
        file.delete();
    }

    public void setDir(String dir) throws IllegalArgumentException {
        if (dir == null || dir.equals("")) {
            System.out.println("No directory provided!");
            throw new IllegalArgumentException("No directory provided!");
        } else {
            this.dir = dir;
        }
    }

    public void setFile(String file) throws IllegalArgumentException {
        if (file == null || file.equals("")) {
            System.out.println("No file provided!");
            throw new IllegalArgumentException("No file provided!");
        } else {
            this.file = file;
        }
    }

    public String getDir() {
        return dir;
    }

    public String getFile() {
        return file;
    }

}