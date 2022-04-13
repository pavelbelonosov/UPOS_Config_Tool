package com.upostool.DAO;

import com.upostool.domain.AppLog;
import com.upostool.domain.Cheque;

import java.util.List;

public interface ChequeDAO {

    String readCheque(String s);
    List<Cheque> findAll(String s);
    Cheque findByType(Cheque.Type t);
    void update(Cheque c);
    void save();
    void deleteCheque(String dir);
    void deleteAll();
    AppLog getLog();
}
