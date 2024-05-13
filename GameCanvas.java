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
    private boolean meHasPotato;
    private Circle potatoIndicator;
    
    private int potatoSwapCooldown;
    private boolean isColliding;
    private boolean enemyCollided;

    private double timeLeft = 250;

    //dash things
    private int dashDuration, dashCooldown;
    private Circle dashIndicator;

    //keybinds
    private boolean wPressed, aPressed, sPressed, dPressed, firstGame;

    //server
    private Player me;
    private Player enemy;
    private int playerID;

    private boolean meReady, enemyReady;

    private Circle meredi, themredi;


    public GameCanvas(int id) {
        playerID = id;
        p1Speed = 1;
        this.setPreferredSize(new Dimension(1000, 750));
        wc = Color.BLACK;
        walls = new ArrayList<>();
        firstGame = true;
        meReady = false;
        enemyReady = false;
        //entities = new ArrayList<>();

        //border walls
        bNorth = new Wall(0, -30, 1000, 40, wc);
        walls.add(bNorth);
        bSouth = new Wall(0, 740, 1000, 40, wc);
        walls.add(bSouth);
        bEast = new Wall(990, 0, 40, 800, wc);
        walls.add(bEast);
        bWest = new Wall(-30, 0, 40, 800, wc);
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

        meredi = new Circle(20, 500, 40, Color.RED);
        themredi = new Circle(20, 560, 40, Color.RED);

        //potato indicator
        potatoIndicator = new Circle(20, 80, 40, Color.GREEN);

        pButton = new PlayButton(300, 250, 400, 200);

        createPlayers();
        //starts the game loop
        t = new MyThread();
        t.start();

    }

    public void startGame() {
        if (playerID == 1) {
            me.setX(75); me.setY(75);
            enemy.setX(1000-105); enemy.setY(750-105);
        } else {
            enemy.setX(75); enemy.setY(75);
            me.setX(1000-105); me.setY(750-105);
        }
        
        fuse.restart();
        fuse.isExploded = false;
        //System.out.println("tite");
        
    }

    //game loop
    //i think this goes in the server
    private class MyThread extends Thread {
        public void run() {
            try {
                while (true) {
                    
                    Thread.sleep(5);

                    if (!(fuse.isExploded) && meReady && enemyReady) {
                        // y-axis movement
                        if ((sPressed) && (!wPressed)) movePlayerDown(me);
                        if ((wPressed) && (!sPressed)) movePlayerUp(me); 
                        if (!(wPressed || sPressed)) stopMovingY(me);

                        // x-axis movement
                        if ((dPressed) && (!aPressed)) movePlayerRight(me);
                        if ((aPressed) && (!dPressed)) movePlayerLeft(me);
                        if (!(aPressed || dPressed)) stopMovingX(me);

                        fuse.tick();

                        isColliding = me.collidePlayer(enemy);

                        if ((isColliding) && potatoSwapCooldown == 0 && (enemyCollided)) {
                            me.changePotatoStatus();
                            enemy.changePotatoStatus();
                            potatoSwapCooldown = 150;
                        }

                        if (potatoSwapCooldown > 0) potatoSwapCooldown -= 1;

                        if (dashDuration > 0) dashDuration -= 1;
                        else resetSpeed();

                        if (dashCooldown > 0) dashCooldown -= 1;
                        else dashIndicator.changeColor(Color.GREEN);

                        if (me.getPotatoStatus()) potatoIndicator.changeColor(Color.RED);
                        else potatoIndicator.changeColor(Color.GREEN);

                        me.tick();
                        enemy.tick();
                    
                    } else {
                        if (fuse.isExploded && meReady) meReady = false;
                        else if (fuse.isExploded && !meReady) continue;
                    }

                    if (fuse.isExploded) {
                        meReady = false;
                        //enemyReady = false;
                    }
                    if (meReady) meredi.changeColor(Color.GREEN);
                    else meredi.changeColor(Color.RED);
                    if (enemyReady) themredi.changeColor(Color.GREEN);
                    else themredi.changeColor(Color.RED);

                    repaint();
                }

            } catch(InterruptedException e) {
                System.out.println("Exception at thread");
            }
            
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
        
        g2d.setStroke(new BasicStroke((float) 15.0, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        for (Wall w: walls) w.draw(g2d);

        bomb.draw(g2d);
        fuse.draw(g2d);
        dashIndicator.draw(g2d);
        potatoIndicator.draw(g2d);
        meredi.draw(g2d);
        themredi.draw(g2d);

        me.draw(g2d);
        enemy.draw(g2d);

        if (fuse.isExploded) {

            pButton.draw(g2d);

            if (!firstGame) {
                if (meHasPotato) {
                    //show lose message
                }
                else {
                    //show win message
                }
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
            if (p.RightC(tempW)) return "right";
            if (p.LeftC(tempW) && p.getY() + p.getSide() > tempW.getY()) return "left";
            if (p.DownC(tempW)) return "down";
            if (p.UpC(tempW)) return "up";

        } return null;

    }

    public String playerCollision(Player p) {
        boolean tempC = false;
        Player otherPlayer = enemy;
        if (p.collidePlayer(otherPlayer)) {
            tempC = true;
        } else tempC = false;

        if (tempC) {
            if (p.playerRightC(otherPlayer)) return "right";
            if (p.playerLeftC(otherPlayer) && p.getY() + p.getSide() > otherPlayer.getY()) return "left";
            if (p.playerDownC(otherPlayer)) return "down";
            if (p.playerUpC(otherPlayer)) return "up";
        } return null;
    }

    //Accessors and mutators
    public void setEnemyReady(boolean ready) {
        enemyReady = ready;
    }

    public boolean getMeReady(){
        return meReady;
    }

    public boolean getColliding() {
        return isColliding;
    }

    public boolean getMePotato() {
        return me.getPotatoStatus();
    }

    public boolean getEnemyPotato() {
        return enemy.getPotatoStatus();
    }

    public void setEnemyCollided (boolean b){
        enemyCollided = b;
    }

    public void swapPotatoStatus(Player p){
        p.changePotatoStatus();
    }

    

    
    public void movePlayerUp(Player p){
        if (!(wallCollision(p) == "up") && !(playerCollision(p) == "up")) {
            p.setVSpeed(-p1Speed);
            repaint();
        } else {
            p.setVSpeed(0);
            //me.changePotatoStatus();
        }
    }

    public void movePlayerDown(Player p){
        if (!(wallCollision(p) == "down") && !(playerCollision(p) == "down")) {
            p.setVSpeed(p1Speed);
            repaint();
        } else p.setVSpeed(0);
    }

    public void movePlayerLeft(Player p){
        if (!(wallCollision(p) == "left") && !(playerCollision(p) == "left")) {
            p.setHSpeed(-p1Speed);
            repaint();
        } else p.setHSpeed(0);
    }

    public void movePlayerRight(Player p){
        if (!(wallCollision(p) == "right") && !(playerCollision(p) == "right")) {
            p.setHSpeed(p1Speed);
            repaint();
        } else p.setHSpeed(0);
    } 
    

    public int meGetX() {
        return me.getX();
    }
    public int meGetY() {
        return me.getY();
    }
    public void enemySetX(int amt) {
        enemy.setX(amt);
    }
    public void enemySetY(int amt) {
        enemy.setY(amt);
    }
    public Player getMe() {
        return me;
    }
    public Player getEnemy() {
        return enemy;
    }

    // dash methods
    public void dashPlayer(boolean b){
        if (dashCooldown == 0){
            dashCooldown = 300;
            dashDuration = 30;
            dashIndicator.changeColor(Color.RED);
            if (b) p1Speed += 2.5;
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

    public void stopMovingY(Player p){ 
        p.setVSpeed(0);
    }
    public void stopMovingX(Player p){
        p.setHSpeed(0);
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
                        System.out.println("pressed");
                        startGame();
                        meReady = true;
                        firstGame = false;
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
                System.out.println("Exception at imporImg");
            }
        }
    
        public void draw(Graphics2D g2d) {
            g2d.drawImage(img, x, y, null);
            
        }

    }


    //SERVER SHENANIGANS

    public void createPlayers() {
        if (playerID == 1) {
            me = new Player(75, 75, 30, Color.BLUE, 0, 0, true);
            enemy = new Player(1000-105, 750-105, 30, Color.RED, 0, 0, false);
        } else {
            me = new Player(1000-105, 750-105, 30, Color.RED, 0, 0, false);
            enemy = new Player(75, 75, 30, Color.BLUE, 0, 0, true);
        } 
    }


}
