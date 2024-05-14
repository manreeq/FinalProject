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
 * This class defines circle objects to be used in the game canvas
 * Uses Graphics2D to create a circle with customizable parameters
 */
public class Circle {
    private double x, y, size;
    private Color color;

    /**
     * Constructor method that defines x and y positions, size, and color of the circle
     */
    public Circle(double x, double y, double size, Color color) {
        this.x = x;
        this.y=y;
        this.size = size;
        this.color = color;
    }

    /**
     * Draw method to create the circle 
     */
    public void draw(Graphics2D g2d) {
        Ellipse2D.Double circle = new Ellipse2D.Double(x, y, size, size);
        g2d.setColor(color);
        g2d.fill(circle);
    }

    /**
     * Mutator method to change the color of the circle
     */
    public void changeColor(Color newColor){
        color = newColor;
    }

}


