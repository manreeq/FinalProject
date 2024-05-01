import java.awt.*;
import javax.swing.*;

public class GameFrame {
    // JFrame 

    private GameCanvas canvas;
    private JFrame frame;

    public GameFrame() {
        canvas = new GameCanvas();
        frame = new JFrame();
    }

    public void setUpGUI() {
        frame.setSize(1000, 800);
        frame.setTitle("Hot Potato");
        //frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        Container cp = frame.getContentPane();
        cp.add(canvas);

        //frame.pack();
        frame.setVisible(true);
    }

}
