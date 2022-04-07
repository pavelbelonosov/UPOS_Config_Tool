import com.upostool.domain.UPOSlog;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UPOSlogTest {
    UPOSlog upoSlog;

    @Before
    public void setUp() {
        upoSlog = new UPOSlog();
    }

    @Test
    public void setFullNameMethodSetsSBkernelLogNameRight(){
        upoSlog.setFullName("sbkernel");
        assertTrue(upoSlog.getFullName().matches("sbkernel[0-9]{4}.log"));
    }

    @Test
    public  void setFullNameMethodSetsUPOSlogNameRight(){
        upoSlog.setFullName("upos");
        assertTrue(upoSlog.getFullName().matches("upos[0-9]{4}.log"));
    }
}
