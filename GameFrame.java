    import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameFrame {
    // JFrame 

    private GameCanvas canvas;
    private JFrame frame;
    private JPanel cp;
    //private int keyCounter;
    private int upTrue, downTrue, leftTrue, rightTrue;
    private int amtKeysX, amtKeysY;


    public GameFrame() {
        canvas = new GameCanvas();
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
        frame.setSize(1000, 800);
        frame.setTitle("Hot Potato");
        //frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        //Container cp = frame.getContentPane();
        cp.add(canvas);

        //frame.pack();
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
                canvas.movePlayerUp();
                System.out.println(amtKeysY);

            }
        };

        AbstractAction moveDown = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                /*
                if (keyCounter == 0) keyCounter = 1;
                else if (keyCounter == 1) keyCounter = 2;

                if (keyCounter > 0) {
                    canvas.movePlayerDown();
                }
                System.out.println(keyCounter); */

                downTrue = 1;
                amtKeysY = upTrue + downTrue;
                canvas.movePlayerDown();
                System.out.println(amtKeysY);
            }
        };

        AbstractAction moveRight = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                /*
                if (keyCounter == 0) keyCounter = 1;
                else if (keyCounter == 1) keyCounter = 2;

                if (keyCounter > 0) {
                    canvas.movePlayerRight();
                }
                System.out.println(keyCounter); */

                rightTrue = 1;
                amtKeysX = leftTrue + rightTrue;
                canvas.movePlayerRight();
                System.out.println(amtKeysX);
            }
        };

        AbstractAction moveLeft = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                /*
                if (keyCounter == 0) keyCounter = 1;
                else if (keyCounter == 1) keyCounter = 2;
                
                if (keyCounter > 0) {
                    canvas.movePlayerLeft();
                }
                System.out.println(keyCounter); */

                leftTrue = 1;
                amtKeysX = leftTrue + rightTrue;
                canvas.movePlayerLeft();
                System.out.println(amtKeysX);
            }
        };

        AbstractAction stopUp = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                /*
                keyCounter -= 1;
                if (keyCounter == 0){
                    canvas.stopMovingY();
                    System.out.println(keyCounter);
                }
                else {
                    //keyCounter = 0;
                    System.out.println(keyCounter);
                } */

                upTrue = 0;
                amtKeysY -= 1;
                if (amtKeysY == 0) {
                canvas.stopMovingY();}
                System.out.println(amtKeysY);

            }
        };

        AbstractAction stopDown = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                /*
                keyCounter -= 1;
                if (keyCounter == 0){
                    canvas.stopMovingY();
                    System.out.println(keyCounter);
                }
                else {
                    //keyPressedY = false;
                    System.out.println(keyCounter);
                } */

                downTrue = 0;
                amtKeysY -= 1;
                if (amtKeysY == 0) {
                canvas.stopMovingY();}
                System.out.println(amtKeysY);
            }
        };

        AbstractAction stopLeft = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                /*
                keyCounter -= 1;
                if (keyCounter == 0){
                    canvas.stopMovingX();
                    System.out.println(keyCounter);
                }
                else {
                    //keyCounter = 0;
                    System.out.println(keyCounter);
                } */

                leftTrue = 0;
                amtKeysX -= 1;
                if (amtKeysX == 0) {
                canvas.stopMovingX();}
                System.out.println(amtKeysX);
            }
        };

        AbstractAction stopRight = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                /*
                keyCounter -= 1;
                if (keyCounter == 0){
                    canvas.stopMovingX();
                    System.out.println(keyCounter);
                }
                else {
                    //keyCounter = 0;
                    System.out.println(keyCounter);
                } */

                rightTrue = 0;
                amtKeysX -= 1;
                if (amtKeysX == 0) {
                canvas.stopMovingX();}
                System.out.println(amtKeysX);
            }
        };


        am.put("up", moveUp);
        am.put("down", moveDown);
        am.put("left", moveLeft);
        am.put("right", moveRight);

        am.put("upEnd", stopUp);
        am.put("downEnd", stopDown);
        am.put("leftEnd", stopLeft);
        am.put("rightEnd", stopRight);

        im.put(KeyStroke.getKeyStroke(87, 0), "up");
        im.put(KeyStroke.getKeyStroke(83, 0), "down");
        im.put(KeyStroke.getKeyStroke(65, 0), "left");
        im.put(KeyStroke.getKeyStroke(68, 0), "right");

        im.put(KeyStroke.getKeyStroke(87, 0, true), "upEnd");
        im.put(KeyStroke.getKeyStroke(83, 0, true), "downEnd");
        im.put(KeyStroke.getKeyStroke(65, 0, true), "leftEnd");
        im.put(KeyStroke.getKeyStroke(68, 0, true), "rightEnd");
    }

    /** 
    public int getKeysPressed() {
        return keyCounterY;
    }
    */

}
