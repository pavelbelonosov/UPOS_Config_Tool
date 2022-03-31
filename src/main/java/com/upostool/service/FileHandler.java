package com.upostool.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileHandler {
    private String dir;
    private String file;

    public List<String> readFile() throws Exception {
        List<String> content = new ArrayList<>();
        Files.lines(Paths.get(dir + "/" + file))
                .forEach(line -> {
                    content.add(line);
                });
        return content;
    }

    private void deleteFile() {
        File file = new File(this.dir + "/" + this.file);
        if (!file.exists()) {
            return;
        }
        file.delete();
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public void setFile(String file) {
        this.file = file;
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