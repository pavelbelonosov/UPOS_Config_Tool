package com.upostool.DAO;

import com.upostool.domain.Setting;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SettingFileDAO implements SettingDAO {

    private List<Setting> settings;
    private DAOFileService service;

    public SettingFileDAO() {
        settings = new ArrayList<>();
        service = new DAOFileService(settings);
    }

    @Override
    public List<Setting> findAll(String dir) {
        try {
            service.setFile(dir);
            service.getSettings();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.settings;
    }

    @Override
    public Setting findByName(String name) {
        List<Setting> list = this.settings.stream()
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Setting s) {
        Setting existed = findByName(s.getName());
        if (existed == null) {
            this.settings.add(s);
            return;
        }
        this.settings.add(this.settings.indexOf(existed), s);
    }

    @Override
    public void replaceSetting() {

    }

    @Override
    public void removeByName(String name) {
        this.settings.remove(findByName(name));
    }

    @Override
    public boolean isEmpty() {
        return this.settings.isEmpty();
    }

    @Override
    public void deleteAll() {
        this.settings.clear();
    }
}
