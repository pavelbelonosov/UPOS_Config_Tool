import com.upostool.DAO.FileSettingDAO;
import com.upostool.domain.*;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

public class FileSettingDAOTest {
    private FileSettingDAO fileSettingDAO;
    private File file;
    private String fakeContent;
    private String dir;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        file = folder.newFile("pinpad.ini");
        dir = folder.getRoot().getAbsolutePath();
        fileSettingDAO = new FileSettingDAO();
        fakeContent = "setting=value";
    }

    @Test
    public void testFindAllMethodWithEmptySettingsFile() {
        assertTrue(fileSettingDAO.findAll(dir).isEmpty() && fileSettingDAO.isEmpty());
    }

    @Test
    public void testFindAllMethod() throws IOException {
        writeToFile(fakeContent);
        assertEquals(fakeContent, fileSettingDAO.findAll(dir)
                .get(0).toString());
    }

    @Test
    public void testFindByNameMethod() {
        writeToFile(fakeContent);
        fileSettingDAO.findAll(dir);
        Setting s = fileSettingDAO.findByName("setting");
        assertTrue(s.getName().equals("setting")
                && s.getValue().equals("value"));
    }

    @Test
    public void testSaveMethod() {
        writeToFile(fakeContent);//writing one Setting into configuration-file
        fileSettingDAO.findAll(dir); //reading configuration from this file
        fileSettingDAO.update(new Setting("setting2", "value2"));//adding new Setting in this configuration
        writeToFile("");//clearing file
        fileSettingDAO.save(dir);//saving two Settings into file
        fileSettingDAO.deleteAll();//clearing configuration
        assertTrue(fileSettingDAO.findAll(dir).size() == 2);//checking, whether file has two Setting-obj.

    }

    @Test
    public void testUpdateMethod() {
        fileSettingDAO.update(new Setting("setting2", "value2"));
        fileSettingDAO.save(dir);
        fileSettingDAO.deleteAll();
        List<Setting> list = fileSettingDAO.findAll(dir);
        Setting s = list.get(0);
        assertTrue(list.size() == 1
                && Objects.equals("setting2=value2",s.toString()));
    }

    @Test
    public void testRemoveByNameMethod(){
        fileSettingDAO.update(new Setting("setting2", "value2"));
        fileSettingDAO.removeByName("setting2");
        fileSettingDAO.save(dir);
        assertTrue(fileSettingDAO.findAll(dir).isEmpty());
    }

    @Test
    public void testDeleteAllMethod(){
        fileSettingDAO.update(new Setting("setting2", "value2"));
        fileSettingDAO.update(new Setting("setting3","value3"));
        fileSettingDAO.deleteAll();
        assertTrue(fileSettingDAO.findAll(dir).isEmpty());
    }

    private void writeToFile(String s) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
