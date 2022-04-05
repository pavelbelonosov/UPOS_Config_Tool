package com.upostool.domain;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileChequeHandler extends FileHandler{

    public FileChequeHandler(){
        setFile("p");
    }

    @Override
    public List readFile() throws IOException {
        String content = "";
        long startTime = System.currentTimeMillis();
        long timeWait = startTime + 2000;
        while (timeWait >= System.currentTimeMillis()) {
            if (isExist()) {
                try (FileInputStream fis = new FileInputStream(getDir() + "/" + getFile())) {
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer, 0, fis.available());
                    content = new String(buffer, "cp866");
                }
            }
        }
        return Arrays.asList(content.split("\n"));
    }
}
