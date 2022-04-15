package com.upostool.domain;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class Cheque {
    private String type;
    private String content;
    private int terminalID;
    private long merchantID;
    private long RRN;
    private int authCode;
    //private double amount;

    public Cheque(String content) {
        if (content == null) {
            this.content = "";
        } else {
            this.content = content;
        }
        setType();
        setTerminalID();
        setMerchantID();
        setAuthCode();
        setRRN();
    }

    private void setType() {
        if (content.toLowerCase().contains("сверка итогов")) {
            type = Type.CLOSE_DAY.toString();
        } else if (content.toLowerCase().contains("контрольная лента")) {
            type = Type.X_REPORT.toString();
        } else if (content.toLowerCase().contains("процессинг")) {
            type = Type.UTIL.toString();
        } else if (content.toLowerCase().contains("загрузка параметров")) {
            type = Type.UTIL.toString();
        } else if (content.toLowerCase().contains("отмена")) {
            type = Type.CANCEL.toString();
        } else if (content.toLowerCase().contains("возврат")) {
            type = Type.REFUND.toString();
        } else if (content.toLowerCase().contains("оплата")) {
            type = Type.PURCHASE.toString();
        } else {
            type = Type.UTIL.toString();
        }
    }

    private void setTerminalID() {
        String[] parts = content.split("\\s+");
        try {
            terminalID = Arrays.stream(parts)
                    .filter(s -> s.matches("[0-9]{8}"))
                    .mapToInt(s -> Integer.valueOf(s)).findAny().getAsInt();
        } catch (NoSuchElementException e) {
        }
    }

    private void setMerchantID() {
        String[] parts = content.split("\\s+");
        try {
            merchantID = Arrays.stream(parts)
                    .filter(s -> s.matches("[0-9]{12}"))
                    .mapToLong(s -> Long.valueOf(s)).findFirst().getAsLong();
        } catch (NoSuchElementException e) {
        }
    }


    private void setRRN() {
        if (!type.equals(Type.PURCHASE.toString())) {
            return;
        }
        String[] parts = content.split("\\s+");
        try {
            RRN = Arrays.stream(Arrays.copyOfRange(parts, parts.length - 10, parts.length))
                    .filter(s -> s.matches("[0-9]{12}"))
                    .mapToLong(s -> Long.valueOf(s)).findFirst().getAsLong();
        } catch (NoSuchElementException e) {
        }
    }

    private void setAuthCode() {
        if (!type.equals(Type.PURCHASE.toString())) {
            return;
        }
        String[] parts = content.split("\\s+");
        try {
            authCode = Arrays.stream(parts)
                    .filter(s -> s.length() == 6 && s.matches("[0-9]{6}"))
                    .mapToInt(s -> Integer.valueOf(s)).findFirst().getAsInt();
        } catch (NoSuchElementException e) {
        }
    }

    public String getType() {
        return type;
    }

    public int getTerminalID() {
        return terminalID;
    }

    public long getRRN() {
        return RRN;
    }

    public int getAuthCode() {
        return authCode;
    }

    public long getMerchantID() {
        return merchantID;
    }

    public String getContent() {
        return content;
    }


    @Override
    public String toString() {
        return "Type: " + type + " TID " + terminalID + " MID " + merchantID + " AC " + authCode + " RRN " + RRN + "\n\n"
                + content;
    }

    public enum Type {
        PURCHASE,
        CANCEL,
        REFUND,
        CLOSE_DAY,
        X_REPORT,
        UTIL;
    }
}
