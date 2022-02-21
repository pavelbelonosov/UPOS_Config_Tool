package com.upostool;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ExtractFile {
    private String destDir;
    private String fileZip;

    public ExtractFile(String destinationFolder, String fileZip) {
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
        unzipFile();
        deleteZip();
    }

    private void createDirOnSystem(String dir){
        File destination = new File(destDir);
        if (destination.exists()) {
            return;
        } else{
            destination.mkdirs();
        }
    }

    private void unzipFile() throws IOException {

        try (ZipFile zipFile = new ZipFile(destDir+fileZip)) {
            Iterator iterator = zipFile.entries().asIterator();
            while (iterator.hasNext()) {
                ZipEntry entry = (ZipEntry) iterator.next();
                File entryDestination = new File(destDir, entry.getName());
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
        File file = new File(destDir+fileZip);
        if(!file.getName().contains(".zip")){
            return;
        }
        file.delete();
    }
}
