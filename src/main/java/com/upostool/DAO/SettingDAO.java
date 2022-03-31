package com.upostool.DAO;

import com.upostool.domain.Log;
import com.upostool.domain.Setting;
import com.upostool.service.PinpadIniHandler;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SettingDAO implements DAO {

    private List<Setting> settings;
    private PinpadIniHandler service;
    private Log log;

    public SettingDAO() {
        settings = new ArrayList<>();
        service = new PinpadIniHandler(settings);
        log = new Log();
    }

    @Override
    public List<Setting> findAll(String dir) {
        try {
            log.addLog("LOADING SETTINGS...");
            deleteAll();
            service.setDir(dir);
            service.readFile();
            settings.stream().forEach(s -> log.addLog(s.toString()));
        } catch (Exception e) {
            log.addLog("NO ACCESS " + e.getMessage());
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
            service.setDir(dir);
            service.saveFile();
            log.addLog("SAVING PINPAD.INI...");
        } catch (FileNotFoundException e) {
            log.addLog(e.getMessage());
        }
    }

    @Override
    public void update(Setting s) {
        Setting existed = findByName(s.getName());
        if (existed == null) {
            settings.add(s);
            log.addLog("ADD " + s);
            return;
        }
        settings.set(this.settings.indexOf(existed), s);
        log.addLog("REMOVE "+ existed);
        log.addLog("ADD " + s);
    }

    @Override
    public void replace() {

    }

    @Override
    public void removeByName(String name) {
        if (findByName(name) != null) {
            log.addLog("REMOVE " + findByName(name));
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
        log.addLog("CACHE CLEARED");
    }

    @Override
    public Log getLog() {
        return log;
    }
}
