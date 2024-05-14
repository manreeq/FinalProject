import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
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
    private UserInterface potatoBomb, potatoIndicatorImage;
    private Fuse fuse;
    private Circle potatoIndicator;
    
    private int potatoSwapCooldown;
    private boolean isColliding;
    private boolean exploded;

    //dash things
    private int dashDuration, dashCooldown;
    private Circle dashIndicator;
    private UserInterface dashImage;

    //keybinds
    private boolean wPressed, aPressed, sPressed, dPressed, firstGame;

    //server
    private Player me;
    private Player enemy;
    private int playerID;

    private boolean meReady, enemyReady, ongoing, collided;

    private UserInterface stoneTile, waitingIndicator, winImage, loseImage;
    private UserInterface enemyWaiting;
    private ArrayList<UserInterface> tiles;


    public GameCanvas(int id) {
        //ongoing = false;
        playerID = id;
        p1Speed = 1;
        this.setPreferredSize(new Dimension(1000, 750));
        wc = Color.BLACK;
        walls = new ArrayList<>();
        tiles = new ArrayList<>();
        firstGame = true;
        meReady = false;
        enemyReady = false;
        //entities = new ArrayList<>();

        //stoneTile bg
        int col = 0;
        int row = 0;
        int tempX = 0;
        int tempY = 0; 

        while (col < 20 && row < 15) {
            stoneTile = new UserInterface("stoneTile.png", tempX, tempY, 50, 50);
            tiles.add(stoneTile);
            col++;
            tempX += 50;

            if (col == 20) {
                col = 0;
                tempX = 0;
                row++;
                tempY += 50;
                }
            }


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
        potatoBomb = new UserInterface("potato.png", 870, -10, 120, 120);
        fuse = new Fuse(930, 80, 930, 330);

        //dash indicator
        dashIndicator = new Circle(20, 20, 40, Color.GREEN);
        dashImage = new UserInterface("dash.png", 27, 27, 25, 25);
        

        //potato indicator
        potatoIndicator = new Circle(20, 80, 40, Color.GREEN);
        potatoIndicatorImage = new UserInterface("potato.png", 27, 87, 25, 25);

        

        String s;
        String a;
        if (playerID == 1) {
            s = "p1Ready.png";
            a = "p2ReadyWaiting.png";
        } else {
            s = "p2Ready.png";
            a = "p1ReadyWaiting.png";
        }
        pButton = new PlayButton(300, 250, 400, 200);
        waitingIndicator = new UserInterface(s, 300, 250, 400, 200);
        winImage = new UserInterface("winner.png", 350, 190, 300, 60);
        loseImage = new UserInterface("loser.png", 350, 190, 300, 60);
        enemyWaiting = new UserInterface(a, 300, 190, 400, 100);

        createPlayers();
        //starts the game loop
        t = new MyThread();
        t.start();

    }

    public void startGame() {
        System.out.println("tite");
        
        if (playerID == 1) {
            me.setX(75); me.setY(75);
            enemy.setX(1000-105); enemy.setY(750-105);
        } else {
            enemy.setX(75); enemy.setY(75);
            me.setX(1000-105); me.setY(750-105);
        }
        
        ongoing = true;
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
                    //System.out.println(exploded);
                    //me.getPlayerImage();
                    //enemy.getPlayerImage();

                    if (!exploded && meReady && enemyReady) {
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

                        if (collided && potatoSwapCooldown == 0) { 
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
                        if (exploded && meReady) {
                            meReady = false;
                            ongoing = false;
                            repaint();
                        } else if (exploded && !meReady){
                            repaint();
                            continue;
                        } 
                    }

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

        for (UserInterface t: tiles) t.draw(g2d);
    
        for (Wall w: walls) w.draw(g2d);
        
        fuse.draw(g2d);
        potatoBomb.draw(g2d);

        dashIndicator.draw(g2d);
        dashImage.draw(g2d);

        potatoIndicator.draw(g2d);
        potatoIndicatorImage.draw(g2d);

        me.draw(g2d);
        enemy.draw(g2d);
        
        if (!ongoing) {
            pButton.draw(g2d);
        } else if (ongoing && meReady && !enemyReady) waitingIndicator.draw(g2d);

        if (!ongoing && !firstGame && (!meReady & !enemyReady)) {
            if (!me.getPotatoStatus()) winImage.draw(g2d);
            else loseImage.draw(g2d);
        } else if (!ongoing && (!meReady & enemyReady)) {
            enemyWaiting.draw(g2d);
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


    

    
    public void movePlayerUp(Player p){
        if (!(wallCollision(p) == "up") && !(playerCollision(p) == "up")) {
            p.setVSpeed(-p1Speed);
            p.setDirection("up");
            repaint();
        } else {
            p.setVSpeed(0);
        }
    }

    public void movePlayerDown(Player p){
        if (!(wallCollision(p) == "down") && !(playerCollision(p) == "down")) {
            p.setVSpeed(p1Speed);
            p.setDirection("down");
            repaint();
        } else p.setVSpeed(0);
    }

    public void movePlayerLeft(Player p){
        if (!(wallCollision(p) == "left") && !(playerCollision(p) == "left")) {
            p.setHSpeed(-p1Speed);
            p.setDirection("left");
            repaint();
        } else p.setHSpeed(0);
    }

    public void movePlayerRight(Player p){
        if (!(wallCollision(p) == "right") && !(playerCollision(p) == "right")) {
            p.setHSpeed(p1Speed);
            p.setDirection("right");
            repaint();
        } else p.setHSpeed(0);
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
                    if (!ongoing) {
                        System.out.println("pressed");
                        startGame();
                        meReady = true;
                        firstGame = false;
                        exploded = false;
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
            me = new Player(75, 75, 30, 0, 0, true);
            enemy = new Player(1000-105, 750-105, 30, 0, 0, false);
        } else {
            me = new Player(1000-105, 750-105, 30, 0, 0, false);
            enemy = new Player(75, 75, 30, 0, 0, true);
        } 
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

    public void setCollided (boolean b){
        collided = b;
    }

    public void swapPotatoStatus(Player p){
        p.changePotatoStatus();
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

    public void setExploded(boolean b) {
        exploded = b;
    }

    public boolean getFuseExploded() {
        return fuse.isExploded;
    }

    public void setEnemyDirection(int i) {
        if (i == 1) enemy.setDirection("up");
        if (i == 2) enemy.setDirection("down");
        if (i == 3) enemy.setDirection("left");
        if (i == 4) enemy.setDirection("right");
    }

    public int meGetDirection(){
        int x = 0;
        if (me.getDirection() == "up") x = 1;
        if (me.getDirection() == "down") x = 2;
        if (me.getDirection() == "left") x = 3;
        if (me.getDirection() == "right") x = 4;

        return x;
    }


}
