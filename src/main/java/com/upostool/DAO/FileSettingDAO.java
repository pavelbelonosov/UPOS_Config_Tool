package com.upostool.DAO;

import com.upostool.domain.AppLog;
import com.upostool.domain.Setting;
import com.upostool.domain.FilePinpadIniHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileSettingDAO implements SettingDAO {

    private List<Setting> settings;
    private FilePinpadIniHandler service;
    private AppLog log;

    public FileSettingDAO() {
        settings = new ArrayList<>();
        service = new FilePinpadIniHandler(settings);
        log = new AppLog();
    }

    @Override
    public List<Setting> findAll(String dir) {
        try {
            log.addRecord("LOADING SETTINGS...");
            deleteAll();
            service.setDir(dir);
            service.readFile();
            settings.stream().forEach(s -> log.addRecord(s.toString()));
        } catch (IllegalArgumentException e) {
            log.addRecord(e.getMessage());
        } catch (IOException e) {
            log.addRecord("NO ACCESS " + e.getMessage());
        }
        return this.settings;
    }

    @Override
    public Setting findByName(String name) {
        List<Setting> list = settings.stream()
                .filter(s -> s.getName()
                        .equals(name))
                .collect(Collectors.toList());
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public void save(String dir) {
        try {
            log.addRecord("SAVING PINPAD.INI...");
            service.setDir(dir);
            service.saveFile();
        } catch (IllegalArgumentException e) {
            log.addRecord(e.getMessage());
        } catch (FileNotFoundException e) {
            log.addRecord(e.getMessage());
        }
    }

    @Override
    public void update(Setting s) {
        Setting existed = findByName(s.getName());
        if (existed == null) {
            settings.add(s);
            log.addRecord("ADD " + s);
            return;
        }
        settings.set(this.settings.indexOf(existed), s);
        log.addRecord("REMOVE " + existed);
        log.addRecord("ADD " + s);
    }

    @Override
    public void replace() {

    }

    @Override
    public void removeByName(String name) {
        if (findByName(name) != null) {
            log.addRecord("REMOVE " + findByName(name));
        }
        settings.remove(findByName(name));
    }

    @Override
    public boolean isEmpty() {
        return settings.isEmpty();
    }

    @Override
    public void deleteAll() {
        settings.clear();
        log.addRecord("CACHE CLEARED");
    }

    @Override
    public AppLog getLog() {
        return log;
    }
}
