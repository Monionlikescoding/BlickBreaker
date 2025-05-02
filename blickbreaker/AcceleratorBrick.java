
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Font;
import java.util.ArrayList;

public class AcceleratorBrick extends ToughBrick {
    
    Color fontColor = null;
    public AcceleratorBrick(int x, int y, int width, int height, int lives, int points) {
        super(x,y,width,height,lives,points);
        super.setHitLimit(lives);
        if (lives > 1000) {
            setColor(new Color(11, 23, 12));
        }
        if (lives <= 100 && lives > 50) {
            setColor(new Color(22, 54, 25));
            fontColor = Color.WHITE;
        }
        if (lives <= 50 && lives > 30) {
            setColor(new Color(40, 133, 49));
            fontColor = Color.WHITE;
        }
        if (lives <= 30 && lives > 10) {
            setColor(new Color(33, 163, 46));
            fontColor = Color.WHITE;
        }
        if (lives <= 10 && lives > 3) {
            setColor(new Color(17, 217, 38));
            fontColor = Color.WHITE;
        }
        if (lives <= 3 && lives > 1) {
            setColor(Color.GREEN);
            fontColor = Color.WHITE;
        }
        setPoints(points);
    }
    
    public void update(BallSprite ball) {
        int ballX = ball.getX(), ballY = ball.getY();
        int X = getX(), Y = ball.getY();
        if (Math.abs(X - ballX) < 60 && Math.abs(Y - ballY) < 60) {
        if (true) {
            ball.setDx(1.002*ball.getDx());
            }
            if (true) {
            ball.setDy(1.001*ball.getDy());
        }
        }
    }
    
    public int isHit(Sprite ball) {
        // Check to see in the ball is hitting the brick
        int lives = getHitLimit();
        if (lives > 1000) {
            setColor(new Color(11, 23, 12));
        }
        if (lives <= 100 && lives > 50) {
            setColor(new Color(22, 54, 25));
            fontColor = Color.WHITE;
        }
        if (lives <= 50 && lives > 30) {
            setColor(new Color(40, 133, 49));
            fontColor = Color.WHITE;
        }
        if (lives <= 30 && lives > 10) {
            setColor(new Color(33, 163, 46));
            fontColor = Color.WHITE;
        }
        if (lives <= 10 && lives > 3) {
            setColor(new Color(17, 217, 38));
            fontColor = Color.WHITE;
        }
        if (lives <= 3 && lives > 1) {
            setColor(Color.GREEN);
            fontColor = Color.WHITE;
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
                setColor(Color.green);
            }
            // Determine hit direction and make ball bounce
            super.updateBall(ball);
            if (getHitLimit() <= 0) {
                if (Math.abs(getX() - ball.getX()) < 60 && Math.abs(getY() - ball.getY()) < 60) {
                    ball.setDx(.85*ball.getDx());
                    ball.setDy(.80*ball.getDy());
                }
                return getPointsEarned();// Broken, send back points
            }
        }
        return 0; // Not broken, no points
    }
}