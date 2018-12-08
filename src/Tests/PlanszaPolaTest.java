package Tests;

import App.Plansza.PlanszaPola;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlanszaPolaTest {

    PlanszaPola planszaPola;
    PlanszaPola planszaPola2;
    @Before
    public void setUp()  {
        planszaPola = new PlanszaPola(20,1,5,10);
        planszaPola2 = new PlanszaPola(null,20,6,17,5);
    }

    @Test
    public void TestFirstConstructor(){
        Assert.assertEquals(planszaPola.pionek, 20);
        Assert.assertEquals(planszaPola.col, 5);
        Assert.assertEquals(planszaPola.row, 10);
        Assert.assertEquals(planszaPola.winID, 1);
    }

    @Test
    public void TestSecondConstructor(){
        Assert.assertEquals(planszaPola2.pionek, 20);
        Assert.assertEquals(planszaPola2.col, 17);
        Assert.assertEquals(planszaPola2.row, 5);
        Assert.assertEquals(planszaPola2.winID, 6);
    }

}