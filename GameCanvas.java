import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.util.*;

public class GameCanvas extends JComponent{
    
    //private ArrayList<GameEntity> entities;
    private ArrayList<WallsInterface> walls;
    private Player player;
    private Timer timer;
    private int delay;
    private Color wc;
    
    private RectangleWall bNorth;
    private RectangleWall bSouth;
    private RectangleWall bEast;
    private RectangleWall bWest;
    private RectangleWall r1;
    private RectangleWall r2;
    private LineWall l1;
    private LineWall l2;
    private LineWall l3;
    private LineWall l4;
    private LineWall l5;
    private LineWall l6;
    private LineWall l7;

    public GameCanvas() {
        this.setPreferredSize(new Dimension(1000, 800));
        delay = 20;
        wc = Color.BLACK;
        walls = new ArrayList<>();
        //entities = new ArrayList<>();

        //border walls
        bNorth = new RectangleWall(0, -30, 1000, 40, wc);
        walls.add(bNorth);
        bSouth = new RectangleWall(0, 755, 1000, 40, wc);
        walls.add(bSouth);
        bEast = new RectangleWall(980, 0, 40, 800, wc);
        walls.add(bEast);
        bWest = new RectangleWall(-35, -0, 40, 800, wc);
        walls.add(bWest);

        //obstacles
        l1 = new LineWall(150, 250, 150, 380, 10, wc);
        walls.add(l1);
        l2 = new LineWall(150, 460, 150, 580, 10, wc);
        walls.add(l2);
        
        l3 = new LineWall(383, 260, 383, 470, 10, wc);
        walls.add(l3);
        l4 = new LineWall(617, 260, 617, 470, 10, wc);
        walls.add(l4);
        l5 = new LineWall(470, 365, 520, 365, 10, wc);
        walls.add(l5);

        l6 = new LineWall(700, 600, 860, 400, 10, wc);
        walls.add(l6);
        l7 = new LineWall(500, 100, 800, 100, 10, wc);
        walls.add(l7);

        r1 = new RectangleWall(300, 600, 100, 100, wc);
        walls.add(r1);
        r2 = new RectangleWall(730, 200, 100, 100, wc);
        walls.add(r2);

        player = new Player(75, 75, 30, Color.BLUE, 0, 0);


        ActionListener timerListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player.tick();
                for (WallsInterface w: walls) w.isColliding(player);
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

        for (WallsInterface w: walls) w.draw(g2d);

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
