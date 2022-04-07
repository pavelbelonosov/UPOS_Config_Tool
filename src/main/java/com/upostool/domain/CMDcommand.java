package com.upostool.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CMDcommand {
    private String process;
    private String[] args;
    private List<String> cmd;

    public void setProcess(String process) {
        if (process == null || process.equals("")) {
            throw new IllegalArgumentException("No cmd process defined!");
        } else {
            this.process = process;
        }
    }

    public void setArg(String arg) {
        this.args = new String[]{arg};
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public List<String> getCmd() {
        List<String> base = new ArrayList<>();
        base.add("cmd.exe");
        base.add("/c");
        if (process == null) {
            return base;
        } else if (process != null && (args == null || args[0] == "")) {
            base.add(process);
            cmd = base;
        } else if (process != null && args != null) {
            cmd = base;
            cmd.add(process);
            cmd.addAll(Arrays.asList(args));
        }
        return cmd;
    }

    @Override
    public String toString() {
        if (cmd.size() < 2) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i < cmd.size(); i++) {
            sb.append(cmd.get(i) + " ");
        }
        return sb.toString();
    }
}
