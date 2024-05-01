import java.awt.*;
import java.awt.geom.*;

public class CircleWall implements Walls {
    
    private double x, y, radius;
    private Color color;

    public CircleWall (double x, double y, double radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }
    
    public void draw(Graphics2D g2d) {
        Ellipse2D.Double sq = new Ellipse2D.Double(x, y, radius, radius);
        g2d.setColor(color);
        g2d.fill(sq);
    }

}
