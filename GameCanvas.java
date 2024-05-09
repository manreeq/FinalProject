import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;
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
    private PlayButton pButton;
    private Thread t;

    //bomb things
    private Circle bomb;
    private Fuse fuse;

    private double timeLeft = 250;

    //dash things
    private int dashDuration, dashCooldown;
    private Circle dashIndicator;

    

    private boolean wPressed, aPressed, sPressed, dPressed, p;


    public GameCanvas() {
        p1Speed = 1;
        this.setPreferredSize(new Dimension(1000, 800));
        delay = 5;
        wc = Color.BLACK;
        walls = new ArrayList<>();
        p = false;
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

        //bomb parts
        bomb = new Circle(900, 20, 60, Color.GRAY);
        fuse = new Fuse(930, 50, 930, 70);

        //dash indicator
        dashIndicator = new Circle(20, 20, 40, Color.GREEN);

        p1 = new Player(75, 75, 30, Color.BLUE, 0, 0);
        p2 = new Player(75, 75, 30, Color.RED, 0, 0);

        //starts the game loop
        t = new MyThread();
        t.start();
    }

    public void startGame() {
        p1.setX(75); p1.setY(75);
        p2.setX(75); p2.setY(75);
        fuse.restart();
        fuse.isExploded = false;
        //System.out.println("tite");
        
    }

    //game loop
    private class MyThread extends Thread {
        public void run() {
            try {
                while (true) {
                    // y-axis movement
                    System.out.println("tite");
                    if ((sPressed) && (!wPressed)) movePlayerDown();
                    if ((wPressed) && (!sPressed)) movePlayerUp(); 
                    if (!(wPressed || sPressed)) stopMovingY();

                    // x-axis movement
                    if ((dPressed) && (!aPressed)) movePlayerRight();
                    if ((aPressed) && (!dPressed)) movePlayerLeft();
                    if (!(aPressed || dPressed)) stopMovingX();
                    
                    
                    if (!(fuse.isExploded)) 
                    {
                        fuse.tick();

                        if (dashDuration > 0) dashDuration -= 1;
                        else resetSpeed();

                        if (dashCooldown > 0) dashCooldown -= 1;
                        else dashIndicator.changeColor(Color.GREEN);

                        p1.tick();
                        p2.tick();
                        repaint();
                        Thread.sleep(5);

                    } else {
                        repaint();
                        
                        //this loops parin infinitely so kahit pinindot mo yung play again, forever siyang magrerepaint
                        //the p is to iterate to the next iteration and go back to the game "round"
                        if (p) continue;
                    }
                }

            } catch(InterruptedException e) {
                
            }
            
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
        
        //doesnt get used
        Stroke defaultStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke((float) 15.0, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        for (Wall w: walls) w.draw(g2d);

        bomb.draw(g2d);
        fuse.draw(g2d);
        dashIndicator.draw(g2d);

        p1.draw(g2d);

        if (fuse.isExploded) {
            pButton = new PlayButton(300, 250, 400, 200);
            pButton.draw(g2d);
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
            if (p.RightC(tempW)) return "right";
            if (p.LeftC(tempW) && p.getY() + p.getSide() > tempW.getY()) return "left";
            if (p.DownC(tempW)) return "down";
            if (p.UpC(tempW)) return "up";

        } return null;

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
    
    // dash methods
    public void dashPlayer(boolean b){
        if (dashCooldown == 0){
            dashCooldown = 300;
            dashDuration = 30;
            dashIndicator.changeColor(Color.RED);
            if (b) p1Speed += 2;
            else 
            p1Speed += 4;
        }
    }

    public void resetSpeed(){
        p1Speed = 1;
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

    /*
    public void stopMovingUp() {
        if (p1.getYSpeed() < 0) p1.setVSpeed(0);
    }
    public void stopMovingDown() {
        if (p1.getYSpeed() > 0) p1.setVSpeed(0);
    }
    public void stopMovingLeft() {
        if (p1.getXSpeed() < 0) p1.setHSpeed(0);
    }
    public void stopMovingRight() {
        if (p1.getXSpeed() > 0) p1.setHSpeed(0);
    } */


    public Player getPlayer1() {
        return p1;
    }

    public class PlayButton {
    
        private BufferedImage image;
        private int x, y;
        private Image img;
    
        public PlayButton(int x, int y, int width, int height) {
            
            this.x = x;
            this.y = y;
            importImg();

            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (fuse.isExploded) {
                        startGame();
                        p = true;
                        System.out.println(t.isAlive());
                    }
                }
            });

            img = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
    
        }
    
        private void importImg() {
            InputStream is = getClass().getResourceAsStream("play.png");
            try {
                image = ImageIO.read(is);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    
        public void draw(Graphics2D g2d) {
            g2d.drawImage(img, x, y, null);
            
        }

    }

}
