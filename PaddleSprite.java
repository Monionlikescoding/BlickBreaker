import java.awt.Color;
import java.awt.Graphics2D;
import java.net.URL;


public class PaddleSprite extends Sprite {

    double speedMult = 1;

    
    public PaddleSprite(int x, int y, int width, int height) {
        super(x, y, width, height);
        setColor(Color.white);
        
    }
    public void draw(Graphics2D g2) {
        g2.setColor(getColor());
        g2.fillRect(getX(), getY(), getWidth(), getHeight());
        g2.setColor(Color.black);
        g2.fillRect(getX()+2, getY()+2, getWidth()-4, getHeight()-4);
    }
    public PaddleSprite(int x, int y, int width, int height, double speedMult) {
        super(x, y, width, height);
        this.speedMult = speedMult;
        setColor(Color.LIGHT_GRAY);
    }

    @Override
    /*
     * @see Sprite#intersects(Sprite) This method overrides the default behavior of
     * intersects. It divides the paddle into 5 zones and changes the bounce angle
     * of the ball based on where it hits the paddle
     */
    public boolean intersects(Sprite ball) {
        if (super.intersects(ball)) {    
            ball.invertDy();
            ball.setY(getY()-6-(ball.getHeight()/2));
            // Define midpoint of ball
            double[] ball_mid = {ball.getX() + ball.getWidth() / 2,ball.getY() + ball.getHeight() / 2};

            // Define paddle zones boundaries
            // ___|___|___|___|___
            //    z0  z1  z2  z3
            double[] z0 = {this.getX() + this.getWidth() / 9, this.getY() + this.getHeight()};
            double[] z1 = {this.getX() + 2 * this.getWidth() / 7,this.getY() + this.getHeight()};
            double[] z2 = {this.getX() + 3 * this.getWidth() / 7, this.getY() + this.getHeight()};
            double[] z3 = {this.getX() + 4 * this.getWidth() / 7, this.getY() + this.getHeight()};
            double[] z4 = {this.getX() + 5 * this.getWidth() / 7, this.getY() + this.getHeight()};
            double[] z5 = {this.getX() + 8 * this.getWidth() / 9,this.getY() + this.getHeight()};
            double[] z6 = {this.getX() + 7 * this.getWidth() / 7, this.getY() + this.getHeight()};
            // Determine which zone is hit by the middle of the ball and
            // set ball velocity
            if (ball_mid[0] <= z0[0] && ball_mid[1] <= z0[1]) {
                // !Edge bounces get a boost!
                if (Math.abs(ball.getDx()) <= 1) {
                    ball.setVelocity(-5*speedMult,-5*speedMult);
                }
                if (ball.getDx() < 0){
                    ball.setVelocity(1.2*speedMult*ball.getDx(), -5*speedMult);
                }
                else {
                    ball.setVelocity(-1.2*speedMult*ball.getDx(), -5*speedMult);
                }
            } else if (ball_mid[0] <= z1[0] && ball_mid[1] <= z1[1]) {
                if (Math.abs(ball.getDx()) <= 1) {
                    ball.setVelocity(-3*speedMult,-4*speedMult);
                }
                if (ball.getDx() < 0){
                    ball.setVelocity(1.06*speedMult*ball.getDx(), -4*speedMult);
                }
                else {
                    ball.setVelocity(-1.06*speedMult*ball.getDx(), -4*speedMult);
                }
            } else if (ball_mid[0] <= z2[0] && ball_mid[1] <= z2[1]) {
                if (Math.abs(ball.getDx()) <= 1) {
                    ball.setVelocity(-1*speedMult,-4*speedMult);
                }
                if (ball.getDx() < 0){
                    ball.setVelocity(.75*speedMult*ball.getDx(), -4*speedMult);
                }
                else {
                    ball.setVelocity(-.75*speedMult*ball.getDx(), -4*speedMult);
                }
            } else if (ball_mid[0] <= z3[0] && ball_mid[1] <= z3[1]) {
                ball.setVelocity(Math.random()*5*speedMult*ball.getDx(), -4*speedMult);
            } else if (ball_mid[0] <= z4[0] && ball_mid[1] <= z4[1]){
                // not middle
                if (Math.abs(ball.getDx()) <= 1) {
                    ball.setVelocity(1*speedMult,-4*speedMult);
                }
                if (ball.getDx() < 0){
                    ball.setVelocity(-.75*speedMult*ball.getDx(), -4*speedMult);
                }
                else {
                    ball.setVelocity(.75*speedMult*ball.getDx(), -4*speedMult);
                }
            }
            else if (ball_mid[0] <= z5[0] && ball_mid[1] <= z5[1]){
                // Far Right
                if (Math.abs(ball.getDx()) <= 1) {
                    ball.setVelocity(3*speedMult,-4*speedMult);
                }
                if (ball.getDx() < 0){
                    ball.setVelocity(-1.06*speedMult*ball.getDx(), -4*speedMult);
                }
                else {
                    ball.setVelocity(1.06*speedMult*ball.getDx(), -4*speedMult);
                }
            }
            else if (ball_mid[0] <= z6[0] && ball_mid[1] <= z6[1]){
                // Far Right
                if (Math.abs(ball.getDx()) <= 1) {
                    ball.setVelocity(5*speedMult,-5*speedMult);
                }
                if (ball.getDx() < 0){
                    ball.setVelocity(-1.2*speedMult*ball.getDx(), -5*speedMult);
                }
                else {
                    ball.setVelocity(1.2*speedMult*ball.getDx(), -5*speedMult);
                }
            }
            
        
            
            //Play audio
            //Brick.clip.stop();
            //Brick.clip.setFramePosition(0);
            //Brick.clip.start();

            return true; // It does intersect
        }
        return false; // It does not intersect
    }
    
    public boolean doesitactuallyintersect(Sprite something) {
        return super.intersects(something);
    }
    
    public void update(Graphics2D g2, int width, int height) {
        // Move            
        if (!(getDx() > 0 && getX() + getWidth() + getDx() < width || getDx() < 0 && getX() + getDx() > 0)) {
            setDx(0);
        }
        if (!((getDy() > 0 && (getY() + getHeight() + getDy() < height-40)) || ((getDy() < 0) && (getDy() < 0 && getY() + getDy() > 300)))) {
            setDy(0);
        }
            super.update(g2);
            
            draw(g2);
        
    }
    
    public double getSpeedMult() {
        return speedMult;
    }
    
    public void setSpeedMult(double a) {
         speedMult = a;
    }
}
