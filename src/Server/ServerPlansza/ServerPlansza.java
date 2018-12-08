package Server.ServerPlansza;

import java.util.ArrayList;

import static java.lang.Math.abs;


public abstract class ServerPlansza {

    public ArrayList<ServerPlanszaPola> serverBoardFields;
    int playerID;

    public abstract ArrayList<ServerPlanszaPola> constructBoard(int numberOfPlayers);

    /**
     * Ustawianie id gracza - tylko dla serwea
     */
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    /**
     * Poruszanie pionkiem ze starej pozycji na nową
     */
    public boolean movePawn(ServerPlanszaPola oldPos, ServerPlanszaPola newPos) {
        if (testMove(oldPos, newPos)) {
            newPos.pionek = oldPos.pionek;
            oldPos.pionek = 0;
            return true;
        }
        return false;
    }

    /**
     * Poruszanie się pionkiem przez server
     * @param oldPos
     * @param newPos
     */
    public void movePawnServer(ServerPlanszaPola oldPos, ServerPlanszaPola newPos) {
        newPos.pionek = oldPos.pionek;
        oldPos.pionek = 0;
    }

    /**
     * Zwraca pole z określoną kolumną i wierszem
     * @param col
     * @param row
     * @return field or null
     */
    public ServerPlanszaPola findField(int col, int row) {
        for (ServerPlanszaPola field : serverBoardFields) {
            if (field.col == col && field.row == row) {
                return field;
            }
        }
        return null;
    }

    /**Sprawdzanie poprawności ruchu
     * @param oldPos
     * @param newPos
     * @return true or false
     */
    public boolean testMove(ServerPlanszaPola oldPos, ServerPlanszaPola newPos) {
        if (newPos.pionek == 0) {
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
                        if (findField(oldPos.col + 2, oldPos.row).pionek > 0) {
                            return true;
                        }
                    }
                    //left
                    if (newPos.col == oldPos.col - 4) {
                        if (findField(oldPos.col - 2, oldPos.row).pionek > 0) {
                            return true;
                        }
                    }
                }

                if (newPos.row == oldPos.row + 2) {
                    //right up
                    if (newPos.col == oldPos.col + 2) {
                        if (findField(oldPos.col + 1, oldPos.row + 1).pionek > 0) {
                            return true;
                        }
                    }
                    //left up
                    if (newPos.col == oldPos.col - 2) {
                        if (findField(oldPos.col - 1, oldPos.row + 1).pionek > 0) {
                            return true;
                        }
                    }
                }

                if (newPos.row == oldPos.row - 2) {
                    //right down
                    if (newPos.col == oldPos.col + 2) {
                        if (findField(oldPos.col + 1, oldPos.row - 1).pionek > 0) {
                            return true;
                        }
                    }
                    //left down
                    if (newPos.col == oldPos.col - 2) {
                        if (findField(oldPos.col - 1, oldPos.row - 1).pionek > 0) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


}


