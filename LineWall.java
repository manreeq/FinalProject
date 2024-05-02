import java.awt.*;
import java.awt.geom.*;

public class LineWall implements WallsInterface {
    
    private double xs, ys, xe, ye, rotate;
    private int thickness;
    private Color color;

    public LineWall (double xs, double ys, double xe, double ye, int thickness, Color color) {
        this.xs = xs;
        this.ys = ys;
        this.xe = xe;
        this.ye = ye;
        this.thickness = thickness;
        this.color = color;
        rotate = 0;
    }
    
    public void draw(Graphics2D g2d) {
        Line2D.Double l = new Line2D.Double(xs, ys, xe, ye);
        g2d.setStroke(new BasicStroke(thickness));
        g2d.setColor(color);
        g2d.rotate(Math.toRadians(rotate), xs, ys);
        g2d.draw(l);
    }

    public boolean isColliding(Player p) {
        return true;
    }

    // accessor methods:
    public double getXS() {
        return xs;
    }
    public double getYS() {
        return ys;
    }
    public double getXE() {
        return xe;
    }
    public double getYE() {
        return ye;
    }
    public double getThickness() {
        return thickness;
    }

}
