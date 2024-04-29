import java.awt.*;
import java.awt.geom.*;

public class Player implements GameEntity{
    // appearance and functionality of player

    private double radius, x, y;
    private Color color;

    public Player (double x, double y, double radius, Color color) {
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
