package App.Plansza;

import java.util.ArrayList;
import App.InstancjaGry;

/**
 *Abstrakcyjna klasa odpowedzialna za wymuszeniu stworzenia planszy klasie która po niej dziedziczy
 */
    public abstract class Plansza {
        public abstract ArrayList<PlanszaPola> constructBoard(InstancjaGry instancjaGry, int numberOfPlayers);
    }



