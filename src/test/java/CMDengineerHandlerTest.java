import com.upostool.domain.*;
import org.junit.*;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class CMDengineerHandlerTest {
    private CMDengineerHandler cmdEngineerHandler;
    private AppLog appLog;
    private String dir;

    @Before
    public void setUp() {
        dir = "randomDir";
        appLog = Mockito.mock(AppLog.class);
        cmdEngineerHandler = new CMDengineerHandler(dir, appLog);

    }

    @Test
    public void testExecuteCMDmethodByIPconfigCommand() {
        cmdEngineerHandler.executeCMD(CMDengineerHandler.Command.IPCONFIG, "");
        assertFalse(cmdEngineerHandler.getStdOut().isEmpty());
    }

    @Test
    public void testExecuteCMDmethodByPingCommand() {
        cmdEngineerHandler.executeCMD(CMDengineerHandler.Command.PING, "1.1.1.1");
        assertFalse(cmdEngineerHandler.getStdOut().isEmpty());
    }

}
