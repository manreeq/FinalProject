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
 * This is the class for the fuse object by using a path
 * This object will be used as the game timer as well, as the length of the game
 * is determined by the length of the fuse
 */
public class Fuse {
    private double startx, starty, endx, endy;
    private double origin;
    public boolean isExploded;

    /**
     * Constructor method that sets the starting and end positions of the fuse
     * If the end position is the same as the start, it sets the isExploded variable to true
     */
    public Fuse(double startx, double starty, double endx, double endy) {
        this.startx = startx;
        this.starty = starty;
        this.endx = endx;
        this.endy = endy;
        origin = endy;
        isExploded = true;
    }

    /**
     * Draw method to draw the fuse onto the canvas
     */
    public void draw(Graphics2D g2d) {
        Path2D.Double fuse = new Path2D.Double();
        fuse.moveTo(startx, starty);
        fuse.lineTo(endx, endy);
        g2d.setColor(Color.GRAY);
        g2d.draw(fuse);
    }

    /**
     * Tick method to be continuosly iterated through in the thread
     * Decrements the end position of the fuse, making it shorter
     */
    public void tick(){
        
        if (endy> starty) {
            endy -= 0.05;
            isExploded = false;
        } else {
            isExploded = true;
        }
    }

    /**
     * Mutator method to set the end point back to its original value
     */
    public void restart() {
        endy = origin;
    }
}
