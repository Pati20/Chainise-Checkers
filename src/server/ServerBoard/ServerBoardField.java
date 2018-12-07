package server.ServerBoard;

public class ServerBoardField {
    public int pawn;
    public int col;
    public int row;
    public int winID;


    public ServerBoardField(int id, int winID, int col, int row) {
        pawn = id;

        this.col = col;
        this.row = row;
        this.winID = winID;

    }
}
