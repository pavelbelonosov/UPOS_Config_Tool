package com.upostool.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHandler {
    private String user;
    private String password;

    public PropertiesHandler() {
        readProperties();
    }

    private void readProperties() {
        try (InputStream input = getClass().getResource("/com/upostool/user.properties").openStream()) {
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Cannot read properties file");
                return;
            }
            prop.load(input);
            this.user = prop.getProperty("user");
            this.password = prop.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}