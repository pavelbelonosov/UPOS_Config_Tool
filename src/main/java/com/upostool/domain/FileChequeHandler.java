package com.upostool.domain;

import java.io.*;
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
        readFile().stream().forEach(line -> sb.append(line + "\n"));
        if (sb.length() == 0) {
            return "";
        }
        cheque = new Cheque(sb.toString());
        cheques.add(cheque);
        return cheque.getContent();
    }

    public void saveToExternal() throws FileNotFoundException {
        if (cheque != null) {
            File file = new File("./" + cheque.getTerminalID() + ".txt");
            try (PrintWriter pw = new PrintWriter(file)) {
                this.cheques.stream().forEach(s -> pw.println(s));
            }
        }
    }

    @Override
    public List readFile() throws IOException {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + 300L;
        while (endTime >= System.currentTimeMillis()) {
            if (isExist()) {
                try (FileInputStream fis = new FileInputStream(getDir() + "/" + getFile())) {
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer, 0, fis.available());
                    String content = new String(buffer, "cp866");
                    return Arrays.asList(content.split("\n"));
                }
            }
        }
        return new ArrayList();
    }

    public List<Cheque> getCheques() {
        return cheques;
    }

}

