import java.awt.*;
import java.awt.geom.*;

public class Bomb {
    private double x, y, size;
    private Color color;

    public Bomb(double x, double y, double size, Color color) {
        this.x = x;
        this.y=y;
        this.size = size;
        this.color = color;
    }

    public void draw(Graphics2D g2d) {
        Ellipse2D.Double b = new Ellipse2D.Double(x, y, size, size);
        g2d.setColor(color);
        g2d.fill(b);
    }

}


