package com.upostool.DAO;

import com.upostool.domain.Log;
import com.upostool.domain.Setting;
import java.util.List;

public interface DAO {

    List<Setting> findAll(String s);
    Setting findByName(String s);
    void update(Setting s);
    void save(String s);
    void replace();
    void removeByName(String s);
    boolean isEmpty();
    void deleteAll();
    Log getLog();

}
