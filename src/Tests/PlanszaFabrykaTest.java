package Tests;

import App.Plansza.Plansza61;
import App.Plansza.PlanszaFabryka;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PlanszaFabrykaTest {

    @Test
    public void createLocalBoard() {
        PlanszaFabryka planszaFabryka = new PlanszaFabryka();
        assertTrue(planszaFabryka.createLocalBoard(61) instanceof Plansza61);
    }
}