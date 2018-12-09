package Tests.Plansza;

import App.Plansza.PlanszaTyp1;
import App.Plansza.PlanszaFabryka;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PlanszaFabrykaTest {

    @Test
    public void createLocalBoard() {
        PlanszaFabryka planszaFabryka = new PlanszaFabryka();
        assertTrue(planszaFabryka.stworzLokalnaPlansze(61) instanceof PlanszaTyp1);
    }
}