import java.awt.*;

public class MapObstacles {
    
    private Color color;
    
    public MapObstacles(Color color) {
        this.color = color;
    }

    public void draw(Graphics2D g2d) {
        RectangleWall bNorth = new RectangleWall(0, -30, 1000, 40, color);
        bNorth.draw(g2d);
        RectangleWall bSouth = new RectangleWall(0, 755, 1000, 40, color);
        bSouth.draw(g2d);
        RectangleWall bEast = new RectangleWall(980, 0, 40, 800, color);
        bEast.draw(g2d);
        RectangleWall bWest = new RectangleWall(-35, -0, 40, 800, color);
        bWest.draw(g2d);

        CircleWall test = new CircleWall(450, 350, 100, Color.BLUE); test.draw(g2d);
    }

}
