import java.awt.*;
import java.awt.geom.*;

public class LineWall implements Walls {
    
    private double xs, xy, xe, ye, thickness, rotate;
    private Color color;

    public LineWall (double xs, double xy, double xe, double ye, int thickness, Color color) {
        this.xs = xs;
        this.xy = xy;
        this.xe = xe;
        this.ye = ye;
        this.thickness = thickness;
        this.color = color;
        rotate = 0;
    }
    
    public void draw(Graphics2D g2d) {
        Rectangle2D.Double sq = new Rectangle2D.Double(x, y, width, height);
        g2d.setColor(color);
        g2d.fill(sq);
    }

}
