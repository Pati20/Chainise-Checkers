package App.Server.ServerPlansza;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

public class ServerPlanszaTest {
    @Mock
    ArrayList<ServerPlanszaPola> serverBoardFields;
    @InjectMocks
    ServerPlansza serverPlansza;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMovePawn() throws Exception {
        boolean result = serverPlansza.movePawn(new ServerPlanszaPola(0, 0, 0, 0), new ServerPlanszaPola(0, 0, 0, 0));
        Assert.assertEquals(true, result);
    }

    @Test
    public void testMovePawnServer() throws Exception {
        serverPlansza.movePawnServer(new ServerPlanszaPola(0, 0, 0, 0), new ServerPlanszaPola(0, 0, 0, 0));
    }

    @Test
    public void testFindField() throws Exception {
        ServerPlanszaPola result = serverPlansza.findField(0, 0);
        Assert.assertEquals(new ServerPlanszaPola(0, 0, 0, 0), result);
    }

    @Test
    public void testTestMove() throws Exception {
        boolean result = serverPlansza.testMove(new ServerPlanszaPola(0, 0, 0, 0), new ServerPlanszaPola(0, 0, 0, 0));
        Assert.assertEquals(true, result);
    }
}

