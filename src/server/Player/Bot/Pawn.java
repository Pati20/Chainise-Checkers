package server.Player.Bot;

public class Pawn { //pionek
    public int column;
    public int row;
    boolean enabled;

    public Pawn(int column, int row){
        this.column = column;
        this.row = row;
        this.enabled = true;
    }


}
