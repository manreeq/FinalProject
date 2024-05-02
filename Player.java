import java.awt.*;
import java.awt.geom.*;

public class Player implements PlayerEntity{
    // appearance and functionality of player

    private double diameter, x, y;
    private Color color;
    private double hspeed, vspeed;

    public Player (double x, double y, double diameter, Color color, double hspeed, double vspeed) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.color = color;
        this.hspeed = hspeed;
        this.vspeed = vspeed;
    }
    
    public void draw(Graphics2D g2d) {
        Ellipse2D.Double sq = new Ellipse2D.Double(x, y, diameter, diameter);
        g2d.setColor(color);
        g2d.fill(sq);
    }


    public boolean collideWall(WallsInterface w) {
        
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
    public double getDiameter() {
        return diameter;
    }


}
