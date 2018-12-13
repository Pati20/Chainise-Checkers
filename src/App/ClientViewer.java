package App;

import App.Decorators.ClientMessageDecorator;
import App.Decorators.MessageDecorator;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Integer.parseInt;


public class ClientViewer extends Thread {

    // Zmienne odpowiedzialne za komunikację z serwerem
    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private String input = "";
    private List<String> comand = new ArrayList<>();

    //Zmienne odpowiedzialne za przebieg gry
    private int klientID = 0;
    private int graczID = 0;
    private int numberOfHuman;
    private int numberOfBots;
    private int currentPlayerTurn;
    private int numberOfPlayers;
    private  int witchBoardOnServer;
    private boolean host;
    private String address;
    Boolean activityOfClient = true;
    boolean goodConnection;

    //reference to client
    ClientApp clientapp;

    public ClientViewer(ClientApp clientapp, int nubmerOfHuman, int numberOfBots, boolean host, String address) {
        this.numberOfBots = numberOfBots;
        numberOfHuman = nubmerOfHuman;
        this.address = address;
        log("Liczba graczy "+nubmerOfHuman + " : botów " + numberOfBots);
        this.host = host;
        this.clientapp = clientapp;
        log("Zaczął grę start");
        this.SocketListener();

    }

    /**
     * Metoda która wypisuje nam informacje o aktualnym przebiegu rozgrywki ze strony Clienta
     * Specjalnie nadpisujemy obiekt App.Decorator, by za każdym razem otrzymywać aktualną datę
     * @param message
     */
    public  void log(String message){
        MessageDecorator m = new ClientMessageDecorator();
        System.out.println(m.log(message)); }

    /**
     * Metoda odopwiedzialna za łączenie się z serwerem
     */
     void SocketListener() {
         goodConnection = true;
        try {
            socket = new Socket(address, 9999);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            goodConnection =false;
            log("UnknownHost Exception: localhost");
        } catch (IOException e) {
            goodConnection =false;
            log("Socket Exception: Brak Input/Output");
        }
       if(goodConnection){
           log("Socket " + socket.getLocalPort());
           conect();
       }else {
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Brak aktywnego servera");
           alert.setHeaderText("Informacja");
           alert.setContentText("Proszę o uruchomienie servera przed rozgrywką.");
           alert.show();
       }
    }

