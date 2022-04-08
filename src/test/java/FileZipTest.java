import com.upostool.domain.AppLog;
import com.upostool.domain.FileZip;
import com.upostool.domain.ModuleLoadParmProcess;
import com.upostool.util.Cons;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class FileZipTest {
    FileZip fileZip;
    AppLog appLog;
    String uposZip;
    File subFolder;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        uposZip = Cons.UPOS_VERSIONS[new Random().nextInt(9)] + ".zip";
        appLog = new AppLog();
        subFolder = folder.newFolder("sc552");
    }

    @Test
    public void testCopyZipMethodByWrongZipName() throws IOException {
        fileZip = new FileZip(subFolder.getAbsolutePath(), "wrong.zip", appLog);
        fileZip.copyZip();
        assertFalse(subFolder.list().length > 0);
    }

    @Test
    public void testCopyZipMethod() throws IOException {
        fileZip = new FileZip(subFolder.getAbsolutePath(), uposZip, appLog);
        fileZip.copyZip();
        assertTrue(subFolder.list().length > 0);
    }

    @After
    public void tearDown() {
        System.out.println(appLog.toString() + " " + subFolder.list().length + " files extracted\n");
    }
}
