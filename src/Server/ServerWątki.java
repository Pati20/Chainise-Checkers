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
import java.util.Objects;

import static java.lang.Integer.parseInt;


public class ServerWątki extends Thread {
    public Server server;
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
        this.server = sserverSide;

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
            } catch (IOException e) {
                System.out.println("Read failed " + clientID);
                line = null;
            }
        }
    }

    private String DoSomething(String line) {
        StringBuilder response = new StringBuilder();
        List<String> comand = parserOfCommand(line);
        int witch;
        int work;
        System.out.println("comand " + comand.get(0));
        switch (comand.get(0)) {
            case "CONECT":
                response = new StringBuilder(clientID + " " + port);
                System.out.println("SERVER CONECTED PORT " + port + "   " + clientID);

                break;

            case "BEGIN":
                witch = addNewBoard(parseInt(comand.get(1)), parseInt(comand.get(2)), parseInt(comand.get(3)));
                boardID = witch;
                System.out.println("server human cclietnID " + server.boards.get(boardID).players.get(0).clientID + " : " + server.boards.get(boardID).players.get(server.boards.get(boardID).pbecnaTuraGracza).clientID);
                response = new StringBuilder(comand.get(1) + " " + boardID + " " + 0);
                break;

            case "GETGAMES"://GETGAMES + CLIENT_ID + BOARD_ID
                for (StaraPlansza b : server.boards) {
                    if (b.numberOfHumans != b.licznikLudzi)
                        if (response.toString().equals(""))
                            response = new StringBuilder("BOARDS " + b.planszaID + " " + b.licznikLudzi + " " + b.numberOfHumans + " " + b.numberOfBost);
                        else
                            response.append(" ").append(b.planszaID).append(" ").append(b.licznikLudzi).append(" ").append(b.numberOfHumans).append(" ").append(b.numberOfBost);
                }

                break;

            case "START": //START + CLIENT_ID + BOARD_ID
                if (server.boards.get(boardID).numberOfHumans == server.boards.get(boardID).licznikLudzi) {
                    server.boards.get(boardID).boardGenerateServer();
                    response = new StringBuilder("START " + server.boards.get(boardID).numberOfHumans + " " + server.boards.get(boardID).numberOfBost);
                } else {
                    response = new StringBuilder("STOP" + " " + comand.get(1));
                }

                break;

            case "GAMEREADY":
                System.out.println("server gamerdy " + clientID + "    " + server.boards.get(boardID).players.get(server.boards.get(boardID).pbecnaTuraGracza).clientID);
                if (clientID == server.boards.get(boardID).players.get(server.boards.get(boardID).pbecnaTuraGracza).clientID) {
                    response = new StringBuilder("YOURTURN " + clientID);
                    System.out.println("server yourturn " + clientID);
                }

                break;

            case "CONECTTOGAME":
                boardID = parseInt(comand.get(2));
                System.out.println("SERVER NEW BOARD ID " + boardID);
                server.boards.get(boardID).newPlayerConected(parseInt(comand.get(1)));

                if (server.boards.get(boardID).numberOfHumans == server.boards.get(boardID).licznikLudzi) {
                    for (ServerWątki t : server.threadServer) {
                        if (t.boardID == boardID) {
                            if (t.clientID == clientID)
                                response = new StringBuilder("START " + server.boards.get(boardID).numberOfHumans + " " + server.boards.get(boardID).numberOfBost);
                            else {
                                t.out.println("START " + server.boards.get(boardID).numberOfHumans + " " + server.boards.get(boardID).numberOfBost);
                            }
                        }
                    }
                    server.boards.get(boardID).boardGenerateServer();
                }

                break;

            case "ENDTURN":
                Boolean correctMove = true;
                Boolean botTurn = true;

                if (!comand.get(1).equals("PASS"))
                    for (int i = 1; i < comand.size(); i = i + 4) {
                        if (!server.boards.get(boardID).localboard.testMove(server.boards.get(boardID).localboard.findField(parseInt(comand.get(i)), parseInt(comand.get(i + 1))), server.boards.get(boardID).localboard.findField(parseInt(comand.get(i + 2)), parseInt(comand.get(i + 3))))) {
                            correctMove = false;
                            break;
                        }
                    }

                if (correctMove) {

                    if (!comand.get(1).equals("PASS"))
                        server.boards.get(boardID).localboard.movePawnServer(server.boards.get(boardID).localboard.findField(parseInt(comand.get(1)), parseInt(comand.get(2))), server.boards.get(boardID).localboard.findField(parseInt(comand.get(comand.size() - 2)), parseInt(comand.get(comand.size() - 1))));

                    if (server.boards.get(boardID).pbecnaTuraGracza + 1 < server.boards.get(boardID).numberOfPlayers)
                        server.boards.get(boardID).pbecnaTuraGracza++;
                    else
                        server.boards.get(boardID).pbecnaTuraGracza = 0;

                    System.out.println("server current player ID " + server.boards.get(boardID).pbecnaTuraGracza);

                    server.boards.get(boardID).localboard.setPlayerID(server.boards.get(boardID).pbecnaTuraGracza);

                    for (ServerWątki t : server.threadServer) {
                        if (t.boardID == boardID) {
                            if (t.clientID == clientID)
                                out.println("MOVEACCEPTED");
                            else if (comand.get(1).equals("PASS"))
                                t.out.println("MOVEPASS");
                            else
                                t.out.println("MOVE " + comand.get(1) + " " + comand.get(2) + " " + comand.get(comand.size() - 2) + " " + comand.get(comand.size() - 1));

                            if (!server.boards.get(boardID).players.get(server.boards.get(boardID).pbecnaTuraGracza).bot)
                                if (t.clientID == server.boards.get(boardID).players.get(server.boards.get(boardID).pbecnaTuraGracza).clientID) {
                                    t.out.println("YOURTURN");
                                    System.out.println("Yourturn " + t.clientID);
                                }


                        }


                    }
                    while (server.boards.get(boardID).players.get(server.boards.get(boardID).pbecnaTuraGracza).bot)
                        botMove();

                    System.out.println("server moves itp " + comand);

                } else response = new StringBuilder("MOVEDECLINED");

                break;


            case "DISCONECT":
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
        return response.toString();
    }


    void botMove() {
        List<String> movelist = parserOfCommand(server.boards.get(boardID).players.get(server.boards.get(boardID).pbecnaTuraGracza).Turn());
        System.out.println("server moves " + movelist);
        if (!movelist.get(1).equals("PASS"))
            server.boards.get(boardID).localboard.movePawnServer(server.boards.get(boardID).localboard.findField(parseInt(movelist.get(1)), parseInt(movelist.get(2))), server.boards.get(boardID).localboard.findField(parseInt(movelist.get(movelist.size() - 2)), parseInt(movelist.get(movelist.size() - 1))));

        if (server.boards.get(boardID).pbecnaTuraGracza + 1 < server.boards.get(boardID).numberOfPlayers)
            server.boards.get(boardID).pbecnaTuraGracza++;
        else
            server.boards.get(boardID).pbecnaTuraGracza = 0;

        System.out.println("server current player ID " + server.boards.get(boardID).pbecnaTuraGracza);

        server.boards.get(boardID).localboard.setPlayerID(server.boards.get(boardID).pbecnaTuraGracza);

        for (ServerWątki t : server.threadServer) {
            if (t.boardID == boardID) {
                if (movelist.get(1).equals("PASS"))
                    t.out.println("MOVEPASS");
                else
                    t.out.println("MOVE " + movelist.get(1) + " " + movelist.get(2) + " " + movelist.get(movelist.size() - 2) + " " + movelist.get(movelist.size() - 1));

                if (!server.boards.get(boardID).players.get(server.boards.get(boardID).pbecnaTuraGracza).bot)
                    if (t.clientID == server.boards.get(boardID).players.get(server.boards.get(boardID).pbecnaTuraGracza).clientID)
                        t.out.println("YOURTURN");


            }
        }


    }

    private List parserOfCommand(String line) {
        List<String> list = new ArrayList<>();
        while (!Objects.equals(line, "")) {
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
        int witch = server.boards.size();
        server.boards.add(new StaraPlansza(witch, playerID, numberOfHuans, numberOfBots));
        return witch;
    }


}
