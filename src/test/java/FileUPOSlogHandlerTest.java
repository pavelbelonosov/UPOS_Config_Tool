import com.upostool.domain.AppLog;
import com.upostool.domain.FileUPOSlogHandler;
import com.upostool.domain.UPOSlog;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

public class FileUPOSlogHandlerTest {
    private UPOSlog uposLogMock;
    private AppLog appLogMock;
    private FileUPOSlogHandler fileUPOSlogHandler;
    private String fakeContent = "someText\nResult= 4119\nsomeText\nResult= 0\nsomeText\nResult= 4353";

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        uposLogMock = Mockito.mock(UPOSlog.class);
        Mockito.when(uposLogMock.getFullName()).thenReturn("sbkernelLogFake.log");
        File file = folder.newFile("sbkernelLogFake.log");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(fakeContent);
        fileWriter.close();
        appLogMock = Mockito.mock(AppLog.class);
        fileUPOSlogHandler = new FileUPOSlogHandler(folder.getRoot().getAbsolutePath(),
                appLogMock, uposLogMock);
    }

    @Test
    public void testGetContentMethodReturnRight() {
        assertTrue(fileUPOSlogHandler.getContent().contains(fakeContent));
    }

    @Test
    public void testFindErrorsMethodReturnsRight() {
        assertEquals(0, fileUPOSlogHandler.findErrors().trim().compareTo("Result= 4119"));
    }

    @After
    public void tearDown() {
        System.out.println(fileUPOSlogHandler.getContent());
        System.out.println(fileUPOSlogHandler.findErrors());
    }
}
