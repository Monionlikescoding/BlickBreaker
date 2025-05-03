import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BrickBreaker implements ActionListener, KeyListener {
    // Variables used to render the graphics
    private final int WIDTH = 500, HEIGHT = 600;
    private int time = 0;
    // why is this HERE?!?!
    private final double PADDLE_SPEED = 6.25;
    private JFrame frame;
    private DrawingPanel panel;
    private Timer gameTimer;
    private Image offScreenImage = null;
    private Graphics2D offScreenGraphics = null;
    private int BALL_START_X = WIDTH / 2;
    private int BALL_START_Y = 230;
    private Icon Heart = new Icon(0,0,2,1);
    // When this variable reaches 0, the player is a winner
    // Change this to equal the number of brick you want the
    // player to break
    int bricksLeftToWin = 10;
    int levels = 2;
    // Player's score
    int score = 0;

    // Player's lives
    int lives = 5;
    
    // Paddle cooldown (bugfixin)
    int paddleCooldown = 8;
    
    // Sprint vars
    
    int sprintDuration = 125;
    double sprintMax = 250.0;
    boolean isSprinting = false;
    double sprintMult = 1.5;
    
    double regMult = 1;
    // All the bricks go in this ArrayList
    ArrayList<Brick> bricks = new ArrayList<Brick>();
    // Ball Sprite
    Sprite ball = new BallSprite(BALL_START_X, BALL_START_Y);
    // Paddle Sprite
    PaddleSprite paddle = new PaddleSprite(WIDTH / 2 - WIDTH / 10, HEIGHT - HEIGHT / 10, WIDTH / 5, HEIGHT / 40);
    
    public BrickBreaker() {
        frame = new JFrame("BrickBreaker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new DrawingPanel();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.addKeyListener(this);
        frame.setLocationRelativeTo(null);

        // Create the game Sprites
        initializeSprites();

        // Set up and start the game timer
        gameTimer = new Timer(10, this);// (speed, listener)
        gameTimer.start();
    }

    // POST: All the needed game Sprites are initialized
    public void initializeSprites() {
        // Initialize paddle and ball
        ball = new BallSprite(BALL_START_X, BALL_START_Y);
        ball.setDx(0);
        ball.setDy(3);

        paddle = new PaddleSprite(WIDTH / 2 - WIDTH / 10, HEIGHT - HEIGHT / 10, WIDTH / 6, HEIGHT / 40);
        // The initial row of bricks
        /*
         * Grid system
         * 8x9 blocks total of 72 (ignore the other one)
         * each block cube is 50x20
         * Therefore the area in which the bricks exist in are
         * 25 < x < 425
         * 30 < y < 210
         */
        
        createLevel(levels);
    }
    // Customized JPanel for drawing
    private class DrawingPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;

            final Dimension d = getSize();
            if (offScreenImage == null) {
                offScreenImage = createImage(d.width, d.height);
                offScreenGraphics = (Graphics2D) offScreenImage.getGraphics();
            }

            offScreenGraphics.setColor(new Color(5, 5, 5));
            offScreenGraphics.fillRect(0, 0, d.width, d.height);
            renderOffScreen(offScreenGraphics);
            g2.drawImage(offScreenImage, 0, 0, null);

        }
    }

    public void renderOffScreen(Graphics2D g) {
        // Convert to a Graphics2D object bc it gives us way more tools
        
        // Turn on anti-aliasing (smoothing)
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Check winner and reset levels
        if (bricksLeftToWin == 0) {
            if (levels == 2) {
                gameTimer.stop();
                g.setColor(new Color(220, 220, 255));
                Font theFont = new Font("Bree Serif", Font.BOLD, 36);
                g.setFont(theFont);
                int start = (WIDTH - g.getFontMetrics().stringWidth("WINNER")) / 2;
                g.drawString("WINNER", start, HEIGHT / 2);
            }
            else if(levels == 1) {
                bricksLeftToWin = 15;
                paddle.setWidth((int)(paddle.getWidth()*1.15));
                lives += 2;
                score += 10;
                levels++;
                createLevel(levels);
            }
        }
        
        for (int i=0; i < bricks.size(); i++) {
            bricks.get(i).setCooldown(bricks.get(i).getCooldown() - 1);
        }
        // Update and redraw the bricks
        ArrayList<Brick> toRemove = new ArrayList<Brick>();
        for (Brick b : bricks) {
            b.update(g);
            b.renderParticles(g);
            b.update((BallSprite) ball);
            int result = b.isHit(ball);
            if (result > 0) {
                score += result;
                bricksLeftToWin--;
                toRemove.add(b);
            }
            if (b.intersects(ball))
                ball.update(g);
        }
        bricks.removeAll(toRemove);
        
        // Update and redraw the ball
        ball.update(g);
        ((BallSprite) ball).resetSpeed();
        // Check ball against the walls
        if (ball.getX() + ball.getWidth() + ball.getDx() > WIDTH || ball.getX() + ball.getDx() < 0) {
            ball.invertDx();
            if(ball.getX() > WIDTH / 2) {
                if(ball.getDx() < 1) {
                    ball.setDx(ball.getDx()+1);
                }
            }
            if(ball.getX() > WIDTH / 2) {
                if(ball.getDx() > -1) {
                    ball.setDx(ball.getDx()-1);
                }
            }
        }
        if (ball.getY() + ball.getDy() < 0) {
            ball.invertDy();
        }

        // Update the paddle
        paddle.update(g, WIDTH, HEIGHT); // Move
        if (paddleCooldown <= 0 && paddle.doesitactuallyintersect(ball)) {
            paddle.intersects(ball);
            paddleCooldown = 8; // 80 ms cooldown to touch ball with paddle, preventing lag from spam touching ball. Does not actually affect gameplay
        }// Check collision with ball
        
        paddleCooldown--;
        
        
        if (sprintDuration < sprintMax && !(isSprinting)) {
            sprintDuration++;
        }
        
        if (isSprinting) {
            sprintDuration-=4;
        }
        
        if (sprintDuration <= 0) {
            sprintDuration = 1;
            isSprinting = false;
        }
        
        if (isSprinting == true) {
            paddle.setSpeedMult(sprintMult);
        }
        else {
            paddle.setSpeedMult(regMult);
        }
        
        // Life Lost
        if (ball.getY() + ball.getHeight() + ball.getDy() > HEIGHT) {
            lives--;
            score /= 1.25; // Score penalty for missed ball
            if (lives > 0) {
                ball = new BallSprite(BALL_START_X, BALL_START_Y);
                ball.setDx(0);
                ball.setDy(3);
            }
            else {
                // Lose
                gameTimer.stop();
                g.setColor(new Color(255, 107, 147));
                Font theFont = new Font("Consolas", Font.BOLD, 36);
                g.setFont(theFont);
                int start = (WIDTH - g.getFontMetrics().stringWidth("GAME OVER")) / 2;
                g.drawString("GAME OVER", start, HEIGHT / 2);
            }
        }
        
        

        // Draw lives
        //AT THE END after checking hp, so on death it is accurate
        Heart.draw(g);
        g.setColor(new Color(170, 170, 170));
        Font theFont = new Font("Consolas", Font.BOLD, 25);
        g.setFont(theFont);
        int start = g.getFontMetrics().stringWidth("    : " + lives) - 10;
        g.drawString("    : " + lives, 5, 25);
        
        g.setColor(new Color(170, 170, 170));
        theFont = new Font("Consolas", Font.BOLD, 25);
        g.setFont(theFont);
        start = (g.getFontMetrics().stringWidth("Level: " + levels)) / 2;
        g.drawString("Level: " + levels, 250 - start, 25);
        
        g.setColor(new Color(170, 170, 170));
         theFont = new Font("Consolas", Font.BOLD, 25);
        g.setFont(theFont);
         start = WIDTH - g.getFontMetrics().stringWidth("Score: " + score) - 10;
        g.drawString("Score: " + score, start, 25);

        // Draw remaining bricks
        g.setColor(new Color(130, 130, 130));
        theFont = new Font("Consolas", Font.BOLD, 20);
        g.setFont(theFont);
        g.drawString("Bricks Remaining: " + bricksLeftToWin, 5, HEIGHT - 5);
        
        
        //DEBUG 
        g.setColor(new Color(130, 130, 130));
        theFont = new Font("Consolas", Font.BOLD, 13);
        g.setFont(theFont);
        g.drawString("Game Time: " + time, 5, HEIGHT - 25);
        time++;
        
        g.setColor(new Color(130, 130, 130));
        theFont = new Font("Consolas", Font.BOLD, 13);
        g.setFont(theFont);
        g.drawString("Ball Vel DX: " + (Math.round(ball.getDx()*1000)/1000.0) + " DY: " + (Math.round(ball.getDy()*1000)/1000.0), 140, HEIGHT - 25);
        time++;
        
        Sprite sprintBarBack = new Sprite(WIDTH-130, HEIGHT-35,120, 30, new Color(40, 35, 35));
        sprintBarBack.draw(g);
        Sprite sprintBar = new Sprite(WIDTH-128, HEIGHT-33,(int)(116*((double)(sprintDuration / sprintMax))), 26, new Color(100, 35, 35));
        sprintBar.draw(g);
        
        g.setColor(new Color(130, 130, 130));
        theFont = new Font("Consolas", Font.BOLD, 15);
        g.setFont(theFont);
        g.drawString("Z: Sprint", WIDTH-100, HEIGHT-15);
        time++;

        
        // Draw score
        
    }

    // Implement ActionPerformed
    public void actionPerformed(ActionEvent e) {
        panel.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            paddle.setDx(PADDLE_SPEED * paddle.getSpeedMult());
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            paddle.setDx(-PADDLE_SPEED * paddle.getSpeedMult());
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            paddle.setDy((-PADDLE_SPEED/1.5) * paddle.getSpeedMult());
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            paddle.setDy((PADDLE_SPEED/1.5) * paddle.getSpeedMult());
        }
        if (e.getKeyCode() == KeyEvent.VK_Z) {
            if (sprintDuration > 30) {
                isSprinting = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {            
            paddle.setDx(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP|| e.getKeyCode() == KeyEvent.VK_DOWN) {            
            paddle.setDy(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_Z) {
            isSprinting = false;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent arg0) {

        // TO DO Auto-generated method stub
    }
    
    public ArrayList<Brick> getBricks() {
        return bricks;
    }
    
    public void createLevel(int lvl){
        
        int x = 25, y = 30;
        bricksLeftToWin = 10;
        if (lvl == 1) {
            bricksLeftToWin = 36;
        
            for (int i = 0; i<9;i++) {
                Brick a = new Brick(x+(50*i), y+20, 50, 20);
                bricks.add(a);
            }
            for (int i = 0; i<9;i++) {
                Brick a = new Brick(x+(50*i), y+40, 50, 20);
                bricks.add(a);
            }
            for (int i = 0; i<9;i++) {
                Brick a = new Brick(x+(50*i), y+60, 50, 20);
                bricks.add(a);
            }
            for (int i = 0; i<9;i++) {
                Brick a = new Brick(x+(50*i), y+80, 50, 20);
                bricks.add(a);
            }
        }
        else if (lvl == 2) {
            bricksLeftToWin = 1;    
            
        }
        else if (lvl == 3) {
            bricksLeftToWin = 13;    
            for (int i = 0; i<9;i++) {
                Brick a = new Brick(x+(50*i), y, 50, 20);
                bricks.add(a);
            }
            for (int i = 0; i<4;i++) {
                Brick a = new TeleportingBrick(x+50+(100*i), y+40, 50, 20, 2, 2);
                bricks.add(a);
            }
        }
    }
}
