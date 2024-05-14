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

/**
 * This class creates the player object for the game
 * The player object is what the user controls, and is where most significant game
 * fucntionalities are based on.
 */
public class Player implements DrawingObject{

    private int side, x, y;
    private int hspeed, vspeed;
    private boolean hasPotato;
    private String direction;
    private UserInterface up1, right1, left1, down1;
    UserInterface sprite;

    /**
     * Constructor method that defines postion, speed, and potato status of player
     * Also instantiates sprite image for movements
     */
    public Player (int x, int y, int side, int hspeed, int vspeed, boolean hasPotato) {
        this.x = x;
        this.y = y;
        this.side = side;
        this.hspeed = hspeed;
        this.vspeed = vspeed;
        this.hasPotato = hasPotato;
        direction = "down";
        sprite = null;
        getPlayerImage();
    }
    
    /**
     * Accessor method for getting the sprite based on movement direction of player
     */
    public void getPlayerImage() {

            up1 = new UserInterface("up1.png", x, y, side, side);
            down1 = new UserInterface("down1.png", x, y, side, side);
            left1 = new UserInterface("left1.png", x, y, side, side);
            right1 = new UserInterface("right1.png", x, y, side, side);
            
        }


    /**
     * Draw method for player
     * Chooses sprite based off of direction player is moving
     */
    public void draw(Graphics2D g2d) {
        switch(direction) {
            case "up":
                sprite = up1;
                break;
            case "down":
                sprite = down1;
                break;
            case "left":
                sprite = left1;
                break;
            case "right":
                sprite = right1;
                break;
        }
        sprite.draw(g2d);
    }

    /**
     * Tick method to be repeated throughout the thread
     * Updates the position of the player
     */
    public void tick() {
        
        x += hspeed;
        y += vspeed;

        up1.tick(hspeed, vspeed);
        right1.tick(hspeed, vspeed);
        left1.tick(hspeed, vspeed);
        down1.tick(hspeed, vspeed);
    }

    /**
     * Mutator method to set direction of the player
     */
    public void setDirection(String d){
        direction = d;
    }

    /**
     * Accessor method that returns the current direction of the player
     */
    public String getDirection(){
        return direction;
    }

    /**
     * Method to determine if a player is colliding with a wall on the right
     */
    public boolean RightC(Wall w) {
        if (x + side >= w.getX() && x < w.getX() && getY() + getSide() - 1 > w.getY() && getY() + 1 < w.getY() + w.getHeight()) {
            return true;
        } else return false;
    }
    /**
     * Method to determine if a player is colliding with a wall on the left
     */
    public boolean LeftC(Wall w) {
        if (x <= w.getX() + w.getWidth() && x + side > w.getX() + w.getWidth() && getY() + getSide() - 1 > w.getY() && getY() + 1 < w.getY() + w.getHeight()) {
            return true;
        } else return false;
    }
    /**
     * Method to determine if a player is colliding with a wall from above
     */
    public boolean DownC(Wall w) {
        if (y + side >= w.getY() && y < w.getY()) {
            return true;
        } else return false;
    }
    /**
     * Method to determine if a player is colliding with a wall from below
     */
    public boolean UpC(Wall w) {
        if (y <= w.getY() + w.getHeight() && y + side > w.getY() + w.getHeight()) {
            return true;
        } else return false;
    }

    /**
     * Method to determine if a player is colliding with another player on the right
     */
    public boolean playerRightC(Player p) {
        if (x + side >= p.getX() && x < p.getX() && getY() + getSide() - 1 > p.getY() && getY() + 1 < p.getY() + p.getSide()) {
            return true;
        } else return false;
    }
    /**
     * Method to determine if a player is colliding with another player on the left
     */
    public boolean playerLeftC(Player p ) {
        if (x <= p.getX() + p.getSide() && x + side > p.getX() + p.getSide() && getY() + getSide() - 1 > p.getY() && getY() + 1 < p.getY() + p.getSide()) {
            return true;
        } else return false;
    }
    /**
     * Method to determine if a player is colliding with another player from above
     */
    public boolean playerDownC(Player p) {
        if (y + side >= p.getY() && y < p.getY()) {
            return true;
        } else return false;
    }
    /**
     * Method to determine if a player is colliding with another player from below
     */
    public boolean playerUpC(Player p) {
        if (y <= p.getY() + p.getSide() && y + side > p.getY() + p.getSide()) {
            return true;
        } else return false;
    }

    /**
     * Method to determine if a collision occurs with a wall
     */
    public boolean collideWall(Wall w) {
        return (!(x + side <= w.getX() ||
        x >= w.getX() + w.getWidth() ||
        y >= w.getY() + w.getHeight() ||
        y + side <= w.getY()));
    }

    /**
     * Method to determiine if a collision occurs with another player
     */
    public boolean collidePlayer(Player p) {
        return (!(x + side <= p.getX() ||
        x >= p.getX() + p.getSide() ||
        y >= p.getY() + p.getSide() ||
        y + side <= p.getY()));
    }


    //movement methods
    
    /**
     * Mutator method to set the x value of the player, as well as its sprite
     */
    public void setX(int x) {
        this.x = x;
        up1.setX(x);
        right1.setX(x);
        left1.setX(x);
        down1.setX(x);
    }

    /**
     * Mutator method to set the y value of the player, as well as its sprite
     */
    public void setY(int y) {
        this.y = y;
        up1.setY(y);
        right1.setY(y);
        left1.setY(y);
        down1.setY(y);
    }

    /**
     * Mutator method to set the horizontal speed of the player
     */
    public void setHSpeed(int hspeed) {
        this.hspeed = hspeed;
    }

    /**
     * Mutator method to set the vertical speed of the player
     */
    public void setVSpeed(int vspeed) {
        this.vspeed = vspeed;
    }    

    /**
     * Accessor method that returns the x position of the player
     */
    public int getX() {
        return x;
    }
    /**
     * Accessor method that returns the y position of the player
     */
    public int getY() {
        return y;
    }
    /**
     * Accessor method that returns the size of the player
     */
    public int getSide() {
        return side;
    }
    /**
     * Accessor method that returns the horizontal speed of the player
     */
    public int getXSpeed() {
        return hspeed;
    }
    /**
     * Accessor method that returns the vertical speed of the player
     */
    public int getYSpeed() {
        return vspeed;
    }
    /**
     * Accessor method that returns if the player has the potato or not
     */
    public boolean getPotatoStatus(){
        return hasPotato;
    }

    /**
     * Mutator method to either give/take the potato to/from the player
     */
    public void changePotatoStatus(){
        if (getPotatoStatus() == true) {
            hasPotato = false;
        }
        else {
            hasPotato = true;
        }
    }

}
