import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

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

    public void setUpGUI() {
        frame.setTitle("Player " + playerID + ": Hot Potato");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        //Container cp = frame.getContentPane();
        
        canvas = new GameCanvas(playerID);
        
        cp.add(canvas);

        frame.pack();
        frame.setVisible(true);
    }


    public void addKeyBindings() {
        ActionMap am = cp.getActionMap();
        InputMap im = cp.getInputMap();

        AbstractAction moveUp = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                /*
                if (keyCounter == 0) keyCounter = 1;
                else if (keyCounter == 1) keyCounter = 2;

                if (keyCounter > 0) {
                    canvas.movePlayerUp();
                }
                System.out.println(keyCounter); */

                upTrue = 1;
                amtKeysY = upTrue + downTrue;
                //canvas.movePlayerUp();
                canvas.wPressed(true);

            }
        };

        AbstractAction moveDown = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {

                downTrue = 1;
                amtKeysY = upTrue + downTrue;
                //canvas.movePlayerDown();
                canvas.sPressed(true);
                //System.out.println("down");
            }
        };

        AbstractAction moveRight = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {

                rightTrue = 1;
                amtKeysX = leftTrue + rightTrue;
                //canvas.movePlayerRight();
                canvas.dPressed(true);
                //System.out.println("right");
            }
        };

        AbstractAction moveLeft = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {

                leftTrue = 1;
                amtKeysX = leftTrue + rightTrue;
                //canvas.movePlayerLeft();
                canvas.aPressed(true);
                //System.out.println("left");
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
                //canvas.stopMovingY();
                canvas.sPressed(false);
            }
        };

        AbstractAction stopLeft = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {

                leftTrue = 0;
                amtKeysX -= 1;
                if (leftTrue == 0) 
                //canvas.stopMovingX();
                canvas.aPressed(false);
            }
        };

        AbstractAction stopRight = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {

                rightTrue = 0;
                amtKeysX -= 1;  
                if (rightTrue == 0) 
                //canvas.stopMovingX();
                canvas.dPressed(false);
            }
        };

        AbstractAction dash = new AbstractAction() {
            public void actionPerformed(ActionEvent ae){
                canvas.dashPlayer(goingDiagonal());
                System.out.println(amtKeysX + "x");
                System.out.println(amtKeysY + "y");
                System.out.println();
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

    //DI PA GUMAGANA GAANO UNG DIAGONAL SHIT
    public boolean goingDiagonal() {
        if (amtKeysX > 0 && amtKeysY > 0) return true;
        else return false;
    }




    //
    //
    //
    //SERVER SHENANIGANS
    //
    //
    //



    public void connectToServer() {
        try {
            socket = new Socket("localhost", 23307);
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


    private class ReadFromServer implements Runnable {

        private DataInputStream dataIn;

        public ReadFromServer(DataInputStream in) {
            dataIn = in;
            System.out.println("RFS Runnable created");
        }

        public void run() {
            try {
                while (true) {
                    System.out.println("TITE");
                    if (canvas != null) {
                        canvas.enemySetX(dataIn.readInt());
                        canvas.enemySetY(dataIn.readInt());
                        canvas.setEnemyReady(dataIn.readBoolean());
                    }
                }

            } catch (IOException e) {
                System.out.println("IOException from RFS run()");
            }
        }

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
    

    private class WriteToServer implements Runnable {

        private DataOutputStream dataOut;

        public WriteToServer(DataOutputStream out) {
            dataOut = out;
            System.out.println("WTS Runnable created");
        }

        public void run() {
            try {
                
                while (true) {
                    
                    if (canvas != null) {
                        dataOut.writeInt(canvas.meGetX());
                        dataOut.writeInt(canvas.meGetY());
                        dataOut.writeBoolean(canvas.getMeReady());
                        dataOut.flush();
                    }
                    
                    try {
                        Thread.sleep(25);
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
