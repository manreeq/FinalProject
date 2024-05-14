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


/**
 * This class is the main method for running that game on a client.
 * Creates the game frame, conencts to the network, and establishes keybinds
 */
public class GameStarter {
    
    /**
     * Main method of GameStarter
     * @param args
     */
    public static void main(String[] args) {
        GameFrame gf = new GameFrame();
        gf.connectToServer();
        gf.setUpGUI();
        gf.addKeyBindings();
    }

    
}
