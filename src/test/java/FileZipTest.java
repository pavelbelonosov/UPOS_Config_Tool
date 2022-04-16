import com.upostool.domain.AppLog;
import com.upostool.domain.FileZip;
import com.upostool.util.Cons;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.*;

public class FileZipTest {
    FileZip fileZip;
    AppLog appLogMock;
    String uposZip;
    String driverZip;
    File subFolder;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();


    @Before
    public void setUp() throws IOException {
        uposZip = Cons.UPOS_VERSIONS[new Random().nextInt(Cons.UPOS_VERSIONS.length-1)+1] + ".zip";
        driverZip = Cons.DRIVERS_VERSIONS[new Random().nextInt(Cons.DRIVERS_VERSIONS.length-1)+1] + ".zip";
        appLogMock = Mockito.mock(AppLog.class);
        subFolder = folder.newFolder("sc552");
    }

    @Test
    public void testCopyZipMethodByNullZipName() throws IOException {
        fileZip = new FileZip(subFolder.getAbsolutePath(), null, appLogMock);
        fileZip.copyZip();
        assertFalse(subFolder.list().length > 0);
    }

    @Test
    public void testCopyZipMethodByWrongZipName() throws IOException {
        fileZip = new FileZip(subFolder.getAbsolutePath(), "wrong.zip", appLogMock);
        fileZip.copyZip();
        assertFalse(subFolder.list().length > 0);
    }

    @Test
    public void testCopyZipMethodWithUpos() throws IOException {
        fileZip = new FileZip(subFolder.getAbsolutePath(), uposZip, appLogMock);
        fileZip.copyZip();
        assertTrue(subFolder.list().length > 0);
    }

    @Test
    public void testCopyZipMethodWithDriver() throws IOException {
        fileZip = new FileZip(subFolder.getAbsolutePath(), driverZip, appLogMock);
        fileZip.copyZip();
        assertTrue(subFolder.list().length > 0);
    }

    @Ignore
    @After
    public void tearDown() {
        System.out.println(subFolder.list().length + " files extracted\n");
    }
}
