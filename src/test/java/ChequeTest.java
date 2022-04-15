import com.upostool.domain.*;
import org.junit.*;

import static org.junit.Assert.*;

public class ChequeTest {

    @Test
    public void testConstructorByEmptyChequeContent() {
        Cheque cheque = new Cheque("");
        assertTrue(cheque.getType().equals(Cheque.Type.UTIL.toString())
                && cheque.getTerminalID() == 0
                && cheque.getAuthCode() == 0
                && cheque.getMerchantID() == 0
                && cheque.getRRN() == 0);
    }
    @Test
    public void testConstructorByNullContent(){
        Cheque cheque = new Cheque(null);
        assertTrue(cheque.getType().equals(Cheque.Type.UTIL.toString())
                && cheque.getTerminalID() == 0
                && cheque.getAuthCode() == 0
                && cheque.getMerchantID() == 0
                && cheque.getRRN() == 0);
    }
}
