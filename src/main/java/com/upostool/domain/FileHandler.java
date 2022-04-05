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

    public void deleteFile() {
        File file = new File(this.dir + "/" + this.file);
        if (!file.exists()) {
            return;
        }
        file.delete();
    }

    public Boolean isExist(){
        File file = new File(this.dir + "/" + this.file);
        return file.exists();
    }

    public void setDir(String dir) throws IllegalArgumentException {
        if (dir == null||dir.equals("")) {
            throw new  IllegalArgumentException("No directory!");
        } else {
            this.dir = dir;
        }
    }

    public void setFile(String file) throws IllegalArgumentException {
        if (file == null||file.equals("")) {
            throw new IllegalArgumentException("No file!");
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

    /* public void save() throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(path)) {
            this.content.stream().forEach(s -> pw.println(s));
        }
    }*/

}