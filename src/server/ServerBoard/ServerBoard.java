package server.ServerBoard;

import java.util.ArrayList;

import static java.lang.Math.abs;

public abstract class ServerBoard {

    public ArrayList<ServerBoardField> serverBoardFields;
    int playerID;

    public abstract ArrayList<ServerBoardField> constructBoard(int numberOfPlayers);

    //to setting player id - only for server_do_not_use use
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    //move pawn from oldPos to newPos
    public boolean movePawn(ServerBoardField oldPos, ServerBoardField newPos) {
        if (testMove(oldPos, newPos)) {
            newPos.pawn = oldPos.pawn;
            oldPos.pawn = 0;
            return true;
        }
        return false;
    }

    //move pawn by server_do_not_use //by Akageneko
    public void movePawnServer(ServerBoardField oldPos, ServerBoardField newPos) {
        newPos.pawn = oldPos.pawn;
        oldPos.pawn = 0;
    }

    //return field with specified column and row
    public ServerBoardField findField(int col, int row) {
        for (ServerBoardField field : serverBoardFields) {
            if (field.col == col && field.row == row) {
                return field;
            }
        }
        return null;
    }

    //check correctness of move
    public boolean testMove(ServerBoardField oldPos, ServerBoardField newPos) {
        if (newPos.pawn == 0) {
            if (true) {
                if ((abs(oldPos.col - newPos.col) <= 2) && (abs(oldPos.row - newPos.row) <= 1)) {
                    //activityOfBoard=false;
                    return true;
                }
            }
            if (true) {
                if (oldPos.row == newPos.row) {
                    //right
                    if (newPos.col == oldPos.col + 4) {
                        if (findField(oldPos.col + 2, oldPos.row).pawn > 0) {
                            return true;
                        }
                    }
                    //left
                    if (newPos.col == oldPos.col - 4) {
                        if (findField(oldPos.col - 2, oldPos.row).pawn > 0) {
                            return true;
                        }
                    }
                }

                if (newPos.row == oldPos.row + 2) {
                    //right up
                    if (newPos.col == oldPos.col + 2) {
                        if (findField(oldPos.col + 1, oldPos.row + 1).pawn > 0) {
                            return true;
                        }
                    }
                    //left up
                    if (newPos.col == oldPos.col - 2) {
                        if (findField(oldPos.col - 1, oldPos.row + 1).pawn > 0) {
                            return true;
                        }
                    }
                }

                if (newPos.row == oldPos.row - 2) {
                    //right down
                    if (newPos.col == oldPos.col + 2) {
                        if (findField(oldPos.col + 1, oldPos.row - 1).pawn > 0) {
                            return true;
                        }
                    }
                    //left down
                    if (newPos.col == oldPos.col - 2) {
                        if (findField(oldPos.col - 1, oldPos.row - 1).pawn > 0) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}


