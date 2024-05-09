import java.awt.*;

public interface DrawingObject {
    
    abstract void draw(Graphics2D g2d);
    abstract int getX();
    abstract int getY();

}
