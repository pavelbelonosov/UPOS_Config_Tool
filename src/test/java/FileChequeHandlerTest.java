import com.upostool.domain.*;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class FileChequeHandlerTest {
    private FileChequeHandler fileChequeHandler;
    private File file;
    private String fakeContent;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        fakeContent = "Мой график на сегодня -\n6-часовая депрессия с уклоном в самобичевание.\n(Филип К. Дик)";
        fileChequeHandler = new FileChequeHandler(new ArrayList());
        fileChequeHandler.setDir(folder.getRoot().getAbsolutePath());
    }

    @Test
    public void testConstructorSetChequeNameRight() {
        assertEquals("p", fileChequeHandler.getFile());
    }

    @Test
    public void testGetChequeContentMethodReturnsRightWhenFileNotExists() throws IOException {
        assertEquals("", fileChequeHandler.getChequeContent());
    }

    @Test
    public void testGetChequeContentMethodDecodesCP866() throws IOException {
        file = folder.newFile("p");
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "Cp866"));
        pw.println(fakeContent);
        pw.close();
        assertTrue( fileChequeHandler.getChequeContent().contains(fakeContent));
    }

    @Test
    public void testGetChequeContentAddsChequeInList() throws IOException {
        file = folder.newFile("p");
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "Cp866"));
        pw.println(fakeContent);
        pw.close();
        fileChequeHandler.getChequeContent();
        assertEquals(1, fileChequeHandler.getCheques().size());
    }

}
