import java.awt.Color;
import java.awt.Graphics2D;

public class BallSprite extends Sprite {
    
    private double maxVELX = 7;
    private double maxVELY = 7;
    private double minVELX = -6;
    private double minVELY = -6;
    private int damage = 1;
    public BallSprite(int x, int y) {
        super(x, y, 10, 10);
    }
    
    public int getDamage() {
        return damage;
    }
    
    public void setDamage(int a) {
        damage = a;
    }
    
    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.fillOval(getX(), getY(), getWidth(), getHeight());
    }
    
    public void resetSpeed() {
        if(getDx() > maxVELX){
            setDx(maxVELX);
        }
        if(getDx() < minVELX) {
            setDx(minVELX);
        }
        if(getDy() > maxVELY) {
            setDy(maxVELY);
        }
        if(getDy() < minVELY) {
            setDy(minVELY);
        }
    }
}