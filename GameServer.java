import java.io.*;
import java.net.*;

public class GameServer {
    
    private ServerSocket ss;
    private int numPlayers;
    private Socket p1Socket;
    private Socket p2Socket;
    private ReadFromClient p1ReadRunnable;
    private ReadFromClient p2ReadRunnable;
    private WriteToClient p1WriteRunnable;
    private WriteToClient p2WriteRunnable;
    private int p1x, p1y, p2x, p2y;
    private boolean p1Ready, p2Ready;
    private boolean p1HasPotato, p2HasPotato;

    public GameServer() {
        System.out.println("Server started");
        numPlayers = 0;

        p1x = 75; p1y = 75;
        p2x = 1000-105; p2y = 750-105;

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
                DataInputStream in = new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());

                numPlayers++;
                out.writeInt(numPlayers);
                System.out.println("Player #" + numPlayers + " has connected");

                ReadFromClient rfc = new ReadFromClient(numPlayers, in);
                WriteToClient wtc = new WriteToClient(numPlayers, out);

                if (numPlayers == 1) {
                    p1Socket = s;
                    p1ReadRunnable = rfc;
                    p1WriteRunnable = wtc;
                } else {
                    p2Socket = s;
                    p2ReadRunnable = rfc;
                    p2WriteRunnable = wtc;
                    p1WriteRunnable.sendStartMsg();
                    p2WriteRunnable.sendStartMsg();

                    Thread readThread1 = new Thread(p1ReadRunnable);
                    Thread readThread2 = new Thread(p2ReadRunnable);
                    readThread1.start();
                    readThread2.start();

                    Thread writeThread1 = new Thread(p1WriteRunnable);
                    Thread writeThread2 = new Thread(p2WriteRunnable);
                    writeThread1.start();
                    writeThread2.start();
                }

            }

        } catch (IOException ex) {
            System.out.println("IOException from acceptConnections()");
        }
    }


    private class ReadFromClient implements Runnable {
        
        private int playerID;
        private DataInputStream dataIn;

        public ReadFromClient(int pid, DataInputStream in) {
            playerID = pid;
            dataIn = in;
            System.out.println("RFC" + playerID + " Runnable created");
        }

        public void run() {
            try {
                
                while (true) {
                    if (playerID == 1) {
                        p1x = dataIn.readInt();
                        p1y = dataIn.readInt();
                        p1Ready = dataIn.readBoolean();
                    } else {
                        p2x = dataIn.readInt();
                        p2y = dataIn.readInt();
                        p2Ready = dataIn.readBoolean();
                    }
                    //read the boolean
                }

            } catch (IOException e) {
                System.out.println("IOException from RFS run()");
            }
        }

    }

    private class WriteToClient implements Runnable {
        
        private int playerID;
        private DataOutputStream dataOut;

        public WriteToClient(int pid, DataOutputStream out) {
            playerID = pid;
            dataOut = out;
            System.out.println("WTC" + playerID + " Runnable created");
        }

        public void run() {
            try {
                
                while (true) {
                    if (playerID == 1) {
                        dataOut.writeInt(p2x);
                        dataOut.writeInt(p2y);
                        dataOut.writeBoolean(p2Ready);
                        dataOut.flush();
                    } else {
                        dataOut.writeInt(p1x);
                        dataOut.writeInt(p1y);
                        dataOut.writeBoolean(p1Ready);
                        dataOut.flush();
                    }

                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        System.out.println("InterruptedException from WTC run()");
                    }
                }

            } catch (IOException e) {
                System.out.println("IOException from WTC run()");
            }
        }

        public void sendStartMsg() {
            try {
                
                dataOut.writeUTF("All players connected!");

            } catch (IOException e) {
                System.out.println("IOException from sendStartMsg()");
            }
        }

    }

    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }


}