package LocalApp;

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


public class ClientCommunicator extends Thread {

    //variables to communicate
    Socket socket = null;
    PrintWriter out = null;
    BufferedReader in = null;
    String input = "";
    List<String> comand = new ArrayList<>();

    //current game variables
    int clientID = 0;
    int playerID = 0;
    int numberOfHuman;
    int numberOfBots;
    int currentPlayerTurn;
    int numberOfPlayers;
    int witchBoardOnServer;
    boolean host;
    String address = new String();
    public Boolean activityOfClient=true;

    //reference to client
    ClientApp clientapp;

    public ClientCommunicator() {
    }

    public ClientCommunicator(ClientApp clientapp, int nnubmerOfHuman, int nnumberOfBots, boolean hhost, String address) {
        numberOfBots = nnumberOfBots;
        numberOfHuman = nnubmerOfHuman;
        this.address = address;
        System.out.println(nnubmerOfHuman + " " + nnumberOfBots);
        host = hhost;
        this.clientapp = clientapp;
        System.out.println("Client start");
        this.SocketListener();

    }

    public void threadEnd(){
        activityOfClient=false;
    }

    public void SocketListener() {

        try {
            socket = new Socket(address, 9999);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: localhost");
        } catch (IOException e) {
            System.out.println("No I/O");
        }
        System.out.println("Client socket " + socket.getLocalPort());
        conect();
    }


    public void conect() {
        out.println("CONECT");
        System.out.println("clientID " + clientID + " conect ");
        try {
            while (input.equals("")) {
                input = in.readLine();
            }
            //CLIENTID
            comand = parserOfCommand(input);
            if (clientID == 0) {
                clientID = parseInt(comand.get(0));
                System.out.println("client clientID " + clientID);
                socket = new Socket(address, parseInt(comand.get(1)));
                System.out.println("Client client id " + comand.get(0) + "  port  " + parseInt(comand.get(1)) + " ustawiono " + socket.getLocalPort());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (host) {
            begin();

        } else {
            getGames();

        }
    }
//Pati20
//pati244952

    public void begin() {
        System.out.println("BEGIN CLIENT");
        out.println("BEGIN" + " " + clientID + " " + numberOfHuman + " " + numberOfBots);
        try {
            input = "";
            while (input.equals("")) {
                input = in.readLine();
                if (input.equals("")) continue;
                //CLIENTID + BOARD_ON_SERVER_ID + COLOR
                comand = parserOfCommand(input);
                if (parseInt(comand.get(0)) == clientID) {
                    witchBoardOnServer = parseInt(comand.get(1));
                    //color = colorClass.color(parseInt(comand.get(2)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println("START " + clientID);
        this.start();
    }


    public void run() {
        System.out.println("WAITNING ON START");
        try {
            input = "";
            while (input.equals("")&&activityOfClient) {
                input = "";
                input = in.readLine();
                System.out.println(input);
                comand = parserOfCommand(input);
                System.out.println(comand);
                if ((comand.get(0).equals("START"))) {
                    System.out.println("CLIENT START " + clientID + " : " + input);
                    numberOfHuman = parseInt(comand.get(1));
                    numberOfBots = parseInt(comand.get(2));
                    clientapp.startLocalGame(playerID, numberOfHuman + numberOfBots);
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

    ///////////////////////////////////////////XROBIC ZROBIC///////////////////////////////////////////////////////////////
    public void getGames() {
        out.println("GETGAMES");
        int conectBoardID = 0;
        int cclientIDOnBoard = 0;
        try {
            input = "";
            while (input.equals("")&&activityOfClient) {
                input = in.readLine();
                comand = parserOfCommand(input); //BOARD_ID + NUMBER_OF._CURRENT_PLAYERS + FINAL_NUMBER_OF_PLAYERS + NUMBER_OF_BOTS
                if((comand.size() > 3))
                if ((comand.get(0).equals("BOARDS"))) {
/*
*/
                    System.out.println("get games " + input);
                    conectBoardID = parseInt(comand.get(1));
                    cclientIDOnBoard = parseInt(comand.get(2)) + parseInt(comand.get(4));
                    conectToGame(conectBoardID, cclientIDOnBoard); //cclientIDOnBoard = numberOfBots + numberOfConectedPlayers
                    break;
                } else {
                    input = "";
                } else {
                    activityOfClient=false;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void conectToGame(int conectBoardID, int cclientIDOnBoard) {
        this.playerID = cclientIDOnBoard;
        this.witchBoardOnServer = conectBoardID;
        System.out.println("conect to " + witchBoardOnServer + " " + playerID);
        out.println("CONECTTOGAME " + clientID + " " + conectBoardID);

        this.start();

    }

    public void terminateServer() {
        out.println("TERMINATE");
    }


    public void game() {
        JFrame dialog;
        out.println("GAMEREADY " + clientID);
        System.out.println("client start game już w game");
        try {
            input = "";
            while (input.equals("")&&activityOfClient) {
                input = in.readLine();
                System.out.println("client in " + input);
                comand = parserOfCommand(input);
                switch (comand.get(0)) {
                    case "YOURTURN":
                        System.out.println("client YOURTURN");
                        clientapp.gameInstance.unlockGame();
                        System.out.println("client AFTER UNLOCK");
                        while (clientapp.gameInstance.getActivityOfBoard()&&activityOfClient) {
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
                        if (clientapp.gameInstance.moveRegister.size() != 0) {
                            List<String> moveList = new ArrayList<>();
                            moveList.clear();
                            moveList = clientapp.gameInstance.moveRegister;
                            for (String s : moveList) {
                                moves = moves + " " + s;
                                System.out.println("moves clietn " + s);
                            }
                        } else moves = " PASS";
                        clientapp.gameInstance.lockGame();
                        out.println("ENDTURN" + moves);

                        clientapp.gameInstance.moveRegister.clear();
                        break;

                    case "MOVE":
                        clientapp.gameInstance.movePawnServer(clientapp.gameInstance.findField(parseInt(comand.get(1)), parseInt(comand.get(2))), clientapp.gameInstance.findField(parseInt(comand.get(3)), parseInt(comand.get(4))));
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
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        //      dialog = new DialogWindow("INCORRECT MOVE");
                        break;
                    case "YOUWON":

                        //    dialog = new DialogWindow("YOU WON /n GAME IS END");

                        break;

                    case "YOULOSE":
                        //  dialog = new DialogWindow("YOU LOSE /n GAME IS END");
                        break;
                }

                input = "";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


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

}