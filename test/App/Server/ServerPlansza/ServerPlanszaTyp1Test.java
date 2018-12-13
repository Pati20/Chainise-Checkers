package App.Server.ServerPlansza;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;

public class ServerPlanszaTyp1Test {
    @Mock
    ArrayList<ServerPlanszaPola> serverPlanszaPola;
    @Mock
    ArrayList<ServerPlanszaPola> serverBoardFields;
    @InjectMocks
    ServerPlanszaTyp1 serverPlanszaTyp1;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConstructBoard() throws Exception {
        ArrayList<ServerPlanszaPola> result = serverPlanszaTyp1.constructBoard(0);
        Assert.assertEquals(new ArrayList<ServerPlanszaPola>(Arrays.asList(new ServerPlanszaPola(0, 0, 0, 0))), result);
    }

    @Test
    public void testSetPawns() throws Exception {
        serverPlanszaTyp1.setPawns(0, 0, 0, 0, 0, 0);
    }

    @Test
    public void testMovePawn() throws Exception {
        boolean result = serverPlanszaTyp1.movePawn(new ServerPlanszaPola(0, 0, 0, 0), new ServerPlanszaPola(0, 0, 0, 0));
        Assert.assertEquals(true, result);
    }

    @Test
    public void testMovePawnServer() throws Exception {
        serverPlanszaTyp1.movePawnServer(new ServerPlanszaPola(0, 0, 0, 0), new ServerPlanszaPola(0, 0, 0, 0));
    }

    @Test
    public void testFindField() throws Exception {
        ServerPlanszaPola result = serverPlanszaTyp1.findField(0, 0);
        Assert.assertEquals(new ServerPlanszaPola(0, 0, 0, 0), result);
    }

    @Test
    public void testTestMove() throws Exception {
        boolean result = serverPlanszaTyp1.testMove(new ServerPlanszaPola(0, 0, 0, 0), new ServerPlanszaPola(0, 0, 0, 0));
        Assert.assertEquals(true, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme