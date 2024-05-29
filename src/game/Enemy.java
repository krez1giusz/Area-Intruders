package game;

import java.awt.*;

public class Enemy extends Entity {
    private int dx = 2;
    private int dy = 0;

    public Enemy(int x, int y) {
        super(x, y, Color.RED);
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public void reverseDirection() {
        dx = -dx;
    }

    public void moveDown() {
        y += 10;
    }
}