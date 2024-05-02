import java.awt.*;
import java.awt.geom.*;

public class RectangleWall implements WallsInterface {
    
    private double x, y, width, height;
    private Color color;

    public RectangleWall (double x, double y, double width, double height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }
    
    public void draw(Graphics2D g2d) {
        Rectangle2D.Double sq = new Rectangle2D.Double(x, y, width, height);
        g2d.setColor(color);
        g2d.fill(sq);
    }

    /*
    public boolean isColliding(Player p) {
        double distx = Math.abs(p.getX() - x);
        double disty = Math.abs(p.getY() - y);

        if (distx > (width/2 + p.getDiameter()/2)) return false;
        if (disty > (height/2 + p.getDiameter()/2)) return false;

        if (distx <= (width/2)) return true;
        if (disty <= (height/2)) return true;

        double cornerDistance_sq = ((distx - width/2) * (distx - width/2)) + ((disty - height/2) * (disty - height/2));

        return (cornerDistance_sq <= (p.getDiameter()/2) * (p.getDiameter()/2));
    } */

    // accessor methods:
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }

}
