package com.upostool.domain;

public abstract class ModuleProcess {
    private String dir, module, firstParam, secondParam;
    private Process process;

    public Boolean makeOperation() {
        try {
            if (secondParam.equals("")&&firstParam.equals("21")) {
                return false;
            }
            if(secondParam.equals("")){
                process = new ProcessBuilder(dir + module, firstParam).start();
            } else {
                process = new ProcessBuilder(dir + module, firstParam, secondParam).start();
            }
            process.waitFor();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir+"/";
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getFirstParam() {
        return firstParam;
    }

    public void setFirstParam(String firstParam) {
        this.firstParam = firstParam;
    }

    public String getSecondParam() {
        return secondParam;
    }

    public void setSecondParam(String secondParam) {
        this.secondParam = secondParam;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    @Override
    public String toString() {
        return module + " " + firstParam + " " + secondParam;
    }
}
