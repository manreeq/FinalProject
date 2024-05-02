import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameFrame {
    // JFrame 

    private GameCanvas canvas;
    private JFrame frame;
    private JPanel cp;

    public GameFrame() {
        canvas = new GameCanvas();
        frame = new JFrame();
        cp = (JPanel) frame.getContentPane();
        cp.setFocusable(true);
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
                canvas.movePlayerUp();
            }
        };

        AbstractAction moveDown = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                canvas.movePlayerDown();
            }
        };

        AbstractAction moveRight = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                canvas.movePlayerRight();
            }
        };

        AbstractAction moveLeft = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                canvas.movePlayerLeft();
            }
        };

        AbstractAction stopY = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                canvas.stopMovingY();
            }
        };

        AbstractAction stopX = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                canvas.stopMovingX();
            }
        };


        am.put("up", moveUp);
        am.put("down", moveDown);
        am.put("left", moveLeft);
        am.put("right", moveRight);

        am.put("upEnd", stopY);
        am.put("downEnd", stopY);
        am.put("leftEnd", stopX);
        am.put("rightEnd", stopX);

        im.put(KeyStroke.getKeyStroke(87, 0), "up");
        im.put(KeyStroke.getKeyStroke(83, 0), "down");
        im.put(KeyStroke.getKeyStroke(65, 0), "left");
        im.put(KeyStroke.getKeyStroke(68, 0), "right");

        im.put(KeyStroke.getKeyStroke(87, 0, true), "upEnd");
        im.put(KeyStroke.getKeyStroke(83, 0, true), "downEnd");
        im.put(KeyStroke.getKeyStroke(65, 0, true), "leftEnd");
        im.put(KeyStroke.getKeyStroke(68, 0, true), "rightEnd");
    }

}
