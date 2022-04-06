package com.upostool.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class UPOSlog {
    private String fullName;

    public void setFullName(String module) {
        fullName = module + getLocatDateTime().charAt(2) + getLocatDateTime().charAt(3) +
                getLocatDateTime().charAt(5) + getLocatDateTime().charAt(6) + ".log";
    }

    public String getFullName() {
        return fullName;
    }

    private String getLocatDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
        String timeStamp = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(dtf) + ":  ";
        return timeStamp;
    }



}
