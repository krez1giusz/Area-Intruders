package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    private static BufferedImage image;

    public Player(int x, int y) {
        super(x, y, Color.GREEN);
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/player/ship1.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setImage(String imagePath) {
        try {
            image = ImageIO.read(Objects.requireNonNull(Player.class.getResource(imagePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() {
        return image;
    }
}
