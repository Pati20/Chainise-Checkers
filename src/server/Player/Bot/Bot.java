package server.Player.Bot;

import server.OldBoard;
import server.Player.Player;
import server.ServerBoard.ServerBoardField;

import java.util.ArrayList;
import java.util.List;

public class Bot extends Player{

    int numberOfPlayers;
    int ID;
    int corner;
    OldBoard board;

    public Bot(int nnumber, int numberOfPlayers,OldBoard board) {
        super(nnumber, numberOfPlayers,board);
        this.numberOfPlayers = numberOfPlayers;
        this.ID = nnumber;
        this.corner = ID+1;
        this.board = board;
        bot = true;

    }

    public List<Pawn> setMyPawns(OldBoard board){
        List<Pawn> pawn = new ArrayList<>();
        for( ServerBoardField field : board.localboard.serverBoardFields){
            if(field.pawn == corner) pawn.add(new Pawn(field.col, field.row));
        }

        return pawn;
    }

    public List<Pawn> setMyPawnsWin(OldBoard board){
        List<Pawn> pawn = new ArrayList<>();
        for( ServerBoardField field : board.localboard.serverBoardFields){
            if(field.winID == corner) pawn.add(new Pawn(field.col, field.row));
        }

        return pawn;
    }


}
