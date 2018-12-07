package server.ServerBoard;


public class ServerBoardFactory {

    public static ServerBoard createLocalBoard(int boardType) {
        switch (boardType) {
            case 61:
                return new ServerBoard61();
        }
        return new ServerBoard61();
    }
}
