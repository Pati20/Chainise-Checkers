package Server.ServerPlansza;

import java.util.ArrayList;

public class ServerPlanszaTyp1 extends ServerPlansza{

    public ArrayList<ServerPlanszaPola> serverPlanszaPola = new ArrayList<>();
    int playerID;

    public ArrayList<ServerPlanszaPola> constructBoard(int numberOfPlayers) {

        switch (numberOfPlayers) {
            case 2:
                setPawns(1, 0, 0, 2, 0, 0);
                break;
            case 3:
                setPawns(1, 0, 2, 0, 3, 0);
                break;
            case 4:
                setPawns(0, 1, 2, 0, 3, 4);
                break;
            case 6:
                setPawns(1, 2, 3, 4, 5, 6);
                break;
            default:
                System.out.println("błąd!");
                break;
        }
        return serverPlanszaPola;
    }

    /**
     * Meotoda ustawia graczy w narożnikach w zależności od liczby graczy
     * @param corn1 - narożnik numer 1
     * @param corn2 - narożnik numer 2
     * @param corn3 - narożnik numer 3
     * @param corn4 - narożnik numer 4
     * @param corn5 - narożnik numer 5
     * @param corn6 - narożnik numer 6
     */
    void setPawns(int corn1, int corn2, int corn3, int corn4, int corn5, int corn6) {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j <= i; j++) {
                serverPlanszaPola.add(new ServerPlanszaPola(corn1, corn4, (12 + 2 * j - i), i));
            }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4 - i; j++) {
                serverPlanszaPola.add(new ServerPlanszaPola(corn6, corn3, (2 * j + i), 4 + i));
            }
            for (int j = 0; j < 5 + i; j++) {
                serverPlanszaPola.add(new ServerPlanszaPola(0, 0, (8 + 2 * j - i), 4 + i));
            }
            for (int j = 0; j < 4 - i; j++) {
                serverPlanszaPola.add(new ServerPlanszaPola(corn2, corn5, (18 + 2 * j + i), 4 + i));
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < i + 1; j++) {
                serverPlanszaPola.add(new ServerPlanszaPola(corn5, corn2, (3 + 2 * j - i), 9 + i));
            }
            for (int j = 0; j < 8 - i; j++) {
                serverPlanszaPola.add(new ServerPlanszaPola(0, 0, (5 + 2 * j + i), 9 + i));
            }
            for (int j = 0; j < i + 1; j++) {
                serverPlanszaPola.add(new ServerPlanszaPola(corn3, corn6, (21 + 2 * j - i), 9 + i));
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4 - i; j++) {
                serverPlanszaPola.add(new ServerPlanszaPola(corn4, corn1, (9 + 2 * j + i), i + 13));
            }
        }
    }
}
