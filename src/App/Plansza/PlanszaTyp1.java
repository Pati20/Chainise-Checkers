package App.Plansza;

import java.util.ArrayList;
import App.InstancjaGry;

/**
 * Klasa odpowiedzialna za stworzenie planszy o 61 polach do gry (121 łącznie)
 */

public class PlanszaTyp1 extends Plansza {

    private ArrayList<PlanszaPola> planszaPola = new ArrayList<>();

    /**
     * W zależnośći od ilości graczy tworzymy plansze, ustawiajac zawodników w odpowiednich rogach
     * @param instancjaGry
     * @param numberOfPlayers
     * @return planszaPola - wypełniona pionami plansza
     */
    public ArrayList<PlanszaPola> stworzPlansze(InstancjaGry instancjaGry, int numberOfPlayers) {
        switch (numberOfPlayers) {
            case 2:
                ustawPionek(instancjaGry, 1, 0, 0, 2, 0, 0);
                break;
            case 3:
                ustawPionek(instancjaGry, 1, 0, 2, 0, 3, 0);
                break;
            case 4:
                ustawPionek(instancjaGry, 0, 1, 2, 0, 3, 4);
                break;
            case 6:
                ustawPionek(instancjaGry, 1, 2, 3, 4, 5, 6);
                break;
            default:
                break;
        }
        return planszaPola;
    }

    /**
     * Meotoda odpowiedzilana za ustawienie początkowe pionków na planszy
     * @param corn1 - narożnik numer 1
     * @param corn2 - narożnik numer 2
     * @param corn3 - narożnik numer 3
     * @param corn4 - narożnik numer 4
     * @param corn5 - narożnik numer 5
     * @param corn6 - narożnik numer 6
     */
    void ustawPionek(InstancjaGry instancjaGry , int corn1, int corn2, int corn3, int corn4, int corn5, int corn6) {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j <= i; j++) {
                planszaPola.add(new PlanszaPola(instancjaGry, corn1, corn4, (12 + 2 * j - i), i));//kol-wiersz
            }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4 - i; j++) {
                planszaPola.add(new PlanszaPola(instancjaGry, corn6, corn3, (2 * j + i), 4 + i));
            }
            for (int j = 0; j < 5 + i; j++) {
                planszaPola.add(new PlanszaPola(instancjaGry, 0, 0, (8 + 2 * j - i), 4 + i));
            }
            for (int j = 0; j < 4 - i; j++) {
                planszaPola.add(new PlanszaPola(instancjaGry, corn2, corn5, (18 + 2 * j + i), 4 + i));
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < i + 1; j++) {
                planszaPola.add(new PlanszaPola(instancjaGry, corn5, corn2, (3 + 2 * j - i), 9 + i));
            }
            for (int j = 0; j < 8 - i; j++) {
                planszaPola.add(new PlanszaPola(instancjaGry, 0, 0, (5 + 2 * j + i), 9 + i));
            }
            for (int j = 0; j < i + 1; j++) {
                planszaPola.add(new PlanszaPola(instancjaGry, corn3, corn6, (21 + 2 * j - i), 9 + i));
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4 - i; j++) {
                planszaPola.add(new PlanszaPola(instancjaGry, corn4, corn1, (9 + 2 * j + i), i + 13));
            }
        }
    }
}
