package server.Player;

import javafx.scene.paint.Color;

public class Human extends Player {
    int playerIDOnBoard;
    int clientID;
    Color color;

    public Human(int cclientID, int pplayerIDOnBoard, Color ccolor) {
        super(cclientID, pplayerIDOnBoard, ccolor);
        this.color = ccolor;
        this.playerIDOnBoard = pplayerIDOnBoard;
        this.clientID = cclientID;
        System.out.println("HUMAN cclietnID " + clientID);
        bot = false;

    }


}
