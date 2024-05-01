import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
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
                System.out.println("moved up lfg");
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

        am.put("up", moveUp);
        am.put("down", moveDown);
        am.put("left", moveLeft);
        am.put("right", moveRight);

        im.put(KeyStroke.getKeyStroke(87, 0, true), "up");
        im.put(KeyStroke.getKeyStroke(83, 0, true), "down");
        im.put(KeyStroke.getKeyStroke(65, 0, true), "left");
        im.put(KeyStroke.getKeyStroke(68, 0, true), "right");
    }

}
