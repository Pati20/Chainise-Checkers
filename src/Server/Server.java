package Server;


import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Server {

    static Server server;
    public List<Integer> ports = new ArrayList<>();
    List<StaraPlansza> boards = new ArrayList<StaraPlansza>();
    ServerSocket serverSocket;
//    Socket client;
//    BufferedReader in = null;
//    PrintWriter out = null;
//    String line = "";
    int clientCounter = 0;
    List<ServerWątki> threadServer = new ArrayList<>();

    public Server() {

        ports.add(new Integer(9999));
        System.out.println(ports.get(0));
        try {
            serverSocket = new ServerSocket(ports.get(0));
            System.out.println("SERVER START");
        } catch (Exception e) {
            System.out.println("Błąd nasłuchu na porcie 9999");
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
            System.out.println("SOCKET START " + clientCounter);
            ServerWątki newthread = new ServerWątki(chosingPort(), ++clientCounter, this, serverSocket);
            threadServer.add(newthread);
            newthread.start();

        }
    }

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


