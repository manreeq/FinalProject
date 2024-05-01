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

}
