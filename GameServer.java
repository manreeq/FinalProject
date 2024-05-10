import java.io.*;
import java.net.*;

public class GameServer {
    
    private ServerSocket ss;
    private int numPlayers;
    private ServerSideConnection p1;
    private ServerSideConnection p2;

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
                ServerSideConnection ssc = new ServerSideConnection(s, numPlayers)
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

    private class ServerSideConnection implements Runnable {

        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;
        private int playerID;

        public ServerSideConnection(Socket s, int id) {
            socket = s;
            playerID = id;
            try {
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
            } catch (IOException ex) {
                System.out.println("IOException from SSC Constructor");
            }
        }

        public void run() {
            try {
                dataOut.writeInt(playerID);
                dataOut.flush();

                while (true) {

                }
            } catch (IOException ex) {
                System.out.println("IOException from run() in SSC");
            }
        }
        
    }

}