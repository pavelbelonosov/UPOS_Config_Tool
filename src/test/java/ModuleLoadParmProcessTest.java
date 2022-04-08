import com.upostool.domain.AppLog;
import com.upostool.domain.FileZip;
import com.upostool.domain.ModuleLoadParmProcess;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ModuleLoadParmProcessTest {
    ModuleLoadParmProcess moduleLoadParmProcess;
    AppLog appLog;
    FileZip fileZip;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        appLog = new AppLog();
        File subFolder = folder.newFolder("sc552");
        fileZip = new FileZip(subFolder.getAbsolutePath(), "31.00.18.zip", appLog);
        fileZip.copyZip();
        moduleLoadParmProcess = new ModuleLoadParmProcess(subFolder.getAbsolutePath(), appLog);
    }

    @Test(timeout = 20000L)
    public void testExecuteMethodByMenuOperationWithEmptySecondArg() {
        moduleLoadParmProcess.execute(ModuleLoadParmProcess.Operation.MENU, "");
        assertTrue(appLog.toString().contains("loadparm.exe 11"));
    }

    @Test(timeout = 20000L)
    public void testExecuteMethodByMenuOperationWithSecondArg() {
        moduleLoadParmProcess.execute(ModuleLoadParmProcess.Operation.MENU, "arg");
        assertFalse(appLog.toString().contains("loadparm.exe 11 arg"));
    }

    @Test(timeout = 20000L)
    public void testExecuteMethodByRemoteLoadOperationWithEmptySecondArg() {
        moduleLoadParmProcess.execute(ModuleLoadParmProcess.Operation.REMOTE_LOAD, "");
        assertFalse(appLog.toString().contains("loadparm.exe 21"));
    }

    @Test(timeout = 20000L)
    public void testExecuteMethodByRemoteLoadOperationWithSecondArg() {
        moduleLoadParmProcess.execute(ModuleLoadParmProcess.Operation.REMOTE_LOAD, "arg");
        assertTrue(appLog.toString().contains("loadparm.exe 21 arg"));
    }

    @Test(timeout = 20000L)
    public void testExecuteMethodByDelKeyOperationWithEmptySecondArg() {
        moduleLoadParmProcess.execute(ModuleLoadParmProcess.Operation.DEL_KEY, "");
        assertTrue(appLog.toString().contains("loadparm.exe 22"));
    }

    @Test(timeout = 20000L)
    public void testExecuteMethodByDelKeyOperationWithSecondArg() {
        moduleLoadParmProcess.execute(ModuleLoadParmProcess.Operation.DEL_KEY, "arg");
        assertFalse(appLog.toString().contains("loadparm.exe 22 arg"));
    }

    @Test(timeout = 20000L)
    public void testExecuteMethodByXReportOperationWithEmptySecondArg() {
        moduleLoadParmProcess.execute(ModuleLoadParmProcess.Operation.XREPORT, "");
        assertTrue(appLog.toString().contains("loadparm.exe 9 1"));
    }

    @Test(timeout = 20000L)
    public void testExecuteMethodByXREportOperationWithSecondArg() {
        moduleLoadParmProcess.execute(ModuleLoadParmProcess.Operation.XREPORT, "arg");
        assertFalse(appLog.toString().contains("loadparm.exe 9 arg"));
    }

    @Test(timeout = 20000L)
    public void testExecuteMethodByPurchaseOperationWithEmptySecondArg() {
        moduleLoadParmProcess.execute(ModuleLoadParmProcess.Operation.PURCHASE, "");
        assertTrue(appLog.toString().contains("loadparm.exe 1 100"));
    }

    @Test(timeout = 20000L)
    public void testExecuteMethodByPurchaseOperationWithSecondArg() {
        moduleLoadParmProcess.execute(ModuleLoadParmProcess.Operation.PURCHASE, "arg");
        assertFalse(appLog.toString().contains("loadparm.exe 1 arg"));
    }

    @Test(timeout = 20000L)
    public void testExecuteMethodByRefundOperationWithEmptySecondArg() {
        moduleLoadParmProcess.execute(ModuleLoadParmProcess.Operation.REFUND, "");
        assertTrue(appLog.toString().contains("loadparm.exe 3 100"));
    }

    @Test(timeout = 20000L)
    public void testExecuteMethodByRefundOperationWithSecondArg() {
        moduleLoadParmProcess.execute(ModuleLoadParmProcess.Operation.REFUND, "arg");
        assertFalse(appLog.toString().contains("loadparm.exe 3 arg"));
    }

    @Test(timeout = 30000L)
    public void testExecuteMethodByCloseDayOperationWithEmptySecondArg() {
        moduleLoadParmProcess.execute(ModuleLoadParmProcess.Operation.CLOSE_DAY, "");
        assertTrue(appLog.toString().contains("loadparm.exe 7"));
    }

    @Test(timeout = 30000L)
    public void testExecuteMethodByCloseDayOperationWithSecondArg() {
        moduleLoadParmProcess.execute(ModuleLoadParmProcess.Operation.CLOSE_DAY, "arg");
        assertFalse(appLog.toString().contains("loadparm.exe 7 arg"));
    }

    @Test(timeout = 20000L)
    public void testExecuteMethodByTestPSDBOperationWithEmptySecondArg() {
        moduleLoadParmProcess.execute(ModuleLoadParmProcess.Operation.TEST_PSDB, "");
        assertTrue(appLog.toString().contains("loadparm.exe 47 2"));
    }

    @Test(timeout = 20000L)
    public void testExecuteMethodByTestPSDBOperationWithSecondArg() {
        moduleLoadParmProcess.execute(ModuleLoadParmProcess.Operation.TEST_PSDB, "arg");
        assertFalse(appLog.toString().contains("loadparm.exe 47 arg"));
    }

    @Test(timeout = 20000L)
    public void testExecuteMethodByHelpInfoOperationWithEmptySecondArg() {
        moduleLoadParmProcess.execute(ModuleLoadParmProcess.Operation.HELP_INFO, "");
        assertTrue(appLog.toString().contains("loadparm.exe 36"));
    }

    @Test(timeout = 20000L)
    public void testExecuteMethodByHelpInfoOperationWithSecondArg() {
        moduleLoadParmProcess.execute(ModuleLoadParmProcess.Operation.HELP_INFO, "arg");
        assertFalse(appLog.toString().contains("loadparm.exe 36 arg"));
    }

    @After
    public void tearDown() {
        System.out.println(appLog.toString());
    }
}
