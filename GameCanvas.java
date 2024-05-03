import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class GameCanvas extends JComponent {
    
    private ArrayList<Wall> walls;
    private Player p1;
    private Player p2;
    //private Player[] players;

    //private Timer timer;
    private int delay;
    private Color wc;
    
    private Wall bNorth;
    private Wall bSouth;
    private Wall bEast;
    private Wall bWest;
    private Wall r1;
    private Wall r2;
    private Wall l1;
    private Wall l2;
    private Wall l3;
    private Wall l4;
    private Wall l5;
    private Wall l6;
    private Wall l7;
    private int p1Speed;

    private boolean wPressed, aPressed, sPressed, dPressed;


    public GameCanvas() {
        p1Speed = 1;
        this.setPreferredSize(new Dimension(1000, 800));
        delay = 5;
        wc = Color.BLACK;
        walls = new ArrayList<>();
        //entities = new ArrayList<>();

        //border walls
        bNorth = new Wall(0, -30, 1000, 40, wc);
        walls.add(bNorth);
        bSouth = new Wall(0, 755, 1000, 40, wc);
        walls.add(bSouth);
        bEast = new Wall(980, 0, 40, 800, wc);
        walls.add(bEast);
        bWest = new Wall(-35, -0, 40, 800, wc);
        walls.add(bWest);

        //obstacles
        l1 = new Wall(150, 250, 130, 10, wc);
        walls.add(l1);
        l2 = new Wall(150, 460, 120, 10, wc);
        walls.add(l2);
        
        l3 = new Wall(383, 260, 10, 210, wc);
        walls.add(l3);
        l4 = new Wall(617, 260, 10, 210, wc);
        walls.add(l4);
        l5 = new Wall(470, 365, 10, 50, wc);
        walls.add(l5);

        l6 = new Wall(550, 580, 250, 10, wc);
        walls.add(l6);
        l7 = new Wall(500, 100, 300, 10, wc);
        walls.add(l7);

        r1 = new Wall(300, 600, 100, 100, wc);
        walls.add(r1);
        r2 = new Wall(730, 200, 100, 100, wc);
        walls.add(r2);

        p1 = new Player(75, 75, 30, Color.BLUE, 0, 0);
        p2 = new Player(75, 75, 30, Color.RED, 0, 0);

        /*
        ActionListener timerListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {


                //System.out.println(wallCollision(p1));
                p1.tick();
                p2.tick();
                
                repaint();
            }
        }; */

        //timer = new Timer(delay, timerListener);
        //timer.start();

        Thread t = new MyThread();
        t.start();

    }

    private class MyThread extends Thread {
        public void run() {
            try {
                while (true) {
                    // y-axis movement
                    if (wPressed) movePlayerUp();
                    if (sPressed) movePlayerDown();
                    if (!(wPressed || sPressed)) stopMovingY();

                    // x-axis movement
                    if (aPressed) movePlayerLeft();
                    if (dPressed) movePlayerRight();
                    if (!(aPressed || dPressed)) stopMovingX();
                    
                    

                    p1.tick();
                    p2.tick();
                    repaint();
                    Thread.sleep(5);
                }   
            } catch(InterruptedException e) {
                
            }
            
        }

        
    }

    public String wallCollision(Player p) {
        boolean tempB = false;
        Wall tempW = null;
        for (Wall w: walls) {
            if (p.collideWall(w)) {
                tempB = true;
                tempW = w;
                break;
            } else tempB = false;
        }

        if (tempB) {
            if (p1.LeftC(tempW)) return "left";
            if (p1.RightC(tempW)) return "right";
            if (p1.DownC(tempW)) return "down";
            if (p1.UpC(tempW)) return "up";
        } return null;

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);

        for (Wall w: walls) w.draw(g2d);

        p1.draw(g2d);
    }

    
    public void movePlayerUp(){
        if (!(wallCollision(p1) == "up")) {
            p1.setVSpeed(-p1Speed);
            repaint();
        } else p1.setVSpeed(0);
    }

    public void movePlayerDown(){
        if (!(wallCollision(p1) == "down")) {
            p1.setVSpeed(p1Speed);
            repaint();
        } else p1.setVSpeed(0);
    }

    public void movePlayerLeft(){
        if (!(wallCollision(p1) == "left")) {
            p1.setHSpeed(-p1Speed);
            repaint();
        } else p1.setHSpeed(0);
    }

    public void movePlayerRight(){
        if (!(wallCollision(p1) == "right")) {
            p1.setHSpeed(p1Speed);
            repaint();
        } else p1.setHSpeed(0);
    } 

    public void wPressed(boolean b) {
        wPressed = b;
    }
    public void aPressed(boolean b) {
        aPressed = b;
    }
    public void sPressed(boolean b) {
        sPressed = b;
    }
    public void dPressed(boolean b) {
        dPressed = b;
    }

    public void stopMovingY(){ 
        p1.setVSpeed(0);
    }
    

    public void stopMovingX(){
        p1.setHSpeed(0);
    }


    public Player getPlayer1() {
        return p1;
    }

}
