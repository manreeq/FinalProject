import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class GameCanvas extends JComponent{
    
    private ArrayList<GameEntity> entities;
    private MapObstacles obstacles;

    public GameCanvas() {
        this.setPreferredSize(new Dimension(1000, 800));
        entities = new ArrayList<>();

        obstacles = new MapObstacles(Color.BLACK);

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);

        obstacles.draw(g2d);
    }

}
