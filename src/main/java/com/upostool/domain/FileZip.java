package com.upostool.domain;

import java.io.*;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileZip {
    private String destDir;
    private String fileZip;

    public FileZip(String destinationFolder, String fileZip) {
        if (destinationFolder == null || destinationFolder.equals("")) {
            this.destDir = "C:/sberbank/";
        }else {
            this.destDir = destinationFolder;
        }
            this.fileZip = fileZip;
    }

    public void copyZip() throws IOException {
        createDirOnSystem();
        try (InputStream is = getClass().getResource("/com/upostool/" + fileZip).openStream();
             OutputStream os = new FileOutputStream(destDir + fileZip)) {
            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }
        }
        unZipFile();
        deleteZip();
    }

    private void createDirOnSystem() {
        File destination = new File(destDir);
        if (destination.exists()) {
            return;
        } else {
            destination.mkdirs();
        }
    }

    private void unZipFile() throws IOException {

        try (ZipFile zipFile = new ZipFile(destDir + fileZip)) {
            Iterator iterator = zipFile.entries().asIterator();
            while (iterator.hasNext()) {
                ZipEntry entry = (ZipEntry) iterator.next();
                java.io.File entryDestination = new java.io.File(destDir, entry.getName());
                if (entry.isDirectory()) {
                    entryDestination.mkdirs();
                } else {
                    entryDestination.getParentFile().mkdirs();
                    try (InputStream in = zipFile.getInputStream(entry);
                         OutputStream out = new FileOutputStream(entryDestination)) {
                        in.transferTo(out);
                    }
                }
            }
        }
    }

    private void deleteZip() {
        File file = new File(destDir + fileZip);
        if (!file.getName().contains(".zip")) {
            return;
        }
        file.delete();
    }
}
