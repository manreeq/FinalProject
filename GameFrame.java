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


import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * This class is where the GUI is created and where key inputs are detected
 * This class also interacts with the GameServer to rad and write information
 */
public class GameFrame {

    private GameCanvas canvas;
    private JFrame frame;
    private JPanel cp;
    private int upTrue, downTrue, leftTrue, rightTrue;
    private int amtKeysX, amtKeysY;
    private Socket socket;

    //server fields;
    private int playerID;
    private ReadFromServer rfsRunnable;
    private WriteToServer wtsRunnable;

    /**
     * This clsas instantiates a GameFrame object
     */
    public GameFrame() {
        frame = new JFrame();
        cp = (JPanel) frame.getContentPane();
        cp.setFocusable(true);
        amtKeysX = 0;
        amtKeysY = 0;
        upTrue = 0;
        downTrue = 0;
        leftTrue = 0;
        rightTrue = 0;
    }

    /**
     * Creates the GUI that will be displayed for the user using GameCanvas
     */
    public void setUpGUI() {
        frame.setTitle("Player " + playerID + ": Hot Potato");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        canvas = new GameCanvas(playerID);
        
        cp.add(canvas);

        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Adds the key bindings for the game
     * Maps WASD keys to movment functions of the player
     */
    public void addKeyBindings() {
        ActionMap am = cp.getActionMap();
        InputMap im = cp.getInputMap();

        AbstractAction moveUp = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {

                upTrue = 1;
                amtKeysY = upTrue + downTrue;
                canvas.wPressed(true);

            }
        };

        AbstractAction moveDown = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {

                downTrue = 1;
                amtKeysY = upTrue + downTrue;
                canvas.sPressed(true);
            }
        };

        AbstractAction moveRight = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {

                rightTrue = 1;
                amtKeysX = leftTrue + rightTrue;
                canvas.dPressed(true);
            }
        };

        AbstractAction moveLeft = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {

                leftTrue = 1;
                amtKeysX = leftTrue + rightTrue;
                canvas.aPressed(true);
            }
        };

        AbstractAction stopUp = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {

                upTrue = 0;
                amtKeysY -= 1;
                if (upTrue == 0) 
                canvas.wPressed(false);

            }
        };

        AbstractAction stopDown = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                downTrue = 0;
                amtKeysY -= 1;
                if (downTrue == 0) 
                canvas.sPressed(false);
            }
        };

        AbstractAction stopLeft = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {

                leftTrue = 0;
                amtKeysX -= 1;
                if (leftTrue == 0) 
                canvas.aPressed(false);
            }
        };

        AbstractAction stopRight = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {

                rightTrue = 0;
                amtKeysX -= 1;  
                if (rightTrue == 0) 
                canvas.dPressed(false);
            }
        };

        AbstractAction dash = new AbstractAction() {
            public void actionPerformed(ActionEvent ae){
                canvas.dashPlayer(goingDiagonal());
            }
        };


        am.put("up", moveUp);
        am.put("down", moveDown);
        am.put("left", moveLeft);
        am.put("right", moveRight);
        
        am.put("shift", dash);

        am.put("upEnd", stopUp);
        am.put("downEnd", stopDown);
        am.put("leftEnd", stopLeft);
        am.put("rightEnd", stopRight);

        im.put(KeyStroke.getKeyStroke(87, 0), "up");
        im.put(KeyStroke.getKeyStroke(83, 0), "down");
        im.put(KeyStroke.getKeyStroke(65, 0), "left");
        im.put(KeyStroke.getKeyStroke(68, 0), "right");

        im.put(KeyStroke.getKeyStroke(32, 0), "shift");

        im.put(KeyStroke.getKeyStroke(87, 0, true), "upEnd");
        im.put(KeyStroke.getKeyStroke(83, 0, true), "downEnd");
        im.put(KeyStroke.getKeyStroke(65, 0, true), "leftEnd");
        im.put(KeyStroke.getKeyStroke(68, 0, true), "rightEnd");
    }

    /**
     * Returns a boolean that determines if the player is going diagonal
     * @return
     */
    public boolean goingDiagonal() {
        if (amtKeysX > 0 && amtKeysY > 0) return true;
        else return false;
    }


    /**
     * Method for connecting to the server
     */
    public void connectToServer() {
        try {
            String host = "";
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.print("IP Adress : ");
                host = scanner.nextLine();
            }
            socket = new Socket(host, 23307);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            playerID = in.readInt();
            System.out.println("Connected as Player " + playerID);

            if (playerID == 1)
            System.out.println("Waiting for Player 2 to connect...");

            rfsRunnable = new ReadFromServer(in);
            wtsRunnable = new WriteToServer(out);
            rfsRunnable.waitForStartMsg();

        } catch (IOException ex) {
            System.out.println("IOException from connectToServer()");
        }
    }

    
    /**
     * The inner class for the thread that reads from the server
     * This class reads player positions, direction, ready status, and collisions
     */
    private class ReadFromServer implements Runnable {

        private DataInputStream dataIn;

        /** Instantiates the ReadFromServer object
         */
        public ReadFromServer(DataInputStream in) {
            dataIn = in;
            System.out.println("RFS Runnable created");
        }

        /**
         * Runs the thread
         */
        public void run() {
            try {
                while (true) {
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        System.out.println("InterruptedException at RFS run()");
                    } 
                    
                    if (canvas != null) {
                        canvas.enemySetX(dataIn.readInt());
                        canvas.enemySetY(dataIn.readInt());
                        canvas.setEnemyDirection(dataIn.readInt()); 
                        canvas.setEnemyReady(dataIn.readBoolean());
                        canvas.setCollided(dataIn.readBoolean());
                        canvas.setExploded(dataIn.readBoolean());                       
                    }
                }

            } catch (IOException e) {
                System.out.println("IOException from RFS run()");
            }
        }
    
        /**
         * Waits for the server to send a message before creating and starting the read and write thread
         */
        public void waitForStartMsg() {
            try {
                
                String startMsg = dataIn.readUTF();
                System.out.println("Message from server: " + startMsg);
                Thread readThread = new Thread(rfsRunnable);
                Thread writeThread = new Thread(wtsRunnable);
                readThread.start();
                writeThread.start();

            } catch (IOException e) {
                System.out.println("IOException from waitForStartMsg()");
            }
        }

    }
    

    /**
     * The inner class for the thread that writes to the server.
     * This class writes player positions, direction, ready status, and collisions.
     */
    private class WriteToServer implements Runnable {

        private DataOutputStream dataOut;

        /**
         * Instantiates the WriteToServer object
         * @param out
         */
        public WriteToServer(DataOutputStream out) {
            dataOut = out;
            System.out.println("WTS Runnable created");
        }

        /**
         * Method that runs the thread
         */
        public void run() {
            try {
                
                while (true) {
                    
                    if (canvas != null) {
                        dataOut.writeInt(canvas.meGetX());
                        dataOut.writeInt(canvas.meGetY());
                        dataOut.writeInt(canvas.meGetDirection()); 
                        dataOut.writeBoolean(canvas.getMeReady());
                        dataOut.writeBoolean(canvas.getColliding());
                        dataOut.writeBoolean(canvas.getFuseExploded());
                        dataOut.flush();
                    }
                    
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException ex) {
                        System.out.println("InterruptedException from WTS run()");
                    }

                }

            } catch (IOException ex) {
                System.out.println("IOException from WTS run()");
            }
        }

    }

}
