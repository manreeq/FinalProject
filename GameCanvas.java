import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.util.*;

public class GameCanvas extends JComponent{
    
    private ArrayList<GameEntity> entities;
    private MapObstacles obstacles;
    private Player player;
    private Timer timer;
    private int delay;

    public GameCanvas() {
        this.setPreferredSize(new Dimension(1000, 800));
        delay = 20;

        entities = new ArrayList<>();

        obstacles = new MapObstacles(Color.BLACK);

        player = new Player(75, 75, 30, Color.BLUE, 0, 0);


        ActionListener timerListener = new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent ae) {
                player.tick();
                repaint();
            }

        };

        timer = new Timer(delay, timerListener);
        timer.start();

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);

        obstacles.draw(g2d);
        player.draw(g2d);
    }

    
    public void movePlayerUp(){
        player.setVSpeed(-5);
        repaint();
    }

    public void movePlayerDown(){
        player.setVSpeed(5);;
        repaint();
    }

    public void movePlayerLeft(){
        player.setHSpeed(-5);;
        repaint();
    }

    public void movePlayerRight(){
        player.setHSpeed(5);;
        repaint();
    } 

    public void stopMovingY(){
        player.setVSpeed(0);
    }

    public void stopMovingX(){
        player.setHSpeed(0);
    }

}
