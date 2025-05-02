import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Font;
import java.util.ArrayList;

public class TeleportingBrick extends ToughBrick {
    /*
     * Creates a tough brick that teleports when hit. 
     * It tries to not teleport behind bricks, but i dunno where i went wrong
     * 
     */
    Color fontColor = null;
    
    public TeleportingBrick(int x, int y, int width, int height, int lives, int points) {
        super(x,y,width,height,lives,points);
        super.setHitLimit(lives);
        if (lives > 1000) {
            setColor(new Color(46, 12, 11));
            fontColor = Color.WHITE;
        }
        if (lives <= 100 && lives > 50) {
            setColor(new Color(74, 18, 16));
            fontColor = Color.WHITE;
        }
        if (lives <= 50 && lives > 30) {
            setColor(new Color(102, 24, 21));
            fontColor = Color.WHITE;
        }
        if (lives <= 30 && lives > 10) {
            setColor(new Color(138, 33, 29));
            fontColor = Color.WHITE;
        }
        if (lives <= 10 && lives > 3) {
            setColor(new Color(179, 38, 32));
            fontColor = Color.WHITE;
        }
        if (lives <= 3 && lives >= 1) {
            setColor(Color.RED);
            fontColor = Color.WHITE;
        }
        setPoints(points);
    }
    
    public int isHit(Sprite ball) {
        // Check to see in the ball is hitting the brick
        int hitlim = getHitLimit();
        if (hitlim <= 100 && hitlim > 50) {
            setColor(new Color(46, 12, 11));
        }
        if (hitlim <= 50 && hitlim > 30) {
            setColor(new Color(74, 18, 16));
        }
        if (hitlim <= 30 && hitlim > 10) {
            setColor(new Color(102, 24, 21));
        }
        if (hitlim <= 10 && hitlim > 3) {
            setColor(new Color(138, 33, 29));
        }
        if (hitlim <= 3 && hitlim >= 1) {
            setColor(new Color(179, 38, 32));
        }
        if (intersects(ball)) {
            //Play audio
            //clip.stop();
            //clip.setFramePosition(0);
            //clip.start();
            
            // Decrement the hit limit
            if (getCooldown() <= 0) {
                setHitLimit(getHitLimit()- ((BallSprite) ball).getDamage());
                setCooldown(getMaxCooldown());
            }
            if(getHitLimit() == 1) {
                setColor(Color.RED);
            }
            // Determine hit direction and make ball bounce
            // Grid of 9 x 9 bricks 450 x 270 81 different possible locations per brick
            super.updateBall(ball);
            ArrayList<Brick> listOfBricks = Main.getBricks();
            setX(25 + (50 * (int)(Math.round(8*Math.random()))));
            setY(30 + (20 * (int)(Math.round(8*Math.random()))));
            
            if (getHitLimit() <= 0) {
                return getPointsEarned(); // Broken, send back points
            }
        }
        return 0; // Not broken, no points
    }
}
