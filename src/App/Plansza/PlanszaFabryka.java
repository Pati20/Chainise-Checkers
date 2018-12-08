package App.Plansza;

public class PlanszaFabryka {

    /**
     * Zastosowanie wzorca projektowego Fabryka, tak by można łatowo dodawać nowe  kształt i rozmiar planszy
     */

    public static Plansza stworzLokalnaPlansze(int boardType) {
        switch (boardType) {
            case 61:
                return new PlanszaTyp1();
        }
        return new PlanszaTyp1();
    }
}
