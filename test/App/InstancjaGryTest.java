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

@RunWith(MockitoJUnitRunner.class)
public class InstancjaGryTest  {
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
    ClientApp clientapp = mock(ClientApp.class);

    @Mock
    InstancjaGry instancjaGry;



    @Before
    public void setUp() {
        //this.clientapp = new ClientApp();
        this.playerID = playerID + 1;
        polaPlanszy = PlanszaFabryka.stworzLokalnaPlansze(61).stworzPlansze(instancjaGry, 2);
       // instancjaGry = new InstancjaGry(mock(ClientApp.class),1,2);
    }

    @Test
    public void testUnlockGame()   {
        instancjaGry.unlockGame();
        verify(instancjaGry,times(1)).unlockGame();
    }

    @Test
    public void testLockGame()   {
        instancjaGry.lockGame();
        verify(instancjaGry,times(1)).lockGame();

    }

    @Test
    public void testCheckWin()   {
        Boolean result = instancjaGry.checkWin();
        Assert.assertEquals(Boolean.FALSE, result);

        doReturn(true).when(instancjaGry).checkWin();
        Assert.assertTrue(instancjaGry.checkWin());

    }

    @Test
    public void testSelectPawn()   {
        instancjaGry.selectPawn(polaPlanszy.get(0));
        verify(instancjaGry,times(1)).selectPawn(polaPlanszy.get(0));

//        doNothing().when(instancjaGry).selectPawn(polaPlanszy.get(1));
        verify(instancjaGry,times(0)).selectPawn(polaPlanszy.get(1));

        doCallRealMethod().when(instancjaGry).selectPawn(polaPlanszy.get(10));
        instancjaGry.selectPawn(polaPlanszy.get(10));
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
        instancjaGry.movePawnServer(polaPlanszy.get(1),polaPlanszy.get(2));
        verify(instancjaGry,times(1)).movePawnServer(polaPlanszy.get(1),polaPlanszy.get(2));

//        doNothing().when(instancjaGry).movePawnServer(polaPlanszy.get(2),polaPlanszy.get(3));
        verify(instancjaGry,times(0)).movePawnServer(polaPlanszy.get(2),polaPlanszy.get(3));
    }

    @Test
    public void testMoveAndSendPawn()   {
        PlanszaPola p1 = new PlanszaPola(instancjaGry, 2, 1, 9, 3);
        PlanszaPola p2 = new PlanszaPola(instancjaGry, 0, 0, 8, 4);
//        doReturn(true).when(instancjaGry).testMove(p1,p2);

        doReturn(true).when(instancjaGry).moveAndSendPawn(p1,p2);
        Boolean result = instancjaGry.moveAndSendPawn(p1,p2);
        Assert.assertEquals(true, result);

         p1 = new PlanszaPola(instancjaGry, 2, 1, 9, 3);
         p2 = new PlanszaPola(instancjaGry, 0, 0, 8, 6);

        doReturn(false).when(instancjaGry).moveAndSendPawn(p1,p2);
        result = instancjaGry.moveAndSendPawn(p1,p2);
        Assert.assertEquals(false, result);




    }

    @Test
    public void testFindField()   {
        PlanszaPola  p1 = new PlanszaPola(instancjaGry, 2, 1, 9, 5);
//        doCallRealMethod().when(instancjaGry).findField(9,5);

       doReturn(p1 ).when(instancjaGry).findField(9,5);
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

    @Test
    public void testbadMove(){
        doNothing().when(instancjaGry).badMove();
        instancjaGry.badMove();
        verify(instancjaGry,times(1)).badMove();
    }

}

