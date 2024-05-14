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
import java.awt.geom.*;


/**
 * This class is for the walls or obstacles found in the map. 
 * It draws these in a GameCanvas object.
 */

public class Wall implements DrawingObject {
    
    private int x, y, width, height;
    private Color color;

    /**
     * Constructor that initializes the wall being drawn
     * @param x
     * @param y
     * @param width
     * @param height
     * @param color
     */
    public Wall (int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }
    
    /**
     * Method that draws the object
     */
    public void draw(Graphics2D g2d) {
        Rectangle2D.Double sq = new Rectangle2D.Double(x, y, width, height);
        g2d.setColor(color);
        g2d.fill(sq);
    }

    /**
     * Accessor methods for the x, y, width, and height of the wall
     */
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }

}
