package com.upostool.domain;
/**
 * * The class extends ModuleLoadParmProcess to provide methods for executing sb_pilot.exe module.
 */
public class ModuleSbPilotProcess extends ModuleLoadParmProcess {

    public ModuleSbPilotProcess(String dir, AppLog log) {
        super(dir, "sb_pilot.exe", log);
    }

}
