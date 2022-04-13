
import com.upostool.domain.*;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

public class FileHandlerTest {
    private FileHandler fileHandler;
    private String fakeContent;
    private File file;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        fakeContent = "The only way to get rid of the temptation is to yield to it.(O.Wilde)";
        file = folder.newFile("test");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(fakeContent);
        fileWriter.close();
        fileHandler = new FileHandler();
        fileHandler.setDir(folder.getRoot().getAbsolutePath());
        fileHandler.setFile(file.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDirMethodWithNull() {
        fileHandler.setDir(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDirMethodWithEmtyParam() {
        fileHandler.setDir("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetFileMethodWithNull() {
        fileHandler.setFile(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetFileMethodWithEmtyParam() {
        fileHandler.setFile("");
    }

    @Test
    public void testReadFileMethod() throws IOException {
        assertTrue(fileHandler.readFile().toString().contains(fakeContent));
    }

    @Test
    public void testDeleteFileMethod(){
        fileHandler.deleteFile();
        assertFalse(fileHandler.isExist());
    }
}
