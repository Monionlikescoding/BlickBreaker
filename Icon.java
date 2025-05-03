/*
 * This class is meant to create the icons
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Icon {

    //Location
    private int x;
    private int y;
    private int type;
    private int scalar;
    
    
    //Default constructor
    public Icon(int type, int X, int Y, int sca) {
        x = X;
        this.type = type;
        y = Y;
        scalar = sca;
    }
    
    //Renders the Sprite on screen
    public void draw(Graphics2D g2) {
        if (type == 0) {
            g2.setColor(new Color(0,0,0));
            // R1
            g2.fillRect(x+(5*scalar), y, 10*scalar,5*scalar);
            g2.fillRect(x+(20*scalar), y, 10*scalar,5*scalar);
            // R2
            g2.fillRect(x, y+(5*scalar), 5*scalar,10*scalar);
            g2.setColor(new Color(255,255,255));
            g2.fillRect(x+(5*scalar), y+(5*scalar), 5*scalar,5*scalar);
            g2.setColor(new Color(255,0,0));
            g2.fillRect(x+(10*scalar), y+(5*scalar), 5*scalar,15*scalar);
            g2.setColor(new Color(0,0,0));
            g2.fillRect(x+(15*scalar), y+(5*scalar), 10*scalar,10*scalar);
            g2.setColor(new Color(255,0,0));
            g2.fillRect(x+(20*scalar), y+(5*scalar), 5*scalar,10*scalar);
            g2.setColor(new Color(123, 0, 0));
            g2.fillRect(x+(25*scalar), y+(5*scalar), 5*scalar,10*scalar);
            g2.setColor(new Color(0,0,0));
            g2.fillRect(x+(30*scalar), y+(5*scalar), 5*scalar,10*scalar);
            // R3
            g2.setColor(new Color(255,0,0));
            g2.fillRect(x+(5*scalar), y+(10*scalar), 5*scalar,5*scalar);
            g2.setColor(new Color(255,0,0));
            g2.fillRect(x+(15*scalar), y+(10*scalar), 5*scalar,10*scalar);
            // R4
            g2.setColor(new Color(0,0,0));
            g2.fillRect(x+(5*scalar), y+(15*scalar), 5*scalar,5*scalar);
            g2.setColor(new Color(123, 0, 0));
            g2.fillRect(x+(20*scalar), y+(15*scalar), 5*scalar,5*scalar);
            g2.setColor(new Color(0,0,0));
            g2.fillRect(x+(25*scalar), y+(15*scalar), 5*scalar,5*scalar);
            // R5
            g2.setColor(new Color(0,0,0));
            g2.fillRect(x+(10*scalar), y+(20*scalar), 5*scalar,5*scalar);
            g2.setColor(new Color(123, 0, 0));
            g2.fillRect(x+(15*scalar), y+(20*scalar), 5*scalar,5*scalar);
            g2.setColor(new Color(0,0,0));
            g2.fillRect(x+(20*scalar), y+(20*scalar), 5*scalar,5*scalar);
            g2.setColor(new Color(0,0,0));
            // R6
            g2.fillRect(x+(15*scalar), y+(25*scalar), 5*scalar,5*scalar);
        }
        else if (type == 1) {
            g2.setColor(new Color(0,0,0));
            g2.fillRect(x+(5*scalar), y, 10*scalar,5*scalar);
        }
    }
    
    //Returns a rectangle representing the Sprite
    
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
}
