import com.upostool.domain.AppLog;
import com.upostool.domain.FileZip;
import com.upostool.domain.ModuleLoadParmProcess;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.*;

public class ModuleLoadParmProcessTest {
    ModuleLoadParmProcess moduleLoadParmProcess;
    AppLog appLog;
    FileZip fileZip;

    @Rule
    public TemporaryFolder folder= new TemporaryFolder(new File("c:\\temp\\"));


    @Before
    public void setUp() throws IOException {
        appLog = new AppLog();
        fileZip = new FileZip(folder.getRoot().getAbsolutePath(),"31.00.18.zip");
        moduleLoadParmProcess = new ModuleLoadParmProcess(folder.getRoot().getAbsolutePath(), appLog);
    }

    @Test
    public void testExecuteMethodByMenuOperationWithEmptySecondArg() {
        moduleLoadParmProcess.execute(ModuleLoadParmProcess.Operation.MENU, "");
        assertTrue(appLog.toString().contains("loadparm.exe 11 "));
        //assertEquals("loadparm.exe 11 ",moduleLoadParmProcess.toString());
    }

    @Test
    public void testExecuteMethodByMenuOperationWithSecondArg() {
        moduleLoadParmProcess.execute(ModuleLoadParmProcess.Operation.MENU, "arg");
        assertFalse(appLog.toString().contains("loadparm.exe 11 arg"));
    }
}
