import com.upostool.domain.ModuleProcess;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ModuleProcessTest {
    Util moduleProcess;

    @Before
    public void setUp(){
        moduleProcess = new Util();
    }

    @Test
    public void testSetDirMethod(){
        moduleProcess.setDir("random/dir");
        assertTrue(moduleProcess.getDir().endsWith("/"));
    }

    private class Util extends ModuleProcess{

    }
}
