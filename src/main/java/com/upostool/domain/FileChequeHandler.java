package com.upostool.domain;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileChequeHandler extends FileHandler {
    private Cheque cheque;
    private List<Cheque> cheques;

    public FileChequeHandler(List cheques) {
        setFile("p");
        this.cheques = cheques;
    }

    public String getChequeContent() throws IOException {
        StringBuilder sb = new StringBuilder();
        readFileCheque().stream().forEach(line -> sb.append(line + "\n"));
        cheque = new Cheque(sb.toString());
        cheques.add(cheque);
        return cheque.getContent();
    }

    public void saveToExternal() throws FileNotFoundException {
        if(cheque.getTerminalID()==0){
            return;
        }
        File file = new File("./" + cheque.getTerminalID() + ".txt");
        try (PrintWriter pw = new PrintWriter(file)) {
            this.cheques.stream().forEach(s -> pw.println(s));
        }
    }

    private List readFileCheque() throws IOException {
        String content = "";
        long startTime = System.currentTimeMillis();
        long endTime = startTime + 300L;
        while (endTime >= System.currentTimeMillis()) {
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

    public List<Cheque> getCheques() {
        return cheques;
    }

}

