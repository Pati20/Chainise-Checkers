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

import static java.lang.Integer.parseInt;


public class ClientViewer extends Thread {

    // Zmienne odpowiedzialne za komunikację z serwerem
    Socket socket = null;
    PrintWriter out = null;
    BufferedReader in = null;
    String input = "";
    List<String> comand = new ArrayList<>();

    //Zmienne odpowiedzialne za przebieg gry
    int klientID = 0;
    int graczID = 0;
    int numberOfHuman;
    int numberOfBots;
    int currentPlayerTurn;
    int numberOfPlayers;
    int witchBoardOnServer;
    boolean host;
    String address = new String();
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

    /**
     * Metoda odopwiedzialna za łączenie się z serwerem
     */
    public void SocketListener() {

        try {
            socket = new Socket(address, 9999);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.out.println("UnknownHost Exception: localhost");
        } catch (IOException e) {
            System.out.println("Socket Exception: Brak Input/Output");
        }
        System.out.println("Client socket " + socket.getLocalPort());
        conect();
    }

    public void conect() {
        out.println("CONECT");
        System.out.println("klientID " + klientID + " conect ");
        try {
            while (input.equals("")) { //Dopóki input jest pusty pobierz z socketa Stream
                input = in.readLine();
            }
            //CLIENTID
            comand = parserOfCommand(input);
            if (klientID == 0) {
                klientID = parseInt(comand.get(0));
                System.out.println("client klientID " + klientID);
                socket = new Socket(address, parseInt(comand.get(1)));
                System.out.println("Client client id " + comand.get(0) + "  port  " + parseInt(comand.get(1)) + " ustawiono " + socket.getLocalPort());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (host) { begin(); }
        else { getGames(); }
    }


    public void begin() {
        System.out.println("BEGIN CLIENT");
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
        System.out.println("WAITNING ON START");
        try {
            input = "";
            while (input.equals("") && activityOfClient) {
                input = "";
                input = in.readLine();
                System.out.println(input);
                comand = parserOfCommand(input);
                System.out.println(comand);
                if ((comand.get(0).equals("START"))) {
                    System.out.println("CLIENT START " + klientID + " : " + input);
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
                        System.out.println("get games " + input);
                        conectBoardID = parseInt(comand.get(1));
                        cclientIDOnBoard = parseInt(comand.get(2)) + parseInt(comand.get(4));
                        System.out.println("cclientIDOnBoard to "+ cclientIDOnBoard);
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
        System.out.println("conect to " + witchBoardOnServer + " " + graczID);
        out.println("CONECTTOGAME " + klientID + " " + conectBoardID);

        this.start();

    }

    public void terminateServer() {
        out.println("TERMINATE");
    }


    public void game() {
        JFrame dialog;
        out.println("GAMEREADY " + klientID);
        System.out.println("Klient jest w grze ");
        try {
            input = "";
            while (input.equals("") && activityOfClient) {
                input = in.readLine();
                System.out.println("client in " + input);
                comand = parserOfCommand(input);
                switch (comand.get(0)) {
                    case "YOURTURN":
                        System.out.println("client YOURTURN");
                        clientapp.instancjaGry.unlockGame();
                        System.out.println("client AFTER UNLOCK");
                        while (clientapp.instancjaGry.getAktywnyNaPlanszy() && activityOfClient) {
                            try {
                                Thread.sleep(250);
                                //System.out.println("oczekiwanie ");
                            } catch (InterruptedException e) {
                                System.out.println("vuhguhgu");
                            }
                        }
                        System.out.println("waiting client ");
                        String moves = "";
                        System.out.println("clietn waiting fo move ");
                        if (clientapp.instancjaGry.moveRegister.size() != 0) {
                            List<String> moveList = new ArrayList<>();
                            moveList.clear();
                            moveList = clientapp.instancjaGry.moveRegister;
                            for (String s : moveList) {
                                moves = moves + " " + s;
                                System.out.println("moves clietn " + s);
                            }
                        } else moves = " PASS";
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
        while (line != "") {
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

<<<<<<< HEAD
//    public static void main(String args[]){
//        // public ClientViewer(ClientApp clientapp, int nnubmerOfHuman, int nnumberOfBots, boolean hhost, String address)
//        ClientViewer cw = new ClientViewer(new ClientApp(), 2,3,true,"localhost");
//
//        String command = "CLIENT_START IN_GAME CONNECT    ";
//        List<String> testArray = new ArrayList<>();
//
//        testArray = cw.parserOfCommand(command);
//        for (int i =0; i < testArray.size() -1; i++) {
//            System.out.println("ELEMENT "+i +" to "+  "\""+ testArray.get(i) + "\"");
//        }
//    }

}
=======
}
>>>>>>> c189367d59dd0796297e3e617178c2298f5e1114
