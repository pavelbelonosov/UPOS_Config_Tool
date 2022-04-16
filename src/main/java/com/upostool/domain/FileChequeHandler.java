package com.upostool.domain;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service class for FileChequeDAO. Provides methods for reading text file and decoding.
 */
public class FileChequeHandler extends FileHandler {
    private Cheque cheque;
    private List<Cheque> cheques;

    /**
     * Constructor sets cheque file name to "p" by default.
     * @param cheques
     */
    public FileChequeHandler(List cheques) {
        setFile("p");
        this.cheques = cheques;
    }

    /**
     * The method returns UPOS cheque content, initializes Cheque-object and adds it into collection.
     * @return String-type cheque content.
     * @throws IOException when cheque file not found.
     */
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

    /**
     * The method for saving cheque collection into file, located within application directory.
     * @throws FileNotFoundException
     */
    public void saveToExternal() throws FileNotFoundException {
        if (cheque != null) {
            File file = new File("./" + cheque.getTerminalID() + ".txt");
            try (PrintWriter pw = new PrintWriter(file)) {
                this.cheques.stream().forEach(s -> pw.println(s));
            }
        }
    }

    /**
     * The method for reading and decoding cheque text file.
     * @return List of lines.
     * @throws IOException
     */
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

