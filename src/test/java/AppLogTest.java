import com.upostool.domain.AppLog;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppLogTest {

    @Test
    public void testToStringReturnsAllRecords() {
        AppLog appLog = new AppLog();
        appLog.addRecord("record1");
        appLog.addRecord("record2");
        assertTrue(appLog.toString().contains("record1")
                && appLog.toString().contains("record2"));
    }
}
