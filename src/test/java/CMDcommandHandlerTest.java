import com.upostool.domain.*;
import org.junit.*;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.*;

public class CMDcommandHandlerTest {
    private CMDcommandHandler cmDcommandHandler;

    @Before
    public void setUp(){
        cmDcommandHandler = new CMDcommandHandler();
        cmDcommandHandler.getCmdCommand().setProcess("ipconfig");
        cmDcommandHandler.getCmdCommand().setArg("");
    }
    @Test
    public void testExecuteCommandMethodInvokesProcess(){
        assertTrue(cmDcommandHandler.executeCommand());
    }

    @Test
    public void testGetStdOut() throws IOException {
        cmDcommandHandler.executeCommand();
        assertFalse(cmDcommandHandler.getStdOut().isEmpty());
    }
}
