package game;

import java.awt.*;

public class Bullet extends Entity {
    public Bullet(int x, int y) {
        super(x, y, Color.YELLOW);
    }

    public void move() {
        y -= 10;
    }
}
