package App.Server;

import App.ClientApp;
import App.Plansza.KoloryModeli;
import App.Server.Gracze.Bot.BotGame;
import App.Server.Gracze.Człowiek;
import App.Server.Gracze.Gracz;
import App.Server.ServerPlansza.ServerPlansza;
import App.Server.ServerPlansza.ServerPlnanszaFabryka;

import java.util.ArrayList;
import java.util.List;


public class StaraPlansza {
    public int planszaID;
    public int graczIDHost;
    public int licznikLudzi = 0;
    public int numberOfHumans;
    public int numberOfBost;
    public List<Gracz> players = new ArrayList<>();
    public ClientApp client;
    public int numberOfPlayers;
    public int pbecnaTuraGracza = 0;
    public ServerPlansza localboard;
    public Boolean gotowaGra = false;


    //CONSTRUCTOR FOR SERVER
    public StaraPlansza(int bboardID, int pplayerID, int nnumberOfHuans, int nnumberOfBots) {
        this.planszaID = bboardID;
        this.graczIDHost = pplayerID;
        this.licznikLudzi++;
        this.numberOfHumans = nnumberOfHuans;
        this.numberOfBost = nnumberOfBots;
        this.numberOfPlayers = numberOfHumans+numberOfBost;
        players.add(new Człowiek(pplayerID,players.size(), KoloryModeli.Kolor.Kolory(licznikLudzi -1)));
        for(int i=1;i <= numberOfBost;i++){
            players.add(new BotGame(i,numberOfPlayers,this));        }
    }


    public void boardGenerateServer() {//FOR SERVER
        localboard = ServerPlnanszaFabryka.createLocalBoard(61);
        localboard.serverBoardFields = localboard.constructBoard(numberOfPlayers);
        for(Gracz p : players)
            if(p.bot) p.gameStart();
    }


    public void newPlayerConected(int pplayerID) {
        licznikLudzi++;
        players.add(new Człowiek(pplayerID, players.size(), KoloryModeli.Kolor.Kolory(players.size())));
    }
}
