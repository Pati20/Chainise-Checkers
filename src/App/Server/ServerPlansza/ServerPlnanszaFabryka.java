package App.Server.ServerPlansza;



public class ServerPlnanszaFabryka {

    /**
     * Metoda odpowiedzialna za tworzenie obiektu ServerPlanszaTyp1().
     * @param boardType typ 61 pól
     * @return plansza 61 pól
     */
    public static ServerPlansza createLocalBoard(int boardType) {
        switch (boardType) {
            case 61:
                return new ServerPlanszaTyp1();
        }
        return new ServerPlanszaTyp1();
    }
}
