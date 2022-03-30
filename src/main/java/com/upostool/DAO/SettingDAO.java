package com.upostool.DAO;

import com.upostool.domain.Log;
import com.upostool.domain.Setting;
import java.util.List;

public interface SettingDAO {

    List<Setting> findAll(String s);
    Setting findByName(String s);
    void save(String s);
    void add(Setting s);
    void replaceSetting();
    void removeByName(String s);
    boolean isEmpty();
    void deleteAll();
    Log getLog();

}
