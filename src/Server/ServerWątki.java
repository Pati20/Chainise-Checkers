package Server;

//package Server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;


public class ServerWątki extends Thread {
    public Server serverSide;
    public int clientID;
    public Socket client = null;
    public int port = 0;
    public ServerSocket serverSocket;
    public int boardID;
    BufferedReader in = null;
    PrintWriter out = null;
    String line = "";

    public ServerWątki(int pport, int clientNumber, Server sserverSide, ServerSocket sserverSocket) {
        this.port = pport;
        try {
            client = sserverSocket.accept();
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("NoT ACDEPTED OR CANNOT MAKE THREAT SOSCKET ON PORT: " + port);
        }
        this.clientID = clientNumber;
        this.serverSide = sserverSide;

    }


    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("IO ERROR");
        }
        while (line != null) {
            try {
                line = in.readLine();
                System.out.println("LISTEN: " + line);
                out.println(DoSomething(line));
                ///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!//
                //line = null;
            } catch (IOException e) {
                System.out.println("Read failed " + clientID);
                line = null;
            }
        }
    }

    private String DoSomething(String line) {
        String response = "";
        List<String> comand = parserOfCommand(line);
        int witch;
        int work;
        System.out.println("comand " + comand.get(0));
        switch (comand.get(0)) {
            case "CONECT":
                response = clientID + " " + port;
                System.out.println("SERVER CONECTED PORT " + port + "   " + clientID);

                break;

            case "BEGIN"://BEGIN + CLIENT_ID + NUMBER_OF_HUMANS + NUMBER_OF_BOTS
                witch = addNewBoard(parseInt(comand.get(1)), parseInt(comand.get(2)), parseInt(comand.get(3)));
                boardID = witch;
                System.out.println("server human cclietnID " + serverSide.boards.get(boardID).players.get(0).clientID + " : " + serverSide.boards.get(boardID).players.get(serverSide.boards.get(boardID).currentPlayerTurn).clientID);
                response = comand.get(1) + " " + boardID + " " + 0;
                break;

            case "GETGAMES"://GETGAMES + CLIENT_ID + BOARD_ID
                for (StaraPlansza b : serverSide.boards) {
                    if (b.numberOfHumans != b.currentHumanCount)
                        if (response.equals(""))
                            response = "BOARDS " + b.boardID + " " + b.currentHumanCount + " " + b.numberOfHumans + " " + b.numberOfBost;
                        else
                            response = response + " " + b.boardID + " " + b.currentHumanCount + " " + b.numberOfHumans + " " + b.numberOfBost;
                }

                break;

            case "START": //START + CLIENT_ID + BOARD_ID
                if (serverSide.boards.get(boardID).numberOfHumans == serverSide.boards.get(boardID).currentHumanCount) {
                    serverSide.boards.get(boardID).boardGenerateServer();
                    response = "START " + serverSide.boards.get(boardID).numberOfHumans + " " + serverSide.boards.get(boardID).numberOfBost;
                } else {
                    response = "STOP" + " " + comand.get(1);
                }

                break;

            case "GAMEREADY":
                System.out.println("server gamerdy " + clientID + "    " + serverSide.boards.get(boardID).players.get(serverSide.boards.get(boardID).currentPlayerTurn).clientID);
                if (clientID == serverSide.boards.get(boardID).players.get(serverSide.boards.get(boardID).currentPlayerTurn).clientID) {
                    response = "YOURTURN " + clientID;
                    System.out.println("server yourturn " + clientID);
                }

                break;

            case "CONECTTOGAME"://CONECT + CLIENT_ID + BOARD_ID(od 0)
                boardID = parseInt(comand.get(2));
                System.out.println("SERVER NEW BOARD ID " + boardID);
                serverSide.boards.get(boardID).newPlayerConected(parseInt(comand.get(1)));

                if (serverSide.boards.get(boardID).numberOfHumans == serverSide.boards.get(boardID).currentHumanCount) {
                    for (ServerWątki t : serverSide.threadServer) {
                        if (t.boardID == boardID) {
                            if (t.clientID == clientID)
                                response = "START " + serverSide.boards.get(boardID).numberOfHumans + " " + serverSide.boards.get(boardID).numberOfBost;
                            else {
                                t.out.println("START " + serverSide.boards.get(boardID).numberOfHumans + " " + serverSide.boards.get(boardID).numberOfBost);
                            }
                        }
                    }
                    serverSide.boards.get(boardID).boardGenerateServer();
                }

                break;

            case "ENDTURN": //ROUNDEND + MOVES
                Boolean correctMove = true;
                Boolean botTurn = true;

                if (!comand.get(1).equals("PASS"))
                    for (int i = 1; i < comand.size(); i = i + 4) {
                        if (!serverSide.boards.get(boardID).localboard.testMove(serverSide.boards.get(boardID).localboard.findField(parseInt(comand.get(i)), parseInt(comand.get(i + 1))), serverSide.boards.get(boardID).localboard.findField(parseInt(comand.get(i + 2)), parseInt(comand.get(i + 3))))) {
                            correctMove = false;
                            break;
                        }
                    }

                if (correctMove) {

                    if (!comand.get(1).equals("PASS"))
                        serverSide.boards.get(boardID).localboard.movePawnServer(serverSide.boards.get(boardID).localboard.findField(parseInt(comand.get(1)), parseInt(comand.get(2))), serverSide.boards.get(boardID).localboard.findField(parseInt(comand.get(comand.size() - 2)), parseInt(comand.get(comand.size() - 1))));

                    if (serverSide.boards.get(boardID).currentPlayerTurn + 1 < serverSide.boards.get(boardID).numberOfPlayers)
                        serverSide.boards.get(boardID).currentPlayerTurn++;
                    else
                        serverSide.boards.get(boardID).currentPlayerTurn = 0;

                    System.out.println("server current player ID " + serverSide.boards.get(boardID).currentPlayerTurn);

                    serverSide.boards.get(boardID).localboard.setPlayerID(serverSide.boards.get(boardID).currentPlayerTurn);

                    for (ServerWątki t : serverSide.threadServer) {
                        if (t.boardID == boardID) {
                            if (t.clientID == clientID)
                                out.println("MOVEACCEPTED");
                            else if (comand.get(1).equals("PASS"))
                                t.out.println("MOVEPASS");
                            else
                                t.out.println("MOVE " + comand.get(1) + " " + comand.get(2) + " " + comand.get(comand.size() - 2) + " " + comand.get(comand.size() - 1));

                            if (!serverSide.boards.get(boardID).players.get(serverSide.boards.get(boardID).currentPlayerTurn).bot)
                                if (t.clientID == serverSide.boards.get(boardID).players.get(serverSide.boards.get(boardID).currentPlayerTurn).clientID) {
                                    t.out.println("YOURTURN");
                                    System.out.println("Yourturn " + t.clientID);
                                }


                        }


                    }
                    while (serverSide.boards.get(boardID).players.get(serverSide.boards.get(boardID).currentPlayerTurn).bot)
                        botMove();

                    System.out.println("server moves itp " + comand);

                } else response = "MOVEDECLINED";

                break;


            case "DISCONECT"://DISCONECT + CLIENT_ID
                line = null;
                try {
                    client.close();
                } catch (IOException e) {
                    System.out.println("UNABLE TO CLOSE SOCKET");
                }

                break;


            default:
                System.out.println("ERROR WHILE READING FROM CLIENT - UNKNOW COMMAND");

                break;

            case "TERMINATE":
                interrupt();
                break;
        }

        System.out.println("server out " + response);
        comand.clear();
        return response;
    }


    void botMove() {
        List<String> movelist = parserOfCommand( serverSide.boards.get(boardID).players.get(serverSide.boards.get(boardID).currentPlayerTurn).Turn());
        System.out.println("server moves "+movelist);
        if (!movelist.get(1).equals("PASS"))
            serverSide.boards.get(boardID).localboard.movePawnServer(serverSide.boards.get(boardID).localboard.findField(parseInt(movelist.get(1)), parseInt(movelist.get(2))), serverSide.boards.get(boardID).localboard.findField(parseInt(movelist.get(movelist.size() - 2)), parseInt(movelist.get(movelist.size() - 1))));

        if (serverSide.boards.get(boardID).currentPlayerTurn + 1 < serverSide.boards.get(boardID).numberOfPlayers)
            serverSide.boards.get(boardID).currentPlayerTurn++;
        else
            serverSide.boards.get(boardID).currentPlayerTurn = 0;

        System.out.println("server current player ID " + serverSide.boards.get(boardID).currentPlayerTurn);

        serverSide.boards.get(boardID).localboard.setPlayerID(serverSide.boards.get(boardID).currentPlayerTurn);

        for (ServerWątki t : serverSide.threadServer) {
            if (t.boardID == boardID) {
                if (movelist.get(1).equals("PASS"))
                    t.out.println("MOVEPASS");
                else
                    t.out.println("MOVE " + movelist.get(1) + " " + movelist.get(2) + " " + movelist.get(movelist.size() - 2) + " " + movelist.get(movelist.size() - 1));

                if (!serverSide.boards.get(boardID).players.get(serverSide.boards.get(boardID).currentPlayerTurn).bot)
                    if (t.clientID == serverSide.boards.get(boardID).players.get(serverSide.boards.get(boardID).currentPlayerTurn).clientID)
                        t.out.println("YOURTURN");


            }
        }


    }

    private List parserOfCommand(String line) {
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


    public int addNewBoard(int playerID, int numberOfHuans, int numberOfBots) {
        int witch = serverSide.boards.size();
        serverSide.boards.add(new StaraPlansza(witch, playerID, numberOfHuans, numberOfBots));
        return witch;
    }


}
