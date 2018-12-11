package App.Plansza;

import App.InstancjaGry;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class PlanszaTyp1Test {

    @Mock
    InstancjaGry instancjaGry;

    @Test
    public void NumberOfFieldTestFor2Players(){
        ArrayList<PlanszaPola> pola = new ArrayList<>();
        Plansza p = new PlanszaTyp1();

        pola = p.stworzPlansze(instancjaGry,2);
        Assert.assertEquals(121,pola.size());
    }

    @Test
    public void NumberOfFieldTestFor3Players(){
        InstancjaGry instancjaGry = mock(InstancjaGry.class);

        ArrayList<PlanszaPola> pola = new ArrayList<>();
        Plansza p = new PlanszaTyp1();

        pola = p.stworzPlansze(instancjaGry,3);
        Assert.assertEquals(121,pola.size());

    }
    @Test
    public void NumberOfFieldTestFor4Players(){
        InstancjaGry instancjaGry = mock(InstancjaGry.class);

        ArrayList<PlanszaPola> pola = new ArrayList<>();
        Plansza p = new PlanszaTyp1();

        pola = p.stworzPlansze(instancjaGry,4);
        Assert.assertEquals(121,pola.size());

    }
    @Test
    public void NumberOfFieldTestFor6Players(){
        InstancjaGry instancjaGry = mock(InstancjaGry.class);

        ArrayList<PlanszaPola> pola = new ArrayList<>();
        Plansza p = new PlanszaTyp1();

        pola = p.stworzPlansze(instancjaGry,6);
        Assert.assertEquals(121,pola.size());

    }

    @Test
    public void NumberOfFieldTestIncorrectNumberOfPlayer(){
        InstancjaGry instancjaGry = mock(InstancjaGry.class);

        ArrayList<PlanszaPola> pola = new ArrayList<>();
        Plansza p = new PlanszaTyp1();

        pola = p.stworzPlansze(instancjaGry,7);
        Assert.assertNotEquals(121,pola.size());
        Assert.assertEquals(0,pola.size());


    }
}
