package Server.ServerPlansza;



public class ServerPlnanszaFabryka {

    public static ServerPlansza createLocalBoard(int boardType) {
        switch (boardType) {
            case 61:
                return new ServerPlanszaTyp1();
        }
        return new ServerPlanszaTyp1();
    }
}
