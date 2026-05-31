import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    Paddle paddle1;
    Paddle paddle2;
    Ball ball;

    int score1 = 0;
    int score2 = 0;

    Timer timer;

    int timeLeft = 20;
    long lastTimeCheck = System.currentTimeMillis();
    boolean gameOver = false;

    public GamePanel() {

        paddle1 = new Paddle(20, 150, 10, 60);
        paddle2 = new Paddle(560, 150, 10, 60);
        ball = new Ball(300, 200, 20);

        this.setFocusable(true);
        this.addKeyListener(this);

        timer = new Timer(10, this);
        timer.start();
    }

    public void resetBall() {
        ball.x = 300;
        ball.y = 200;
        ball.xSpeed = 2; // slower speed
        ball.ySpeed = 2;
    }
    public void resetGame() {
    score1 = 0;
    score2 = 0;

    timeLeft = 20;
    gameOver = false;

    lastTimeCheck = System.currentTimeMillis();

    resetBall();
}

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 600, 400);

        // scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Player 1: " + score1, 50, 30);
        g.drawString("Player 2: " + score2, 400, 30);
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.drawString("Press R to Restart", 200, 260);

        // timer
        g.drawString("Time: " + timeLeft, 260, 30);

        // draw objects
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);

        // GAME OVER + WINNER
        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("GAME OVER", 150, 150);

            g.setFont(new Font("Arial", Font.BOLD, 25));

            if (score1 > score2) {
                g.drawString("Player 1 Wins!", 180, 220);
            } else if (score2 > score1) {
                g.drawString("Player 2 Wins!", 180, 220);
            } else {
                g.drawString("It's a Draw!", 200, 220);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {

        // ⏱ TIMER LOGIC (FIXED)
        if (!gameOver) {
            long currentTime = System.currentTimeMillis();

            if (currentTime - lastTimeCheck >= 1000) {
                timeLeft--;
                lastTimeCheck = currentTime;
            }

            if (timeLeft <= 0) {
                timeLeft = 0;   // 🔥 prevent negative
                gameOver = true;
            }
        }

        // 🟢 ONLY RUN GAME IF NOT OVER
        if (!gameOver) {
            ball.move();
            if (ball.x <= -10) {
                score2++;
                resetBall();
                return; // 🔥 stop further checks
            }
            if (ball.x >= 590) {
                score1++;
                resetBall();
                return; // 🔥 stop further checks
            }

    // 🏓 THEN COLLISION

    // LEFT paddle
            if (ball.x <= paddle1.x + paddle1.width && ball.y + ball.diameter >= paddle1.y && ball.y <= paddle1.y + paddle1.height) {
                ball.xSpeed = Math.abs(ball.xSpeed);
            }

    // RIGHT paddle
            if (ball.x + ball.diameter >= paddle2.x && ball.y + ball.diameter >= paddle2.y && ball.y <= paddle2.y + paddle2.height) {
                ball.xSpeed = -Math.abs(ball.xSpeed);
            }
        }

        repaint();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            resetGame();
        }

        if (!gameOver) { // 🔥 stop movement after game over

            if (e.getKeyCode() == KeyEvent.VK_W) {
                paddle1.moveUp();
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                paddle1.moveDown();
            }

            if (e.getKeyCode() == KeyEvent.VK_UP) {
                paddle2.moveUp();
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                paddle2.moveDown();
            }

            repaint();
        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
}