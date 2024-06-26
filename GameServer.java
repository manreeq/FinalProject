/**
	This is a template for a Java file.
	
	@author Gabriel P. Hermosura (233080)
    @author Evan Sebastian M. Garcia (232776)
	@version 14 May 2024
	
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
**/

import java.io.*;
import java.net.*;

/**
 * This class is for the server of the game where both players connect to in order 
 * to send data to each other
 */
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
    private boolean p1Collided, p2Collided, p1Exploded, p2Exploded, exploded;
    private int p1Direction, p2Direction;
    private boolean collided;

    /**
     * Constructor that instantiates the GameServer object
     */
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

    /**
     * Waits for both players to connect before starting the read and write threads of the server
     */
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


    /**
     * Private class for the thread that reads from both of the players' clients
     */
    private class ReadFromClient implements Runnable {
        
        private int playerID;
        private DataInputStream dataIn;

        /**
         * Instantiates the ReadFromClient object
         */
        public ReadFromClient(int pid, DataInputStream in) {
            playerID = pid;
            dataIn = in;
            System.out.println("RFC" + playerID + " Runnable created");
        }

        /**
         * Method for starting the thread
         */
        public void run() {
            try {
                
                while (true) {
                    if (playerID == 1) {
                        p1x = dataIn.readInt();
                        p1y = dataIn.readInt();
                        p1Direction = dataIn.readInt(); 
                        p1Ready = dataIn.readBoolean();
                        p1Collided = dataIn.readBoolean();
                        p1Exploded = dataIn.readBoolean();

                    } else {
                        p2x = dataIn.readInt();
                        p2y = dataIn.readInt();
                        p2Direction = dataIn.readInt(); 
                        p2Ready = dataIn.readBoolean();
                        p2Collided = dataIn.readBoolean();
                        p2Exploded = dataIn.readBoolean();
                        
                    }
                    System.out.println(p1Ready);
                    System.out.println(p2Ready);
                    System.out.println();
                }

            } catch (IOException e) {
                System.out.println("IOException from RFS run()");
            }
        }

    }

    /**
     * Inner class for the thread that writes to both of the players' clients
     */
    private class WriteToClient implements Runnable {
        
        private int playerID;
        private DataOutputStream dataOut;

        /**
         * Instantiates the WriteToClient object
         * @param pid
         * @param out
         */
        public WriteToClient(int pid, DataOutputStream out) {
            playerID = pid;
            dataOut = out;
            System.out.println("WTC" + playerID + " Runnable created");
        }

        /**
         * Method for starting the thread
         */
        public void run() {
            try {
                
                while (true) {
                    if (p1Exploded && p2Exploded) exploded = true;
                    else exploded = false;

                    if (p1Collided && p2Collided) collided = true;
                    else collided = false;
                    
                    if (playerID == 1) {
                        dataOut.writeInt(p2x);
                        dataOut.writeInt(p2y);
                        dataOut.writeInt(p2Direction); 
                        dataOut.writeBoolean(p2Ready);
                        dataOut.writeBoolean(collided);
                        dataOut.writeBoolean(exploded);
                        dataOut.flush();
                    } else {
                        dataOut.writeInt(p1x);
                        dataOut.writeInt(p1y);
                        dataOut.writeInt(p1Direction); 
                        dataOut.writeBoolean(p1Ready);
                        dataOut.writeBoolean(collided);
                        dataOut.writeBoolean(exploded);       
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

        /**
         * Sends the start message to the players if both players are connected
         */
        public void sendStartMsg() {
            try {
                
                dataOut.writeUTF("All players connected!");

            } catch (IOException e) {
                System.out.println("IOException from sendStartMsg()");
            }
        }

    }

    /**
     * Main method of the server
     * @param args
     */
    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }


}