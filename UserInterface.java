import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class UserInterface extends JComponent {
    
    private BufferedImage image;
    private int x, y;
    private Image img;
    private String fileName;

    public UserInterface(String fileName, int x, int y, int width, int height) {
        
        this.fileName = fileName;
        this.x = x;
        this.y = y;
        importImg();
        try {
            img = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        } catch (Exception e) {
            System.out.println(fileName);
        }
        
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream(fileName);
        try {
            image = ImageIO.read(is);
        } catch(Exception e) {
            System.out.println("Exception at imporImg");
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(img, x, y, null);
        
    }

    public void tick(int h, int v) {
        x += h;
        y += v;
    }

    public void setX(int amt) {
        x = amt;
    }

    public void setY(int amt) {
        y = amt;
    }

}