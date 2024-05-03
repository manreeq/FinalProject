import java.awt.*;

public abstract class PlayerEntity {
    
    abstract void draw(Graphics2D g2d);

    abstract boolean collideWall(Wall w);

    abstract boolean collidePlayer(Player p);

    abstract boolean collidePowerUp(PowerUp pu);

}
