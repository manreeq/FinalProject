import java.awt.*;
import java.awt.geom.*;

public class Player extends PlayerEntity{
    // appearance and functionality of player

    private int side, x, y;
    private Color color;
    private int hspeed, vspeed;

    public Player (int x, int y, int side, Color color, int hspeed, int vspeed) {
        this.x = x;
        this.y = y;
        this.side = side;
        this.color = color;
        this.hspeed = hspeed;
        this.vspeed = vspeed;
    }
    
    public void draw(Graphics2D g2d) {
        Rectangle2D.Double sq = new Rectangle2D.Double(x, y, side, side);
        g2d.setColor(color);
        g2d.fill(sq);
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

    //collision
    public boolean collideWall(Wall w) {
        return (!(x + side <= w.getX() ||
        x >= w.getX() + w.getWidth() ||
        y >= w.getY() + w.getHeight() ||
        y + side <= w.getY()));
    }

    public boolean collidePlayer(Player p) {
        return false;
    }
    
    public boolean collidePowerUp(PowerUp pu) {
        return false;
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

}
