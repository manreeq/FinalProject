import java.awt.*;
import java.awt.geom.*;

public class Player implements GameEntity{
    // appearance and functionality of player

    private double radius, x, y;
    private Color color;
    private int hspeed, vspeed;

    public Player (double x, double y, double radius, Color color, int hspeed, int vspeed) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
        this.hspeed = hspeed;
        this.vspeed = vspeed;
    }
    
    public void draw(Graphics2D g2d) {
        Ellipse2D.Double sq = new Ellipse2D.Double(x, y, radius, radius);
        g2d.setColor(color);
        g2d.fill(sq);
    }

    public void moveRight() {
        this.x += hspeed;
    }

    public void moveLeft(){
        this.x -= hspeed;
    }

    public void moveUp() {
        this.y -= vspeed;
    }

    public void moveDown(){
        this.y += vspeed;
    }

    public void changeHSpeed(int speedChange){
        hspeed += speedChange;
    }

    public void changeVSpeed (int speedChange) {
        vspeed += speedChange;
    }

}
