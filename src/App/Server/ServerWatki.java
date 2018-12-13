package App.Server;

import App.Decorators.MessageDecorator;
import App.Decorators.ServerMessageDecorator;

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


public class ServerWatki extends Thread {
    public Server server;
    private int clientID;
    private Socket client = null;
    private int port;
    private ServerSocket serverSocket;
    private int boardID;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String line = "";

    ServerWatki(int port, int clientNumber, Server serverSide, ServerSocket serverSocket) {
        this.port = port;
        try {
            client = serverSocket.accept();
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            log("NOT ACDEPTED OR CANNOT MAKE THREAT SOSCKET ON PORT: " + this.port);
        }
        this.clientID = clientNumber;
        this.server = serverSide;
    }

    public void log(String message) {
        MessageDecorator m = new ServerMessageDecorator();
        System.out.println(m.log(message));
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException e) {
            log(" run: IO ERROR");
        }
        while (line != null) {
            try {
                line = in.readLine();
                log("LISTEN: " + line);
                out.println(DoSomething(line));
            } catch (IOException e) {
                log("Read failed " + clientID);
                line = null;
            }
        }
    }

    private String DoSomething(String line) {
        StringBuilder response = new StringBuilder();
        List<String> comand = parserOfCommand(line);
        int witch;
        log("Command form client: " + comand.get(0));
        switch (comand.get(0)) {
            case "CONECT":
                response = new StringBuilder(clientID + " " + port);
                log("CONECTED ON PORT " + port + "   " + clientID);

                break;

            case "BEGIN":
                witch = addNewBoard(parseInt(comand.get(1)), parseInt(comand.get(2)), parseInt(comand.get(3)));
                boardID = witch;
                log("cclietnID " + server.staraPlanszas.get(boardID).players.get(0).clientID + " : " + server.staraPlanszas.get(boardID).players.get(server.staraPlanszas.get(boardID).pbecnaTuraGracza).clientID);
                response = new StringBuilder(comand.get(1) + " " + boardID + " " + 0);
                break;

            case "GETGAMES":
                for (StaraPlansza b : server.staraPlanszas) {
                    if (b.numberOfHumans != b.licznikLudzi)
                        if (response.toString().equals(""))
                            response = new StringBuilder("BOARDS " + b.planszaID + " " + b.licznikLudzi + " " + b.numberOfHumans + " " + b.numberOfBost);
                        else
                            response.append(" ").append(b.planszaID).append(" ").append(b.licznikLudzi).append(" ").append(b.numberOfHumans).append(" ").append(b.numberOfBost);
                }

                break;

            case "START": //START + CLIENT_ID + BOARD_ID
                if (server.staraPlanszas.get(boardID).numberOfHumans == server.staraPlanszas.get(boardID).licznikLudzi) {
                    server.staraPlanszas.get(boardID).boardGenerateServer();
                    response = new StringBuilder("START " + server.staraPlanszas.get(boardID).numberOfHumans + " " + server.staraPlanszas.get(boardID).numberOfBost);
                } else {
                    response = new StringBuilder("STOP" + " " + comand.get(1));
                }

                break;

            case "GAMEREADY":
                log("Game ready " + clientID + "    " + server.staraPlanszas.get(boardID).players.get(server.staraPlanszas.get(boardID).pbecnaTuraGracza).clientID);
                if (clientID == server.staraPlanszas.get(boardID).players.get(server.staraPlanszas.get(boardID).pbecnaTuraGracza).clientID) {
                    response = new StringBuilder("YOURTURN " + clientID);
                    log("Your turn " + clientID);
                }

                break;

            case "CONECTTOGAME":
                boardID = parseInt(comand.get(2));
                log("NEW BOARD ID " + boardID);
                server.staraPlanszas.get(boardID).newPlayerConected(parseInt(comand.get(1)));

                if (server.staraPlanszas.get(boardID).numberOfHumans == server.staraPlanszas.get(boardID).licznikLudzi) {
                    for (ServerWatki t : server.serverWatkis) {
                        if (t.boardID == boardID) {
                            if (t.clientID == clientID)
                                response = new StringBuilder("START " + server.staraPlanszas.get(boardID).numberOfHumans + " " + server.staraPlanszas.get(boardID).numberOfBost);
                            else {
                                t.out.println("START " + server.staraPlanszas.get(boardID).numberOfHumans + " " + server.staraPlanszas.get(boardID).numberOfBost);
                            }
                        }
                    }
                    server.staraPlanszas.get(boardID).boardGenerateServer();
                }

                break;

            case "ENDTURN":
                Boolean correctMove = true;
                Boolean botTurn = true;

                if (!comand.get(1).equals("PASS"))
                    for (int i = 1; i < comand.size(); i = i + 4) {
                        if (!server.staraPlanszas.get(boardID).localboard.testMove(server.staraPlanszas.get(boardID).localboard.findField(parseInt(comand.get(i)), parseInt(comand.get(i + 1))), server.staraPlanszas.get(boardID).localboard.findField(parseInt(comand.get(i + 2)), parseInt(comand.get(i + 3))))) {
                            correctMove = false;
                            break;
                        }
                    }

                if (correctMove) {

                    if (!comand.get(1).equals("PASS"))
                        server.staraPlanszas.get(boardID).localboard.movePawnServer(server.staraPlanszas.get(boardID).localboard.findField(parseInt(comand.get(1)), parseInt(comand.get(2))), server.staraPlanszas.get(boardID).localboard.findField(parseInt(comand.get(comand.size() - 2)), parseInt(comand.get(comand.size() - 1))));

                    if (server.staraPlanszas.get(boardID).pbecnaTuraGracza + 1 < server.staraPlanszas.get(boardID).numberOfPlayers)
                        server.staraPlanszas.get(boardID).pbecnaTuraGracza++;
                    else
                        server.staraPlanszas.get(boardID).pbecnaTuraGracza = 0;

                    log("Current player ID " + server.staraPlanszas.get(boardID).pbecnaTuraGracza);

                    server.staraPlanszas.get(boardID).localboard.setPlayerID(server.staraPlanszas.get(boardID).pbecnaTuraGracza);

                    for (ServerWatki t : server.serverWatkis) {
                        if (t.boardID == boardID) {
                            if (t.clientID == clientID)
                                out.println("MOVEACCEPTED");
                            else if (comand.get(1).equals("PASS"))
                                t.out.println("MOVEPASS");
                            else
                                t.out.println("MOVE " + comand.get(1) + " " + comand.get(2) + " " + comand.get(comand.size() - 2) + " " + comand.get(comand.size() - 1));

                            if (!server.staraPlanszas.get(boardID).players.get(server.staraPlanszas.get(boardID).pbecnaTuraGracza).bot)
                                if (t.clientID == server.staraPlanszas.get(boardID).players.get(server.staraPlanszas.get(boardID).pbecnaTuraGracza).clientID) {
                                    t.out.println("YOURTURN");
                                    log("Your turn " + t.clientID);
                                }


                        }


                    }
                    while (server.staraPlanszas.get(boardID).players.get(server.staraPlanszas.get(boardID).pbecnaTuraGracza).bot)
                        botMove();

                    log("Moves itp " + comand);

                } else response = new StringBuilder("MOVEDECLINED");

                break;


            case "DISCONECT":
                line = null;
                try {
                    client.close();
                } catch (IOException e) {
                    log("UNABLE TO CLOSE SOCKET");
                }

                break;


            default:
                log("ERROR WHILE READING FROM CLIENT - UNKNOW COMMAND");

                break;

            case "TERMINATE":
                interrupt();
                break;
        }

        log("Out " + response);
        comand.clear();
        return response.toString();
    }


    void botMove() {
        List<String> movelist = parserOfCommand(server.staraPlanszas.get(boardID).players.get(server.staraPlanszas.get(boardID).pbecnaTuraGracza).Turn());
        log("Moves " + movelist);
        if (!movelist.get(1).equals("PASS"))
            server.staraPlanszas.get(boardID).localboard.movePawnServer(server.staraPlanszas.get(boardID).localboard.findField(parseInt(movelist.get(1)), parseInt(movelist.get(2))), server.staraPlanszas.get(boardID).localboard.findField(parseInt(movelist.get(movelist.size() - 2)), parseInt(movelist.get(movelist.size() - 1))));

        if (server.staraPlanszas.get(boardID).pbecnaTuraGracza + 1 < server.staraPlanszas.get(boardID).numberOfPlayers)
            server.staraPlanszas.get(boardID).pbecnaTuraGracza++;
        else
            server.staraPlanszas.get(boardID).pbecnaTuraGracza = 0;

        log("Current player ID " + server.staraPlanszas.get(boardID).pbecnaTuraGracza);

        server.staraPlanszas.get(boardID).localboard.setPlayerID(server.staraPlanszas.get(boardID).pbecnaTuraGracza);

        for (ServerWatki t : server.serverWatkis) {
            if (t.boardID == boardID) {
                if (movelist.get(1).equals("PASS"))
                    t.out.println("MOVEPASS");
                else
                    t.out.println("MOVE " + movelist.get(1) + " " + movelist.get(2) + " " + movelist.get(movelist.size() - 2) + " " + movelist.get(movelist.size() - 1));

                if (!server.staraPlanszas.get(boardID).players.get(server.staraPlanszas.get(boardID).pbecnaTuraGracza).bot)
                    if (t.clientID == server.staraPlanszas.get(boardID).players.get(server.staraPlanszas.get(boardID).pbecnaTuraGracza).clientID)
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
        int witch = server.staraPlanszas.size();
        server.staraPlanszas.add(new StaraPlansza(witch, playerID, numberOfHuans, numberOfBots));
        return witch;
    }


}
