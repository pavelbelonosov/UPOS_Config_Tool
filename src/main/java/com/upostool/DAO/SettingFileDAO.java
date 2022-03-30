package com.upostool.DAO;

import com.upostool.domain.Log;
import com.upostool.domain.Setting;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SettingFileDAO implements SettingDAO {

    private List<Setting> settings;
    private DAOFileService service;
    private Log log;

    public SettingFileDAO() {
        settings = new ArrayList<>();
        service = new DAOFileService(settings);
        log = new Log();
    }

    @Override
    public List<Setting> findAll(String dir) {
        try {
            log.addLog("LOADING SETTINGS...");
            deleteAll();
            service.setFile(dir);
            service.getSettings();
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
            service.setFile(dir);
            service.save();
            log.addLog("SAVING PINPAD.INI...");
        } catch (FileNotFoundException e) {
            log.addLog(e.getMessage());
        }
    }

    @Override
    public void add(Setting s) {
        Setting existed = findByName(s.getName());
        if (existed == null) {
            settings.add(s);
            log.addLog("ADD " + s.toString());
            return;
        }
        settings.set(this.settings.indexOf(existed), s);
        log.addLog("REMOVE "+existed.toString());
        log.addLog("ADD " + s.toString());
    }

    @Override
    public void replaceSetting() {

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
