package com.upostool.dao;

import com.upostool.domain.AppLog;
import com.upostool.domain.Setting;
import com.upostool.domain.FilePinpadIniHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class for instantiating Data Access Objects for handling Pinpad.ini, UPOS-configuration text-file.
 */
public class  FileSettingDAO implements SettingDAO {

    private List<Setting> settings;
    private FilePinpadIniHandler service;
    private AppLog log;

    public FileSettingDAO() {
        settings = new ArrayList<>();
        service = new FilePinpadIniHandler(settings);
        log = new AppLog();
    }

    /**
     * The method for reading UPOS-configuration from file.
     * @param dir String-type directory to UPOS repository.
     * @return list of Setting-objects.
     */
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

    /**
     * The method for searching setting in loaded or created configuration.
     * @param  name Name of the setting.
     * @return Setting-object, if it presents in configuration, otherwise returns null.
     */
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

    /**
     * The method for writing list of Setting-objects into configuration file.
     * @param dir String-type directory to UPOS repository.
     */
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

    /**
     * The method for adding new Setting-object into settings-configuration.
     * If this Setting already exists, its value replaced by new one's.
     * @param s Setting-object to be added into configuration.
     */
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

    /**
     * The method for deleting Setting from configuration.
     * @param name Name variable of the Setting-object.
     */
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

    /**
     * The method for clearing loaded or created configuration.
     */
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
