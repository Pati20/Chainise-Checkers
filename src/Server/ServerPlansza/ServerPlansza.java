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
            if (field.kolumna == col && field.row == row) {
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
            if ((abs(oldPos.kolumna - newPos.kolumna) <= 2) && (abs(oldPos.row - newPos.row) <= 1)) {
                //aktywnyNaPlanszy=false;
                return true;
            }
            if (oldPos.row == newPos.row) {
                //right
                if (newPos.kolumna == oldPos.kolumna + 4) {
                    if (findField(oldPos.kolumna + 2, oldPos.row).pionek > 0) {
                        return true;
                    }
                }
                //left
                if (newPos.kolumna == oldPos.kolumna - 4) {
                    if (findField(oldPos.kolumna - 2, oldPos.row).pionek > 0) {
                        return true;
                    }
                }
            }

            if (newPos.row == oldPos.row + 2) {
                //right up
                if (newPos.kolumna == oldPos.kolumna + 2) {
                    if (findField(oldPos.kolumna + 1, oldPos.row + 1).pionek > 0) {
                        return true;
                    }
                }
                //left up
                if (newPos.kolumna == oldPos.kolumna - 2) {
                    if (findField(oldPos.kolumna - 1, oldPos.row + 1).pionek > 0) {
                        return true;
                    }
                }
            }

            if (newPos.row == oldPos.row - 2) {
                //right down
                if (newPos.kolumna == oldPos.kolumna + 2) {
                    if (findField(oldPos.kolumna + 1, oldPos.row - 1).pionek > 0) {
                        return true;
                    }
                }
                //left down
                if (newPos.kolumna == oldPos.kolumna - 2) {
                    return findField(oldPos.kolumna - 1, oldPos.row - 1).pionek > 0;
                }
            }
        }
        return false;
    }


}


