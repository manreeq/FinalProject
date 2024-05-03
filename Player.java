import java.awt.*;
import java.awt.geom.*;

public class Player implements PlayerEntity{
    // appearance and functionality of player

    private double side, x, y;
    private Color color;
    private double hspeed, vspeed;

    public Player (double x, double y, double side, Color color, double hspeed, double vspeed) {
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
        if (x + side >= w.getX() && x < w.getX()) {
            System.out.println("right");
            return true;
        } else return false;
    }
    public boolean LeftC(Wall w) {
        if (x <= w.getX() + w.getWidth() && x + side > w.getX() + w.getWidth()) {
            System.out.println("left");
            return true;
        } else return false;
    }
    public boolean DownC(Wall w) {
        if (y + side >= w.getY() && y < w.getY()) {
            System.out.println("down");
            return true;
        } else return false;
    }
    public boolean UpC(Wall w) {
        if (y <= w.getY() + w.getHeight() && y + side > w.getY() + w.getHeight()) {
            System.out.println("up");
            return true;
        } else return false;
    }

    //collision
    public boolean collideWall(Wall w) {
        return (!(x + side <= w.getX() ||
        x >= w.getX() + w.getWidth() ||
        y >= w.getY() + w.getHeight() ||
        y + side <= w.getY()));
        
        /* 
        
            if (x + side > w.getX() && x < w.getX()) {
                System.out.println("right");
                return "right";
            }
            else if (x < w.getX() + w.getWidth() && x + side > w.getX() + w.getWidth()) {
                System.out.println("left");
                return "left";
            }
            else if (y + side > w.getY() && y < w.getY()) {
                System.out.println("down");
                return "down";
            }
            else if (y < w.getY() + w.getHeight() && y + side > w.getY() + w.getHeight()) {
                System.out.println("up");
                return "up";
            }
            return null;
        } 
        return null;*/
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
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void setHSpeed(double hspeed) {
        this.hspeed = hspeed;
    }
    public void setVSpeed(double vspeed) {
        this.vspeed = vspeed;
    }

    // accessor methods:
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getSide() {
        return side;
    }

}
