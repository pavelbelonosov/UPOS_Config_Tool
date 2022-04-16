package com.upostool.dao;

import com.upostool.domain.AppLog;
import com.upostool.domain.Cheque;
import com.upostool.domain.FileChequeHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * The class for instantiating Data Access Objects for handling UPOS cheque file.
 */
public class FileChequeDAO implements ChequeDAO {

    private List<Cheque> cheques;
    private FileChequeHandler service;
    private AppLog appLog;

    public FileChequeDAO(AppLog log) {
        appLog = log;
        cheques = new ArrayList<>();
        service = new FileChequeHandler(cheques);
    }

    /**
     * The method returns decoded from cp866 to UTF-8 content of check file.
     * By default, in UPOS it is file "p".
     * @param dir String-type directory of UPOS repository.
     * @return cheque content, if cheque exists.
     */
    @Override
    public String readCheque(String dir) {
        service.setDir(dir);
        appLog.addRecord("READING CHEQUE...");
        String content = "";
        try {
            content = service.getChequeContent();
        } catch (IOException e) {
            appLog.addRecord(e.getMessage());
        }
        return content;
    }

    @Override
    public List<Cheque> findAll(String s) {
        return null;
    }

    /**
     * The method for finding Cheque-object by its type. Util type used for all non-transactional cheques.
     * @param type One of presented in enum inner class of Cheque class.
     * @return Cheque-object, if exists.
     */
    @Override
    public Cheque findByType(Cheque.Type type) {
        List<Cheque> list = cheques.stream()
                .filter(c->c.getType().equals(type.toString()))
                .collect(Collectors.toList());
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public void update(Cheque c) {}

    /**
     * The method for saving Cheque().toString of all Cheques into file,
     * created in same directory with application.
     */
    @Override
    public void save() {
        appLog.addRecord("SAVING CHEQUE...");
        try {
            service.saveToExternal();
        } catch (FileNotFoundException e) {
            appLog.addRecord(e.getMessage());
        }
    }

    /**
     * The method for deleting UPOS cheque-file.
     * @param dir String-type directory of UPOS application.
     */
    @Override
    public void deleteCheque(String dir) {
        service.setDir(dir);
        service.deleteFile();
    }

    @Override
    public void deleteAll() {
        service.getCheques().clear();
    }

    @Override
    public AppLog getLog() {
        return appLog;
    }
}
