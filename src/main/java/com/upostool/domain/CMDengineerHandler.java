package com.upostool.domain;

import java.io.IOException;

public class CMDengineerHandler extends CMDcommandHandler {

    private AppLog log;
    private String dir;

    public CMDengineerHandler(String dir, AppLog log) {
        super();
        this.log = log;
        this.dir = dir;
    }

    public void executeCMD(Command command, String arg) {
        switch (command) {
            case SERVICES:
                getCmdCommand().setProcess("SERVICES.MSC");
                break;
            case DEVMGMT:
                getCmdCommand().setProcess("DEVMGMT.MSC");
                break;
            case IPCONFIG:
                getCmdCommand().setProcess("IPCONFIG");
                getCmdCommand().setArg("/all");
                break;
            case PING:
                getCmdCommand().setProcess("PING");
                getCmdCommand().setArg(arg);
                break;
            case REG_DLL:
                getCmdCommand().setProcess("regsvr32.exe");
                getCmdCommand().setArgs(new String[]{dir + "sbrf.dll", dir + "sbrfcom.dll"});
                break;
            case UNREG_DLL:
                getCmdCommand().setProcess("regsvr32.exe");
                getCmdCommand().setArgs(new String[]{"/U", dir + "sbrf.dll", dir + "sbrfcom.dll"});
                break;
            case REG_AGENT:
                getCmdCommand().setProcess(dir+"agent.exe");
                getCmdCommand().setArg("/reg");
                break;
            case UNREG_AGENT:
                getCmdCommand().setProcess(dir+"agent.exe");
                getCmdCommand().setArg("/unreg");
                break;
            case RUN_AGENT:
                getCmdCommand().setProcess(dir+"agent.exe");
                getCmdCommand().setArg("/run");
                break;
            default:
                break;
        }
        if (executeCommand()) {
            log.addRecord(getCmdCommand().toString());
        }
    }

    @Override
    public String getStdOut() {
        String s = "";
        try {
            s = super.getStdOut();
            return s;
        } catch (IOException e) {
            log.addRecord(getCmdCommand().toString() + " " + e.getMessage());
        }
        return s;
    }

    public enum Command {
        SERVICES,
        DEVMGMT,
        IPCONFIG,
        PING,
        REG_DLL,
        UNREG_DLL,
        REG_AGENT,
        UNREG_AGENT,
        RUN_AGENT;
    }

}
