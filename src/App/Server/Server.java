package App.Server;


import App.Decorators.MessageDecorator;
import App.Decorators.ServerMessageDecorator;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Klasa odpowiedzialna za łączenie się  z klientem.
 */
public class Server {

    static private Server server;
    private List<Integer> ports = new ArrayList<>();
    List<StaraPlansza> staraPlanszas = new ArrayList<StaraPlansza>();
    private ServerSocket serverSocket;
    List<ServerWatki> serverWatkis = new ArrayList<>();
    private int clientCounter = 0;


    public Server() {

        ports.add((9999));
        log("Port " + ports.get(0));
        try {
            serverSocket = new ServerSocket(ports.get(0));
            log("START");
        } catch (Exception e) {
            log("Błąd nasłuchu na porcie 9999");
            e.printStackTrace();
        }
        this.listenSocket();
    }

    public static void main(String[] args) {
        server = new Server();
        server.listenSocket();
    }

    private void listenSocket() {
        while (true) {
            log("SOCKET START " + clientCounter);
            ServerWatki serverWatki = new ServerWatki(chosingPort(), ++clientCounter, this, serverSocket);
            serverWatkis.add(serverWatki);
            serverWatki.start();

        }
    }

    /**
     * Metoda wyświetlająca logi servera
     * @param message
     */
    public void log(String message) {
        MessageDecorator m = new ServerMessageDecorator();
        System.out.println(m.log(message));
    }

    /**
     * Metoda dopasowywująca port dla danego połączenia
     * @return port
     */
    int chosingPort() {
        int i = 1;
        int port = 0;
        if (ports.size() == 1) {
            port = ports.get(0) + 1;
            ports.add(port);
        }
        while (i < ports.size()) {
            if (i + ports.get(0) < ports.get(i)) {
                port = i + ports.get(0);
                ports.add(port);
                break;
            }
            i++;
        }
        if (port == 0) {
            port = ports.get(0) + i;
            ports.add(port);
        }
        Collections.sort(ports);
        return port;
    }

}


