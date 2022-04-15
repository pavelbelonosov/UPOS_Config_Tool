import com.upostool.domain.*;
import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CMDcommandTest {
    private CMDcommand cmdCommand;
    private List<String> expectedCMD;

    @Before
    public void setUp(){
        cmdCommand = new CMDcommand();
        expectedCMD = new ArrayList<>();
        expectedCMD.add("cmd.exe");
        expectedCMD.add("/c");
    }

    @Test
    public void testGetCmdMethodByNullProcess(){
        assertEquals(expectedCMD, cmdCommand.getCmd());
    }

    @Test
    public void testGetCmdMethodByNullArgs(){
        cmdCommand.setProcess("process");
        expectedCMD.add("process");
        assertEquals(expectedCMD,cmdCommand.getCmd());
    }

    @Test
    public void testGetCmdMethodWithOneArg(){
        cmdCommand.setProcess("process");
        cmdCommand.setArg("oneArg");
        expectedCMD.add("process");
        expectedCMD.add("oneArg");
        assertEquals(expectedCMD,cmdCommand.getCmd());
    }

    @Test
    public void testGetCmdMethodWithManyArg(){
        String[] args = new String[]{"arg1","arg2","arg3"};
        cmdCommand.setProcess("process");
        cmdCommand.setArgs(args);
        expectedCMD.add("process");
        expectedCMD.addAll(Arrays.asList(args));
        assertEquals(expectedCMD,cmdCommand.getCmd());
    }

    @Test
    public void testToStringMethodWhenCmdIsNotDefined(){
        assertTrue(cmdCommand.toString().isEmpty());
    }

    @Test
    public void testToStringMethodReturnsCmdRight(){
        cmdCommand.setProcess("process");
        cmdCommand.setArg("arg");
        cmdCommand.getCmd();
        assertEquals("process arg", cmdCommand.toString().trim());
    }
}
