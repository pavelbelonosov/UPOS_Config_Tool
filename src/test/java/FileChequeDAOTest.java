import com.upostool.dao.FileChequeDAO;
import com.upostool.domain.*;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.*;

import static org.junit.Assert.*;

public class FileChequeDAOTest {
    private FileChequeDAO fileChequeDAO;
    private File file;
    private String fakeContent;
    private String dir;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        AppLog appLogMock = Mockito.mock(AppLog.class);
        fileChequeDAO = new FileChequeDAO(appLogMock);
        fakeContent = "Счастье можно найти даже в темные времена,\nесли не забывать обращаться к свету.\n(Альбус Дамблдор)";
        file = folder.newFile("p");
        dir = folder.getRoot().getAbsolutePath();
    }

    @Test
    public void testReadChequeMethodWhenChequeFileNotExists() {
        file.delete();
        assertTrue(fileChequeDAO.readCheque(dir).isEmpty());
    }

    @Test
    public void testReadChequeMethod() {
        writeToFile();
        assertTrue(fileChequeDAO.readCheque(dir).contains(fakeContent));
    }

    @Test
    public void testFindByTypeMethod() {
        writeToFile();
        fileChequeDAO.readCheque(dir);
        Cheque cheque = fileChequeDAO.findByType(Cheque.Type.UTIL);
        assertTrue(cheque.getContent().contains(fakeContent));
    }

    @Test
    public void testDeleteCheque(){
        fileChequeDAO.deleteCheque(dir);
        assertFalse(file.exists());
    }

    private void writeToFile() {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "Cp866"));) {
            pw.println(fakeContent);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
