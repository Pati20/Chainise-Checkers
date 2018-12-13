package App.Server.ServerPlansza;

import org.junit.Assert;
import org.junit.Test;

public class ServerPlnanszaFabrykaTest {

    @Test
    public void testCreateLocalBoard() throws Exception {
        ServerPlansza result = ServerPlnanszaFabryka.createLocalBoard(61);
        Assert.assertTrue( result instanceof ServerPlanszaTyp1);
    }
}

