package com.upostool.domain;

import java.util.Objects;

public class Setting {

    private String name;
    private String value;

    public Setting(String name, String value) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Setting setting = (Setting) o;
        return Objects.equals(name, setting.name) && Objects.equals(value, setting.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    @Override
    public String toString() {
        return name + "=" + value;
    }
}
