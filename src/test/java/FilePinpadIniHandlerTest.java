import com.upostool.domain.*;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FilePinpadIniHandlerTest {

    private FilePinpadIniHandler filePinpadIniHandler;
    private List<Setting> settings;
    private String fakeContent;
    private File file;
    private Setting settingMock;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        settingMock = Mockito.mock(Setting.class);
        settings = new ArrayList<>();
        fakeContent = "setting=value";
        file = folder.newFile("pinpad.ini");
        filePinpadIniHandler = new FilePinpadIniHandler(settings);
        filePinpadIniHandler.setDir(folder.getRoot().getAbsolutePath());
    }

    @Test
    public void testReadFileMethod() throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(fakeContent);
        fileWriter.close();
        assertTrue(filePinpadIniHandler.readFile().size() > 0);
    }

    @Test
    public void testSaveFileMethod() throws IOException {
        Mockito.when(settingMock.toString()).thenReturn(fakeContent);
        settings.add(settingMock);
        filePinpadIniHandler.saveFile();
        assertEquals(fakeContent, filePinpadIniHandler.readFile().get(0).toString());
    }

    @After
    public void tearDown() throws IOException {
        settings.clear();
    }
}
