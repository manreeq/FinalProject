import java.awt.*;
import java.awt.geom.*;

public class Player implements DrawingObject{
    // appearance and functionality of player

    private int side, x, y;
    private Color color;
    private int hspeed, vspeed;
    private boolean hasPotato;
    private String direction;
    private UserInterface up1, up2, right1, right2, left1, left2, down1, down2;

    public Player (int x, int y, int side, Color color, int hspeed, int vspeed, boolean hasPotato) {
        this.x = x;
        this.y = y;
        this.side = side;
        this.color = color;
        this.hspeed = hspeed;
        this.vspeed = vspeed;
        this.hasPotato = hasPotato;
        direction = "down";
        getPlayerImage();
    }
    
    public void getPlayerImage() {

            System.out.println("tiute");
            /*
            up1 = new UserInterface("up1.png", x, y, side, side);
            up2 = new UserInterface("up2.png", x, y, side, side);
            down1 = new UserInterface("down1.png", x, y, side, side);
            down2 = new UserInterface("down2.png", x, y, side, side);
            left1 = new UserInterface("left1.png", x, y, side, side);
            left2 = new UserInterface("left2.png", x, y, side, side);
            right1 = new UserInterface("right1.png", x, y, side, side);
            right2 = new UserInterface("right2.png", x, y, side, side);
             */
        }


    public void draw(Graphics2D g2d) {
        Rectangle2D.Double sq = new Rectangle2D.Double(x, y, side, side);
        g2d.setColor(color);
        g2d.fill(sq);

        UserInterface sprite = null;

        switch(direction) {
            case "up":
                sprite = up1;
                break;
            case "down":
                sprite = down1;
                break;
            case "left":
                sprite = left1;
                break;
            case "right":
                sprite = right1;
                break;
        }
        sprite.draw(g2d);
    }

    

    public void setDirection(String d){
        direction = d;
    }

    public String getDirection(){
        return direction;
    }

    // what wall
    public boolean RightC(Wall w) {
        if (x + side >= w.getX() && x < w.getX() && getY() + getSide() - 1 > w.getY() && getY() + 1 < w.getY() + w.getHeight()) {
            return true;
        } else return false;
    }
    public boolean LeftC(Wall w) {
        if (x <= w.getX() + w.getWidth() && x + side > w.getX() + w.getWidth() && getY() + getSide() - 1 > w.getY() && getY() + 1 < w.getY() + w.getHeight()) {
            return true;
        } else return false;
    }
    public boolean DownC(Wall w) {
        if (y + side >= w.getY() && y < w.getY()) {
            return true;
        } else return false;
    }
    public boolean UpC(Wall w) {
        if (y <= w.getY() + w.getHeight() && y + side > w.getY() + w.getHeight()) {
            return true;
        } else return false;
    }

    //what player
    public boolean playerRightC(Player p) {
        if (x + side >= p.getX() && x < p.getX() && getY() + getSide() - 1 > p.getY() && getY() + 1 < p.getY() + p.getSide()) {
            return true;
        } else return false;
    }
    public boolean playerLeftC(Player p ) {
        if (x <= p.getX() + p.getSide() && x + side > p.getX() + p.getSide() && getY() + getSide() - 1 > p.getY() && getY() + 1 < p.getY() + p.getSide()) {
            return true;
        } else return false;
    }
    public boolean playerDownC(Player p) {
        if (y + side >= p.getY() && y < p.getY()) {
            return true;
        } else return false;
    }
    public boolean playerUpC(Player p) {
        if (y <= p.getY() + p.getSide() && y + side > p.getY() + p.getSide()) {
            return true;
        } else return false;
    }

    //collision
    public boolean collideWall(Wall w) {
        return (!(x + side <= w.getX() ||
        x >= w.getX() + w.getWidth() ||
        y >= w.getY() + w.getHeight() ||
        y + side <= w.getY()));
    }

    public boolean collidePlayer(Player p) {
        return (!(x + side <= p.getX() ||
        x >= p.getX() + p.getSide() ||
        y >= p.getY() + p.getSide() ||
        y + side <= p.getY()));
    }


    //movement methods
    public void tick() {
        x += hspeed;
        y += vspeed;
        //System.out.println("tite");
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setHSpeed(int hspeed) {
        this.hspeed = hspeed;
    }
    public void setVSpeed(int vspeed) {
        this.vspeed = vspeed;
    }    

    // accessor methods:
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getSide() {
        return side;
    }
    public int getXSpeed() {
        return hspeed;
    }
    public int getYSpeed() {
        return vspeed;
    }

    public boolean getPotatoStatus(){
        return hasPotato;
    }

    public void changePotatoStatus(){
        if (getPotatoStatus() == true) {
            hasPotato = false;
        }
        else {
            hasPotato = true;
        }
    }

}
