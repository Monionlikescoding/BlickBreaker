import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Font;
public class ToughBrick extends Brick {
    
    Color fontColor = null;
    
    public ToughBrick(int x, int y, int width, int height, int lives, int points) {
        super(x,y,width,height);
        super.setHitLimit(lives);
        if (lives > 1000) {
            setColor(Color.DARK_GRAY);
            fontColor = Color.WHITE;
        }
        if (lives <= 100 && lives > 50) {
            setColor(new Color(4, 4, 20));
            fontColor = Color.WHITE;
        }
        if (lives <= 50 && lives > 30) {
            setColor(new Color(8, 8, 40));
            fontColor = Color.WHITE;
        }
        if (lives <= 30 && lives > 10) {
            setColor(new Color(12, 12, 60));
            fontColor = Color.WHITE;
        }
        if (lives <= 10 && lives > 3) {
            setColor(new Color(16, 16, 80));
            fontColor = Color.WHITE;
        }
        if (lives <= 3 && lives > 1) {
            setColor(new Color(20, 20, 100));
            fontColor = Color.WHITE;
        }
        setPoints(points);
    }
    
    public int isHit(Sprite ball) {
        // Check to see in the ball is hitting the brick
        int hitlim = getHitLimit();
        if (hitlim <= 100 && hitlim > 50) {
            setColor(new Color(4, 4, 20));
            fontColor = Color.WHITE;
        }
        if (hitlim <= 50 && hitlim > 30) {
            setColor(new Color(8, 8, 40));
            fontColor = Color.WHITE;
        }
        if (hitlim <= 30 && hitlim > 10) {
            setColor(new Color(12, 12, 60));
            fontColor = Color.WHITE;
        }
        if (hitlim <= 10 && hitlim > 3) {
            setColor(new Color(16, 16, 80));
            fontColor = Color.WHITE;
        }
        if (hitlim <= 3 && hitlim > 1) {
            setColor(new Color(20, 20, 100));
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
                setColor(Color.blue);
            }
            // Determine hit direction and make ball bounce
            super.updateBall(ball);

            if (getHitLimit() <= 0) {
                return getPointsEarned(); // Broken, send back points
            }
        }
        return 0; // Not broken, no points
    }
    public void draw(Graphics2D g2) {
        int hitlim = getHitLimit();
        g2.setColor(getColor());
        g2.fillRect(getX(), getY(), getWidth(), getHeight());
        if(getHitLimit() > 0 && getHitLimit() < 1000) {
            String strung = "";
            int num = getHitLimit();
            
            String result = "";
            if (hitlim >= 10){
                result+="<";
                if(hitlim>=30){
                    result+=":";
                    if(hitlim>=50){
                        result+="-";
                    }
                }
            }
        while (num >= 100) {
            result += "C";
            num -= 100;
        }
        while (num >= 90) {
            result += "XC";
            num -= 90;
        }
        while (num >= 50) {
            result += "L";
            num -= 50;
        }
        while (num >= 40) {
            result += "XL";
            num -= 40;
        }
        while (num >= 10) {
            result += "X";
            num -= 10;
        }
        while (num >= 9) {
            result += "IX";
            num -= 9;
        }
        while (num >= 5) {
            result += "V";
            num -= 5;
        }
        while (num >= 4) {
            result += "IV";
            num -= 4;
        }
        while (num >= 1) {
            result += "I";
            num -= 1;
        }
        if (hitlim >= 50){
            result+="-";
        }
        if(hitlim>=30){
            result+=":";      
        }
        if(hitlim>=10){
            result+=">";
        }
        g2.setColor(fontColor);
            int start = (getWidth() - g2.getFontMetrics().stringWidth(result))/2;

            Font theFont = new Font("Consolas", Font.BOLD, (10));
            g2.setFont(theFont);
            g2.drawString(result, getX()+(start), getY()+(getHeight()/2)+3);
        }
    }
    public void setFontColor(Color a){
        fontColor = a;
    }
}

