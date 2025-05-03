import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Font;
import java.util.ArrayList;

public class MovingBrick extends ToughBrick {
    ArrayList<int[]> coordinates = new ArrayList<int[]>();
    int cooldown = 2;
    Color fontColor = null;
    int a = 50;
    public MovingBrick(int x, int y, int width, int height, int lives, int points) {
        super(x,y,width,height,lives,points);
        super.setHitLimit(lives);
        if (lives > 1000) {
            setColor(new Color(68, 25, 27));
            fontColor = Color.WHITE;
        }
        if (lives <= 100 && lives > 50) {
            setColor(new Color(71, 21, 69));
            fontColor = Color.WHITE;
        }
        if (lives <= 50 && lives > 30) {
            setColor(new Color(105, 29, 102));
            fontColor = Color.WHITE;
        }
        if (lives <= 30 && lives > 10) {
            setColor(new Color(158, 41, 154));
            fontColor = Color.WHITE;
        }
        if (lives <= 10 && lives > 3) {
            setColor(new Color(191, 48, 186));
            fontColor = Color.WHITE;
        }
        if (lives <= 3 && lives >= 1) {
            setColor(Color.PINK);
            fontColor = Color.WHITE;
        }
        setPoints(points);
    }
    public void renderParticles(Graphics2D g2) {

        int X = (int) (Math.random() * 30), Y = (int) (Math.random() * 10), x = getX(), y = getY();
        if (cooldown == 0) {
        if (Math.random() > .5) {
            X *= -1;
        }
        if (Math.random() > .5) {
            Y *= -1;
        }
        int[] a = {X, Y};
        coordinates.add(a);
        
        if (coordinates.size() > 2) {
                coordinates.remove(0);
        }
        cooldown = 2;
        }
        else {
            cooldown -= 1;
        }
        for (int i = 0; i < coordinates.size(); i++) {
            g2.setColor(new Color(191, 48, 186));
            g2.fillRect(coordinates.get(i)[0] + x + getWidth()/2, coordinates.get(i)[1] + y + getHeight()/2, 2, 2);
        }
    }
    public void update(BallSprite ball) {
        if (getX() > 426) {
            setDx(-5);
        }
        else if (getX() < 26) {
            setDx(5);
        }
        else if (getDx() == 0) {
            double a = Math.random();
            if (a > .5) {
                setDx(-5);
            }
            else{
                setDx(5);
            }
        }
    }
    
    public int isHit(Sprite ball) {
        // Check to see in the ball is hitting the brick
        int lives = getHitLimit();
        if (lives > 1000) {
            setColor(new Color(48, 15, 47));
        }
        if (lives <= 100 && lives > 50) {
            setColor(new Color(71, 21, 69));
        }
        if (lives <= 50 && lives > 30) {
            setColor(new Color(105, 29, 102));
        }
        if (lives <= 30 && lives > 10) {
            setColor(new Color(158, 41, 154));
        }
        if (lives <= 10 && lives > 3) {
            setColor(new Color(191, 48, 186));
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
                setColor(Color.pink);
            }
            // Determine hit direction and make ball bounce
            super.updateBall(ball);
            if (getHitLimit() <= 0) {
                return getPointsEarned(); // Broken, send back points
            }
        }
        return 0; // Not broken, no points
    }
}
