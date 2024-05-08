import java.awt.*;
import java.awt.geom.*;

public class Fuse {
    private double startx, starty, endx, endy;
    public boolean isExploded;

    public Fuse(double startx, double starty, double endx, double endy) {
        this.startx = startx;
        this.starty = starty;
        this.endx = endx;
        this.endy = endy;

        isExploded = false;
    }

    public void draw(Graphics2D g2d) {
        Path2D.Double fuse = new Path2D.Double();
        fuse.moveTo(startx, starty);
        fuse.lineTo(endx, endy);
        g2d.setColor(Color.BLACK);
        g2d.draw(fuse);
    }

    public void tick(){
        
        if (endy> starty) endy -= 0.05;
        else {
            System.out.println("BOOM");
            isExploded = true;
        }
    }
}