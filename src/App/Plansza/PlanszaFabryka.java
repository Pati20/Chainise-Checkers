package App.Plansza;

public class PlanszaFabryka {

    /**
     * Zastosowanie wzorca projektowego Fabryka, tak by można łatowo dodawać nowe  kształt i rozmiar planszy
     */

    public static Plansza createLocalBoard(int boardType) {
        switch (boardType) {
            case 61:
                return new Plansza61();
        }
        return new Plansza61();
    }
}
