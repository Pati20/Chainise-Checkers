package App;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public Boolean activityOfClient = true;

    //reference to client
    ClientApp clientapp;

    public ClientViewer() {
    }

    public ClientViewer(ClientApp clientapp, int nnubmerOfHuman, int nnumberOfBots, boolean hhost, String address) {
        numberOfBots = nnumberOfBots;
        numberOfHuman = nnubmerOfHuman;
        this.address = address;
        System.out.println("Liczba graczy : botów "+nnubmerOfHuman + " " + nnumberOfBots);
        host = hhost;
        this.clientapp = clientapp;
        System.out.println("Kilent zaczął grę start");
        this.SocketListener();

    }

    public void threadEnd() {
        activityOfClient = false;
    }

    public  void log(String message){ System.out.println(message); }

    /**
     * Metoda odopwiedzialna za łączenie się z serwerem
     */
    public void SocketListener() {

        try {
            socket = new Socket(address, 9999);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            log("UnknownHost Exception: localhost");
        } catch (IOException e) {
            System.out.println("Socket Exception: Brak Input/Output");
        }
        log("Client socket " + socket.getLocalPort());
        conect();
    }

    public void conect() {
        out.println("CONECT");
        log("klientID " + klientID + " conect ");
        try {
            while (input.equals("")) { //Dopóki input jest pusty pobierz z socketa Stream
                input = in.readLine();
            }
            //CLIENTID
            comand = parserOfCommand(input);
            if (klientID == 0) {
                klientID = parseInt(comand.get(0));
                log("client klientID " + klientID);
                socket = new Socket(address, parseInt(comand.get(1)));
                log("Client client id " + comand.get(0) + "  port  " + parseInt(comand.get(1)) + " ustawiono " + socket.getLocalPort());
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
                    log("CLIENT START " + klientID + " : " + input);
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
                        log("get games " + input);
                        conectBoardID = parseInt(comand.get(1));
                        cclientIDOnBoard = parseInt(comand.get(2)) + parseInt(comand.get(4));
                        log("cclientIDOnBoard to "+ cclientIDOnBoard);
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
        log("conect to " + witchBoardOnServer + " " + graczID);
        out.println("CONECTTOGAME " + klientID + " " + conectBoardID);

        this.start();

    }

    public void terminateServer() {
        out.println("TERMINATE");
    }


    public void game() {
        JFrame dialog;
        out.println("GAMEREADY " + klientID);
      log("Klient jest w grze ");
        try {
            input = "";
            while (input.equals("") && activityOfClient) {
                input = in.readLine();
                log("client in " + input);
                comand = parserOfCommand(input);
                switch (comand.get(0)) {
                    case "YOURTURN":
                        log("client YOURTURN");
                        clientapp.instancjaGry.unlockGame();
                        log("client AFTER UNLOCK");
                        while (clientapp.instancjaGry.getAktywnyNaPlanszy() && activityOfClient) {
                            try {
                                Thread.sleep(250);
                            } catch (InterruptedException e) {
                                System.out.println("vuhguhgu");
                            }
                        }
                        log("waiting client ");
                        StringBuilder moves = new StringBuilder();
                        log("clietn waiting fo move ");
                        if (clientapp.instancjaGry.moveRegister.size() != 0) {
                            List<String> moveList = new ArrayList<>();
                            moveList.clear();
                            moveList = clientapp.instancjaGry.moveRegister;
                            for (String s : moveList) {
                                moves.append(" ").append(s);
                                log("moves clietn " + s);
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
     * @return
     */
    public List parserOfCommand(String line) {
        List<String> list = new ArrayList<>();
        while (!Objects.equals(line, "")) {
            if (line.contains(" ")) {
                list.add(line.substring(0, line.indexOf(" ")));
                line = line.substring(line.indexOf(" ") + 1);
            } else {
                list.add(line);
                line = "";
            }
        }
        return list;
    }


}