     void conect() {
        out.println("CONECT");
        log("ClientID " + klientID + " conect ");
        try {
            while (input.equals("")) { //Dopóki input jest pusty pobierz z socketa Stream
                input = in.readLine();
            }
            //CLIENTID
            comand = parserOfCommand(input);
            if (klientID == 0) {
                klientID = parseInt(comand.get(0));
                log("klientID " + klientID);
                socket = new Socket(address, parseInt(comand.get(1)));
                log("klient id " + comand.get(0) + "  port  " + parseInt(comand.get(1)) + " ustawiono " + socket.getLocalPort());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (host) { begin(); }
        else { getGames(); }
    }


    public void begin() {
        log("BEGIN CLIENT");
        out.println("BEGIN" + " " + klientID + " " + numberOfHuman + " " + numberOfBots);
        try {
            input = "";
            while (input.equals("")) {
                input = in.readLine();
                if (input.equals("")) continue;
                comand = parserOfCommand(input);
                if (parseInt(comand.get(0)) == klientID) {
                    witchBoardOnServer = parseInt(comand.get(1));
                }
            }
        } catch (IOException e) {
            log("Zerwano połączenie");
            e.printStackTrace();
        }
        out.println("START " + klientID);
        this.start();
    }


    public void run() {
        log("WAITNING ON START");
        try {
            input = "";
            while (input.equals("") && activityOfClient) {
                input = "";
                input = in.readLine();
                log(input);
                comand = parserOfCommand(input);
                System.out.println(comand);
                if ((comand.get(0).equals("START"))) {
                    log("START " + klientID + " : " + input);
                    numberOfHuman = parseInt(comand.get(1));
                    numberOfBots = parseInt(comand.get(2));
                    clientapp.startLocalGame(graczID, numberOfHuman + numberOfBots);
                    game();
                    break;
                } else {

                    input = "";
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getGames() {
        out.println("GETGAMES");
        int conectBoardID = 0;
        int cclientIDOnBoard = 0;
        try {
            input = "";
            while (input.equals("") && activityOfClient) {
                input = in.readLine();
                comand = parserOfCommand(input);
                if ((comand.size() > 3))
                    if ((comand.get(0).equals("BOARDS"))) {
                        /*
                         */
                        log("Get Games " + input);
                        conectBoardID = parseInt(comand.get(1));
                        cclientIDOnBoard = parseInt(comand.get(2)) + parseInt(comand.get(4));
                        log("ID On Board to "+ cclientIDOnBoard);
                        conectToGame(conectBoardID, cclientIDOnBoard);
                        break;
                    } else {
                        input = "";
                    }
                else {
                    activityOfClient = false;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void conectToGame(int conectBoardID, int cclientIDOnBoard) {
        this.graczID = cclientIDOnBoard;
        this.witchBoardOnServer = conectBoardID;
        log("Conect to " + witchBoardOnServer + " " + graczID);
        out.println("CONECTTOGAME " + klientID + " " + conectBoardID);

        this.start();

    }

    public void terminateServer() {
        out.println("TERMINATE");
    }


    public void game() {
        out.println("GAMEREADY " + klientID);
      log("Jest w grze ");
        try {
            input = "";
            while (input.equals("") && activityOfClient) {
                input = in.readLine();
                log("In " + input);
                comand = parserOfCommand(input);
                switch (comand.get(0)) {
                    case "YOURTURN":
                        log("YOURTURN");
                        clientapp.instancjaGry.unlockGame();
                        log("AFTER UNLOCK");
                        while (clientapp.instancjaGry.getAktywnyNaPlanszy() && activityOfClient) {
                            try {
                                Thread.sleep(250);
                            } catch (InterruptedException e) {
                                System.out.println("vuhguhgu");
                            }
                        }
                        log("Waiting ");
                        StringBuilder moves = new StringBuilder();
                        log("Waiting for move ");
                        if (clientapp.instancjaGry.moveRegister.size() != 0) {
                            List<String> moveList = new ArrayList<>();
                            moveList.clear();
                            moveList = clientapp.instancjaGry.moveRegister;
                            for (String s : moveList) {
                                moves.append(" ").append(s);
                                log("Moves  " + s);
                            }
                        } else moves = new StringBuilder(" PASS");
                        clientapp.instancjaGry.lockGame();
                        out.println("ENDTURN" + moves);

                        clientapp.instancjaGry.moveRegister.clear();
                        break;

                    case "MOVE":
                        clientapp.instancjaGry.movePawnServer(clientapp.instancjaGry.findField(parseInt(comand.get(1)), parseInt(comand.get(2))), clientapp.instancjaGry.findField(parseInt(comand.get(3)), parseInt(comand.get(4))));
                        break;

                    case "MOVEPASS":
                        if (currentPlayerTurn + 1 < numberOfPlayers)
                            currentPlayerTurn++;
                        else
                            currentPlayerTurn = 0;

                        break;

                    case "MOVEACCEPTED":
                        if (currentPlayerTurn + 1 < numberOfPlayers)
                            currentPlayerTurn++;
                        else
                            currentPlayerTurn = 0;

                        break;

                    case "MOVEDECLINED":
                        break;
                    case "YOUWON":
                        break;

                    case "YOULOSE":
                        break;
                }

                input = "";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * Metoda króra wycina nam komendy i zwraca je jako Arraylist
     * @param line
     * @return list
     */
    public List parserOfCommand(String line) {
        List<String> list = new ArrayList<>();
        while (line != ("")) {
            if (line.indexOf(" ") != -1) {
                list.add(line.substring(0, line.indexOf(" ")));
                line = line.substring(line.indexOf(" ") + 1);
            } else {
                list.add(line);
                line = "";
            }
        }
        return list;
    }

    Boolean getGoodConnection(){return goodConnection;}

}