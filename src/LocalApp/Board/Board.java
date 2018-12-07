package LocalApp.Board;

import LocalApp.GameInstance;

import java.util.ArrayList;

public abstract class Board {
    public abstract ArrayList<BoardField> constructBoard(GameInstance gameInstance, int numberOfPlayers);
}


