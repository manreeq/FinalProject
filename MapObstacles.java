import java.awt.*;
import javax.sound.sampled.Line;

public class MapObstacles {
    
    private Color color;
    
    public MapObstacles(Color color) {
        this.color = color;
    }

    public void draw(Graphics2D g2d) {
        //border walls
        RectangleWall bNorth = new RectangleWall(0, -30, 1000, 40, color);
        bNorth.draw(g2d);
        RectangleWall bSouth = new RectangleWall(0, 755, 1000, 40, color);
        bSouth.draw(g2d);
        RectangleWall bEast = new RectangleWall(980, 0, 40, 800, color);
        bEast.draw(g2d);
        RectangleWall bWest = new RectangleWall(-35, -0, 40, 800, color);
        bWest.draw(g2d);

        //obstacles
        LineWall l1 = new LineWall(150, 250, 150, 380, 10, color);
        l1.draw(g2d);
        LineWall l2 = new LineWall(150, 460, 150, 580, 10, color);
        l2.draw(g2d);
        
        LineWall l3 = new LineWall(383, 260, 383, 470, 10, color);
        l3.draw(g2d);
        LineWall l4 = new LineWall(617, 260, 617, 470, 10, color);
        l4.draw(g2d);
        LineWall l5 = new LineWall(470, 365, 520, 365, 10, color);
        l5.draw(g2d);

        LineWall l6 = new LineWall(700, 600, 860, 400, 10, color);
        l6.draw(g2d);
        LineWall l7 = new LineWall(500, 100, 800, 100, 10, color);
        l7.draw(g2d);

        RectangleWall r1 = new RectangleWall(300, 600, 100, 100, color);
        r1.draw(g2d);
        RectangleWall r2 = new RectangleWall(730, 200, 100, 100, color);
        r2.draw(g2d);

    }

}
