import java.awt.*;
import java.awt.geom.*;

public class CircleWall implements WallsInterface {
    
    private double x, y, diameter;
    private Color color;

    public CircleWall (double x, double y, double diameter, Color color) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.color = color;
    }
    
    public void draw(Graphics2D g2d) {
        Ellipse2D.Double sq = new Ellipse2D.Double(x, y, diameter, diameter);
        g2d.setColor(color);
        g2d.fill(sq);
    }

}
