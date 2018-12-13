package App;

import App.Plansza.Plansza;
import App.Plansza.PlanszaFabryka;
import App.Plansza.PlanszaPola;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class InstancjaGryTest {
    @Mock
    ArrayList<PlanszaPola> polaPlanszy;
    @Mock
    PlanszaPola wybranyPionek;
    @Mock
    PlanszaPola ruszonyPionek;
    @Mock
    List<String> moveRegister;
    @Mock
    Plansza board;
    @Mock
    GridPane gridpane;
    @Mock
    VBox vbox;

    int playerID;

    @Mock
    ClientApp clientapp;

    @Mock
    InstancjaGry instancjaGry;

    @Before
    public void setUp() {
        this.clientapp = new ClientApp();
        this.playerID = playerID + 1;
        polaPlanszy = PlanszaFabryka.stworzLokalnaPlansze(61).stworzPlansze(instancjaGry, 2);
    }

    @Test
    public void testUnlockGame()   {
        instancjaGry.unlockGame();
    }

    @Test
    public void testLockGame()   {
        instancjaGry.lockGame();
    }

//    @Test
//    public void testCheckWin()   {
//        Boolean result = instancjaGry.checkWin();
//        Assert.assertEquals(Boolean.TRUE, result);
//
//    }

    @Test
    public void testSelectPawn()   {
        instancjaGry.selectPawn(polaPlanszy.get(0));
        verify(instancjaGry,times(1)).selectPawn(polaPlanszy.get(0));

        doNothing().when(instancjaGry).selectPawn(polaPlanszy.get(1));
        verify(instancjaGry,times(0)).selectPawn(polaPlanszy.get(1));
    }


    @Test
    public void testMovePawn()   {
        PlanszaPola p1 = new PlanszaPola(instancjaGry, 2, 1, 9, 3);
        PlanszaPola p2 = new PlanszaPola(instancjaGry, 0, 0, 8, 4);

        doReturn(true).when(instancjaGry).movePawn(p1,p2);
        boolean result = instancjaGry.movePawn(p1,p2);

        Assert.assertNotNull(instancjaGry);
        Assert.assertTrue( result);

         p1 = new PlanszaPola(instancjaGry, 2, 1, 9, 5);
         p2 = new PlanszaPola(instancjaGry, 0, 0, 5, 5);

        doReturn(false).when(instancjaGry).movePawn(p1,p2);
        result = instancjaGry.movePawn(p1,p2);

        Assert.assertNotNull(instancjaGry);
        Assert.assertFalse(result);

        p1 = new PlanszaPola(instancjaGry, 2, 1, 9, 13);
        p2 = new PlanszaPola(instancjaGry, 1, 0, 8, 13);

        doReturn(false).when(instancjaGry).movePawn(p1,p2);
        result = instancjaGry.movePawn(p1,p2);

        Assert.assertNotNull(instancjaGry);
        Assert.assertFalse(result);


    }

    @Test
    public void testMovePawnServer()   {
        instancjaGry.movePawnServer(new PlanszaPola(null, 0, 0, 0, 0), new PlanszaPola(null, 0, 0, 0, 0));
    }

    @Test
    public void testMoveAndSendPawn()   {
        boolean result = instancjaGry.moveAndSendPawn(new PlanszaPola(null, 0, 0, 0, 0), new PlanszaPola(null, 0, 0, 0, 0));
        Assert.assertEquals(true, result);
    }

    @Test
    public void testFindField()   {

        PlanszaPola  p1 = new PlanszaPola(instancjaGry, 2, 1, 9, 5);
        doCallRealMethod().when(instancjaGry).findField(9,5);

       doReturn(new PlanszaPola(2,1,9,5)).when(instancjaGry).findField(9,5);
        Assert.assertEquals(p1,instancjaGry.findField(9,5));
    }

    @Test
    public void testTestMove()   {

        PlanszaPola p1 = new PlanszaPola(instancjaGry, 2, 1, 9, 3);
        PlanszaPola p2 = new PlanszaPola(instancjaGry, 0, 0, 8, 4);

        doReturn(true).when(instancjaGry).testMove(p1,p2);
        boolean result = instancjaGry.testMove(p1,p2);

        Assert.assertNotNull(instancjaGry);
        Assert.assertTrue( result);

        p1 = new PlanszaPola(instancjaGry, 2, 1, 9, 5);
        p2 = new PlanszaPola(instancjaGry, 0, 0, 5, 5);

        doReturn(false).when(instancjaGry).testMove(p1,p2);
        result = instancjaGry.testMove(p1,p2);

        Assert.assertNotNull(instancjaGry);
        Assert.assertFalse(result);

        p1 = new PlanszaPola(instancjaGry, 2, 1, 9, 13);
        p2 = new PlanszaPola(instancjaGry, 1, 0, 8, 13);

        doReturn(false).when(instancjaGry).testMove(p1,p2);
        result = instancjaGry.testMove(p1,p2);

        Assert.assertNotNull(instancjaGry);
        Assert.assertFalse(result);

    }
}

