package App.Server.Gracze;

import javafx.scene.paint.Color;



public class Człowiek extends Gracz {
    private int playerIDOnBoard;
    private int clientID;
    private Color color;

    public Człowiek(int clientID, int playerIDOnBoard, Color ccolor) {
        super(clientID, playerIDOnBoard, ccolor);
        this.color = ccolor;
        this.playerIDOnBoard = playerIDOnBoard;
        this.clientID = clientID;
        System.out.println("HUMAN cclietnID " + this.clientID);
        bot = false;
    }
}