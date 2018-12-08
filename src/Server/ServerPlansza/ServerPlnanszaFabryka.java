package Server.ServerPlansza;



public class ServerPlnanszaFabryka {

    public static ServerPlansza createLocalBoard(int boardType) {
        switch (boardType) {
            case 61:
                return new ServerPlansza61();
        }
        return new ServerPlansza61();
    }
}
