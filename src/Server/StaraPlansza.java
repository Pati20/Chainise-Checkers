package Server;

import App.ClientApp;
import App.Plansza.KoloryModeli;
import Server.Gracze.Człowiek;
import Server.Gracze.Gracz;
import Server.ServerPlansza.ServerPlansza;
import Server.ServerPlansza.ServerPlnanszaFabryka;

import java.util.ArrayList;
import java.util.List;


public class StaraPlansza {
    public int boardID;
    public int playerIDHost;
    public int currentHumanCount = 0;
    public int numberOfHumans = 0;
    public int numberOfBost = 0;
    public List<Gracz> players = new ArrayList<>();
    public ClientApp client;
    public int numberOfPlayers;
    public int currentPlayerTurn = 0;
    public ServerPlansza localboard;
    public Boolean gameReady = false;


    //CONSTRUCTOR FOR SERVER
    public StaraPlansza(int bboardID, int pplayerID, int nnumberOfHuans, int nnumberOfBots) {
        this.boardID = bboardID;
        this.playerIDHost = pplayerID;
        this.currentHumanCount++;
        this.numberOfHumans = nnumberOfHuans;
        this.numberOfBost = nnumberOfBots;
        this.numberOfPlayers = numberOfHumans+numberOfBost;
        players.add(new Człowiek(pplayerID,players.size(), KoloryModeli.Kolor.KoloryModeli(currentHumanCount-1)));
        for(int i=1;i <= numberOfBost;i++){
            //Dodać boty trzeba
        }
    }


    public void boardGenerateServer() {//FOR SERVER
        localboard = ServerPlnanszaFabryka.createLocalBoard(61);
        localboard.serverBoardFields = localboard.constructBoard(numberOfPlayers);
        for(Gracz p : players)
            if(p.bot) p.gameStart();
    }


    public void newPlayerConected(int pplayerID) {
        currentHumanCount++;
        players.add(new Człowiek(pplayerID, players.size(), KoloryModeli.Kolor.KoloryModeli(players.size())));
    }
}
