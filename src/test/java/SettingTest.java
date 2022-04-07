import com.upostool.domain.Setting;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SettingTest {
    Setting s1;
    Setting s2;

    @Before
    public void setUp() {
        s1 = new Setting("ComPort", "1");
        s2 = new Setting("ComPort", "1");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void constructorSetNameAndValueRight() {
        assertEquals("ComPort=1", s1.toString());
    }

    @Test
    public void testEqualsMethod() {
        assertEquals(true, s1.equals(s2));
    }
}

