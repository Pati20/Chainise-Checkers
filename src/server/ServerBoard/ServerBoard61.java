package server.ServerBoard;

import java.util.ArrayList;

public class ServerBoard61 extends ServerBoard {

    public ArrayList<ServerBoardField> serverBoardFields = new ArrayList<>();
    int playerID;

    public ArrayList<ServerBoardField> constructBoard(int numberOfPlayers) {
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
        return serverBoardFields;
    }

    void setPawns(int corn1, int corn2, int corn3, int corn4, int corn5, int corn6) {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j <= i; j++) {
                serverBoardFields.add(new ServerBoardField(corn1, corn4, (12 + 2 * j - i), i));
            }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4 - i; j++) {
                serverBoardFields.add(new ServerBoardField(corn6, corn3, (2 * j + i), 4 + i));
            }
            for (int j = 0; j < 5 + i; j++) {
                serverBoardFields.add(new ServerBoardField(0, 0, (8 + 2 * j - i), 4 + i));
            }
            for (int j = 0; j < 4 - i; j++) {
                serverBoardFields.add(new ServerBoardField(corn2, corn5, (18 + 2 * j + i), 4 + i));
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < i + 1; j++) {
                serverBoardFields.add(new ServerBoardField(corn5, corn2, (3 + 2 * j - i), 9 + i));
            }
            for (int j = 0; j < 8 - i; j++) {
                serverBoardFields.add(new ServerBoardField(0, 0, (5 + 2 * j + i), 9 + i));
            }
            for (int j = 0; j < i + 1; j++) {
                serverBoardFields.add(new ServerBoardField(corn3, corn6, (21 + 2 * j - i), 9 + i));
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4 - i; j++) {
                serverBoardFields.add(new ServerBoardField(corn4, corn1, (9 + 2 * j + i), i + 13));
            }
        }
    }
}
