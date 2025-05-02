import java.awt.Color;
import java.awt.Point;
import java.net.URL;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Font;
//import javax.sound.sampled.AudioInputStream;
//import javax.sound.sampled.AudioSystem;
//import javax.sound.sampled.Clip;

public class Brick extends Sprite {
    //public static Clip clip; //Audio clip
    
    private int hitLimit; // How many hits a brick can take before being broken
    private int pointsEarned; // How many points a brick is worth once broken
    private int cooldown = 15;
    private int maxCooldown = 15; // Should be 150 miliseconds (used to balance so that you can't blitz through a 10 hp block)


    public Brick(int x, int y, int width, int height) {
        super(x, y, width, height);
        hitLimit = 1;
        pointsEarned = 100;
        setColor(Color.blue);
        //Set up the audio to playback
        try {
            //URL file;
            //AudioInputStream ais;
            //file = new URL("file:./hit.wav");
            //ais = AudioSystem.getAudioInputStream(file);
            //clip = AudioSystem.getClip();
            //clip.open(ais);
        } catch (Exception e) {    }

    }

    // Updates ball direction based on hit location
    // Returns points > 0 if the brick is broken, 0 if it is not hit or not
    // broken
    public int isHit(Sprite ball) {
        // Check to see in the ball is hitting the brick
        if (intersects(ball)) {
            //Play audio
            //clip.stop();
            //clip.setFramePosition(0);
            //clip.start();
            ball.setDx(ball.getDx() * 1.05);
            ball.setDy(ball.getDy() * 1.05);
            // Decrement the hit limit
            
            setHitLimit(getHitLimit()- ((BallSprite) ball).getDamage());

            
            // Determine hit direction and make ball bounce
            updateBall(ball);

            if (hitLimit <= 0) {
                return getPointsEarned(); // Broken, send back points
            }
        }
        return 0; // Not broken, no points
    }
    
    // Update ball directions
    public void updateBall(Sprite ball) {

        Point left = new Point(ball.getX(), ball.getY() + ball.getHeight() / 2);

        // Left/Right Collision
        if (left.y > getY() && left.y < getY() + getHeight()) {
            ball.invertDx();
        }
        // Top/Bottom Collision
        else {
            ball.invertDy();
        }
    }

    public int getHitLimit() {
        return hitLimit;
    }

    public void setHitLimit(int hitLimit) {
        this.hitLimit = hitLimit;
    }

    public int getPointsEarned() {
        return pointsEarned;
    }
    
    public void setCooldown(int a) {
        cooldown = a;
    }
    
    public int getCooldown() {
        return cooldown;
    }
    public int getMaxCooldown() {
        return maxCooldown;
    }
    public void setPoints(int pointsEarned) {
        this.pointsEarned = pointsEarned;
    }
    public void draw(Graphics2D g2) {
        g2.setColor(getColor());
        g2.fillRect(getX(), getY(), getWidth(), getHeight());
        
        Font theFont = new Font("Consolas", Font.BOLD, (10));
        g2.setColor(Color.WHITE);
        g2.setFont(theFont);
        int start = (getWidth() - g2.getFontMetrics().stringWidth("I"))/2;

        g2.drawString("I", getX()+(getWidth()/2), getY()+(getHeight()/2+3));
    }
    public void update(BallSprite ball) {
        cooldown += 1;
        cooldown -= 1;
    }
}
