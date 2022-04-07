import com.upostool.domain.AppLog;
import com.upostool.domain.SocketHandler;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SocketHandlerTest {
    SocketHandler socketHandler;
    String info;

    @Before
    public void setUP() {
        socketHandler = new SocketHandler(new AppLog());
        info = socketHandler.checkPortsInfo();
    }

    @Test
    public void testCheckPortsInfoContains650port() {
        assertTrue(info.contains("650"));
    }

    @Test
    public void testCheckPortsInfoContains666port() {
        assertTrue(info.contains("666"));
    }

    @Test
    public void testCheckPortsInfoContains670port() {
        assertTrue(info.contains("670"));
    }

    @Test
    public void testCheckPortsInfoContains690port() {
        assertTrue(info.contains("690"));
    }

    @Test
    public void testCheckPortsInfoContainsAgentPort() {
        assertTrue(info.contains("80"));
    }
}
