package com.upostool.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class AppLog {

    private List<String> logs = new ArrayList<>();

    public void addRecord(String record) {
        logs.add(getLocatDateTime() + record);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        logs.stream().forEach(l -> sb.append(l + "\n"));
        return sb.toString();
    }

    private String getLocatDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
        String timeStamp = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(dtf) + ":  ";
        return timeStamp;
    }


}
