import java.io.*;
import java.net.*;

public class GameServer {
    
    private ServerSocket ss;
    private int numPlayers;

    public GameServer() {
        System.out.println("Server started");
        numPlayers = 0;
        try {
            ss = new ServerSocket(23307);
        } catch (IOException ex) {
            System.out.println("IOException from GameServer Constructor");
        }
    }

    public void acceptConnections() {
        try {
            System.out.println("Waiting for connections...");
            while (numPlayers < 2) {
                Socket s = ss.accept();
                numPlayers++;
                System.out.println("Player #" + numPlayers + " has connected.");
            }

            System.out.println("All players connected.");
        } catch (IOException ex) {
            System.out.println("IOException from acceptConnections()");
        }
    }

    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }

}