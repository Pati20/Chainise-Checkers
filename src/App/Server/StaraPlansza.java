package App.Server;

import App.Plansza.KoloryModeli;
import App.Server.Gracze.Bot.BotGame;
import App.Server.Gracze.Człowiek;
import App.Server.Gracze.Gracz;
import App.Server.ServerPlansza.ServerPlansza;
import App.Server.ServerPlansza.ServerPlnanszaFabryka;

import java.util.ArrayList;
import java.util.List;


public class StaraPlansza {
    int planszaID;
    int graczIDHost;
    int licznikLudzi = 0;
    int numberOfHumans;
    int numberOfBost;
    List<Gracz> players = new ArrayList<>();
    public int numberOfPlayers;
    int pbecnaTuraGracza = 0;
    public ServerPlansza localboard;

    /**
     * Przygotowywanie planszy dla serwera, tworzenie botów,planszy, oraz graczy
     * @param planszaID -identyfikator planszy
     * @param graczID - identyfikator gracza
     * @param numberofHumans -liczba graczy
     * @param numberOfBots - liczba botów
     */
    public StaraPlansza(int planszaID, int graczID, int numberofHumans, int numberOfBots) {
        this.planszaID = planszaID;
        this.graczIDHost = graczID;
        this.licznikLudzi++;
        this.numberOfHumans = numberofHumans;
        this.numberOfBost = numberOfBots;
        this.numberOfPlayers = numberOfHumans + numberOfBost;
        players.add(new Człowiek(graczID, players.size(), KoloryModeli.Kolor.Kolory(licznikLudzi - 1)));
        for (int i = 1; i <= numberOfBost; i++) {
            players.add(new BotGame(i, numberOfPlayers, this));
        }
    }


    /**
     * Metoda odpowiedzialna za tworzenie planszy do serwera.
     */
     void boardGenerateServer() {
        localboard = ServerPlnanszaFabryka.createLocalBoard(61);
        localboard.serverBoardFields = localboard.constructBoard(numberOfPlayers);
        for (Gracz p : players)
            if (p.bot) p.gameStart();
    }


    /**
     * Metoda odpowiedzialna za dodawanie nowych graczy do rozgrywki .
     * @param pplayerID
     */
     void newPlayerConected(int pplayerID) {
        licznikLudzi++;
        players.add(new Człowiek(pplayerID, players.size(), KoloryModeli.Kolor.Kolory(players.size())));
    }
}
