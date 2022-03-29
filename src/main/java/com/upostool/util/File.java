package com.upostool.util;

import java.io.*;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class File {
    private String destDir;
    private String fileZip;

    public File(String destinationFolder, String fileZip) {
        this.destDir = destinationFolder;
        this.fileZip = fileZip;
    }

    public void copyFile() throws IOException {
        createDirOnSystem(destDir);

        try(InputStream is = getClass().getResource(fileZip).openStream();
            OutputStream os = new FileOutputStream(destDir+fileZip)){
            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }
        }
        unZipFile();
        deleteZip();
    }

    private void createDirOnSystem(String dir){
        java.io.File destination = new java.io.File(destDir);
        if (destination.exists()) {
            return;
        } else{
            destination.mkdirs();
        }
    }

    private void unZipFile() throws IOException {

        try (ZipFile zipFile = new ZipFile(destDir+fileZip)) {
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
    private void deleteZip(){
        java.io.File file = new java.io.File(destDir+fileZip);
        if(!file.getName().contains(".zip")){
            return;
        }
        file.delete();
    }
}
