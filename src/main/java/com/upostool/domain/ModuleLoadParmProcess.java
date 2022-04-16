package com.upostool.domain;

/**
 * * The class provides methods for executing loadparm.exe module.
 */
public class ModuleLoadParmProcess extends ModuleProcess {
    private AppLog log;

    public ModuleLoadParmProcess(String dir, AppLog log) {
        this(dir, "loadparm.exe", log);
    }

    /**
     * @param dir Directory, containing module
     * @param name Module name (loadparm.exe | sb_pilot.exe)
     * @param log AppLog-object for maintaining application log
     */
    public ModuleLoadParmProcess(String dir, String name, AppLog log) {
        setDir(dir);
        setModule(name);
        this.log = log;
    }

    /**
     * The method executes loadparm.exe with arguments, defined by Operation.Name
     * @param operation One of presented in ModuleLoadParmProcess.Operation inner class.
     * @param code 8-characters' length argument, only used for remote load operation. Set "" to ignore this arg.
     */
    public void execute(Operation operation, String code) {
        switch (operation) {
            case MENU:
                setFirstParam("11");
                setSecondParam("");
                break;
            case REMOTE_LOAD:
                setFirstParam("21");
                setSecondParam(code.trim());
                break;
            case DEL_KEY:
                setFirstParam("22");
                setSecondParam("");
                break;
            case XREPORT:
                setFirstParam("9");
                setSecondParam("1");
                break;
            case PURCHASE:
                setFirstParam("1");
                setSecondParam("100");
                break;
            case REFUND:
                setFirstParam("3");
                setSecondParam("100");
                break;
            case CLOSE_DAY:
                setFirstParam("7");
                setSecondParam("");
                break;
            case TEST_PSDB:
                setFirstParam("47");
                setSecondParam("2");
                break;
            case HELP_INFO:
                setFirstParam("36");
                setSecondParam("");
                break;
            default:
                break;
        }
        if (makeOperation()) {
            log.addRecord(toString());
        }
    }

    public enum Operation {
        MENU("MENU"),
        REMOTE_LOAD("REMOTE LOAD"),
        DEL_KEY("DEL KEY"),
        XREPORT("X-REPORT"),
        PURCHASE("PURCHASE"),
        REFUND("REFUND"),
        CLOSE_DAY("CLOSE DAY"),
        TEST_PSDB("TEST PSDB"),
        HELP_INFO("HELP INFO");

        private String explanation;

        Operation(String explanation) {
            this.explanation = explanation;
        }

        @Override
        public String toString() {
            return explanation;
        }
    }

    private Boolean openMenu() {
        setFirstParam("11");
        setSecondParam("");
        return makeOperation();
    }

    private Boolean downloadParametres(String activationCode) {
        setFirstParam("21");
        setSecondParam(activationCode);
        return makeOperation();
    }

    //One ruble payment
    private Boolean makePayment() {
        setFirstParam("1");
        setSecondParam("100");
        return makeOperation();
    }

    //One ruble refund
    private Boolean makeRefund() {
        setFirstParam("3");
        setSecondParam("100");
        return makeOperation();
    }

    private Boolean closeDay() {
        setFirstParam("7");
        setSecondParam("");
        return makeOperation();
    }

    //Connect to Sberbank POS monitoring
    private Boolean testPSDB() {
        setFirstParam("47");
        setSecondParam("2");
        return makeOperation();
    }

    private Boolean openInfo() {
        setFirstParam("36");
        setSecondParam("");
        return makeOperation();
    }

    private Boolean delKey() {
        setFirstParam("22");
        setSecondParam("");
        return makeOperation();
    }

    private Boolean makeXReport() {
        setFirstParam("9");
        setSecondParam("1");
        return makeOperation();
    }
}
