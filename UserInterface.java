/**
	This is a template for a Java file.
	
	@author Gabriel P. Hermosura (233080)
    @author Evan Sebastian M. Garcia (232776)
	@version 14 May 2024
	
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
**/


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

/**
 * This class was made for the images that were used in the GameCanvas.
 * It's basically like a Rectangle.2D object but with a BufferedImage instead
 */

public class UserInterface extends JComponent implements DrawingObject{
    
    private BufferedImage image;
    private int x, y;
    private Image img;
    private String fileName;

    /**
     * Constructor that initializes the UserInterface object
     * @param fileName
     * @param x
     * @param y
     * @param width
     * @param height
     */
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

    /**
     * Imports the image to be used
     */
    private void importImg() {
        InputStream is = getClass().getResourceAsStream(fileName);
        try {
            image = ImageIO.read(is);
        } catch(Exception e) {
            System.out.println("Exception at imporImg");
        }
    }

    /**
     * Draws the object
     * @param g2d
     */
    public void draw(Graphics2D g2d) {
        g2d.drawImage(img, x, y, null);
        
    }

    /**
     * Method that makes the object move, used by the player sprite
     * @param h
     * @param v
     */
    public void tick(int h, int v) {
        x += h;
        y += v;
    }

    /**
     * Mutator methods for x and y
     */
    public void setX(int amt) {
        x = amt;
    }
    public void setY(int amt) {
        y = amt;
    }

}