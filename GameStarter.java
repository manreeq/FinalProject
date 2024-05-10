
import java.io.*;
import java.net.*;

public class GameStarter {
    
    private ClientSideConnection csc;
    public static void main(String[] args) {
        GameFrame gf = new GameFrame();
        gf.setUpGUI();
        gf.addKeyBindings();
    }


    public void connectToServer() {
        csc = new ClientSideConnection();
    }


    private class ClientSideConnection {

        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;

        public ClientSideConnection() {
            System.out.println("----Client----");
            try {
                socket = new Socket("localhost", 23307);
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
            } catch (IOException ex) {
                System.out.println("IO Exception from CSC constructor");
            }
        }
    }
    
}
