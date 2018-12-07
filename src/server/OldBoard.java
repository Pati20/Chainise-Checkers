package server;

import LocalApp.Board.BoardFactory;
import LocalApp.ClientApp;
import server.Player.*;
import server.Player.Bot.NormalBot;
import LocalApp.GameInstance;
import javafx.scene.paint.Color;
import server.ServerBoard.ServerBoard;
import server.ServerBoard.ServerBoardFactory;

import java.util.ArrayList;
import java.util.List;

public class OldBoard {
    public int boardID;
    public int playerIDHost;
    public int currentHumanCount = 0;
    public int numberOfHumans = 0;
    public int numberOfBost = 0;
    public List<Player> players = new ArrayList<>();
    public ClientApp client;
    public int numberOfPlayers;
    public int currentPlayerTurn = 0;
    public ServerBoard localboard;
    public Boolean gameReady = false;
    Color[] colors = {Color.LIGHTGRAY, Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA, Color.YELLOW, Color.ORANGE};


    //CONSTRUCTOR FOR SERVER
    public OldBoard(int bboardID, int pplayerID, int nnumberOfHuans, int nnumberOfBots) {
        this.boardID = bboardID;
        this.playerIDHost = pplayerID;
        this.currentHumanCount++;
        this.numberOfHumans = nnumberOfHuans;
        this.numberOfBost = nnumberOfBots;
        this.numberOfPlayers = numberOfHumans+numberOfBost;
        players.add(new Human(pplayerID,players.size(),colors[currentHumanCount-1]));
        for(int i=1;i <= numberOfBost;i++){
            players.add(new NormalBot(i,numberOfPlayers,this));
        }
    }


    public void boardGenerateServer() {//FOR SERVER
        localboard = ServerBoardFactory.createLocalBoard(61);
        localboard.serverBoardFields = localboard.constructBoard(numberOfPlayers);
        for(Player p : players)
            if(p.bot) p.gameStart();
    }


    public void newPlayerConected(int pplayerID) {
        currentHumanCount++;
        players.add(new Human(pplayerID, players.size(), colors[players.size()]));
    }
}
