import java.awt.*;
import java.awt.geom.*;

public class LineWall implements WallsInterface {
    
    private double xs, xy, xe, ye, rotate;
    private int thickness;
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
        Line2D.Double l = new Line2D.Double(xs, xy, xe, ye);
        g2d.setStroke(new BasicStroke(thickness));
        g2d.setColor(color);
        g2d.rotate(Math.toRadians(rotate), xs, xy);
        g2d.draw(l);
    }

}
