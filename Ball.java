import java.awt.*;

public class Ball {

    int x, y, diameter;
    int xSpeed = 2, ySpeed = 2;

    public Ball(int x, int y, int diameter) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
    }

    public void move() {
        x += xSpeed;
        y += ySpeed;

        // bounce top and bottom
        if (y <= 0 || y >= 380) {
            ySpeed *= -1;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, diameter, diameter);
    }
}