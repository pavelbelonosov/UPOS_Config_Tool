package com.upostool.DAO;

import com.upostool.domain.AppLog;
import com.upostool.domain.Cheque;
import com.upostool.domain.FileChequeHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileChequeDAO implements ChequeDAO {

    private List<Cheque> cheques;
    private FileChequeHandler service;
    private AppLog appLog;

    public FileChequeDAO(AppLog log) {
        appLog = log;
        cheques = new ArrayList<>();
        service = new FileChequeHandler(cheques);

    }

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
    public void update(Cheque c) {

    }

    @Override
    public void save() {
        appLog.addRecord("SAVING CHEQUE...");
        try {
            service.saveToExternal();
        } catch (FileNotFoundException e) {
            appLog.addRecord(e.getMessage());
        }
    }

    @Override
    public void deleteCheque() {
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
