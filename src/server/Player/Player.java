package server.Player;

import javafx.scene.paint.Color;
import server.OldBoard;
import server.ServerBoard.ServerBoardField;

import java.util.ArrayList;
import java.util.List;

public class Player {
    public int playerIDOnBoard;
    public int clientID;
    public Color color;
    public Boolean bot;
    public int numberOfPlayers;
    public String moves = "";


    //for humans
    public Player(int cclientID, int nnumber, Color ccolor) {
        this.color = ccolor;
        this.playerIDOnBoard = nnumber;
        this.clientID = cclientID;
        System.out.println("HUMAN cclietnID " + clientID);

    }

    //for bots
    public Player(int nnumber, int numberOfPlayers, OldBoard board) {
        this.playerIDOnBoard = nnumber;
        this.numberOfPlayers = numberOfPlayers;

    }

    public String Turn() {

        return moves;
    }

    public void gameStart(){}

}
