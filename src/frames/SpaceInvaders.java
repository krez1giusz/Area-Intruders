package frames;
import game.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpaceInvaders extends JFrame {
    private static String playerName;
    private static boolean isGamePaused = false;
    private static Board board;

    public SpaceInvaders() {
        playerName = JOptionPane.showInputDialog("Enter your nickname");


        board = new Board();
        add(board);
        setTitle("Area Intruders");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);




        JMenuBar menuBar = new JMenuBar();


        JMenu spaceshipMenu = new JMenu("Spaceship");
        JMenuItem ship1MenuItem = new JMenuItem("Ship 1");
        JMenuItem ship2MenuItem = new JMenuItem("Ship 2");
        JMenuItem ship3MenuItem = new JMenuItem("Ship 3");
        spaceshipMenu.add(ship1MenuItem);
        spaceshipMenu.add(ship2MenuItem);
        spaceshipMenu.add(ship3MenuItem);


        ship1MenuItem.addActionListener(e -> Player.setImage("/resources/player/ship1.png"));
        ship2MenuItem.addActionListener(e -> Player.setImage("/resources/player/ship2.png"));
        ship3MenuItem.addActionListener(e -> Player.setImage("/resources/player/ship3.png"));


        JMenu gameMenu = new JMenu("Game");
        JMenuItem pauseMenuItem = new JMenuItem("Pause");
        gameMenu.add(pauseMenuItem);


        pauseMenuItem.addActionListener(e -> setIsGamePaused(!getIsGamePaused()));


        menuBar.add(spaceshipMenu);
        menuBar.add(gameMenu);


        setJMenuBar(menuBar);

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                board.resizeBoard(getWidth(), getHeight());
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                board.handleKeyPress(e.getKeyCode());
            }
        });
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

        Timer timer = new Timer(1000 / 60, e -> {
            if (!isGamePaused) {
                board.update();
                board.repaint();

            }
        });
        timer.start();

        requestFocusInWindow();

        new ControlPanel(board);
    }

    public static String getPlayerName() {
        return playerName;
    }

    public static void setIsGamePaused(boolean isGamePaused) {
        SpaceInvaders.isGamePaused = isGamePaused;

        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(board);
        if (currentFrame != null) {
            currentFrame.getContentPane().repaint();
        }
    }

    public static boolean getIsGamePaused() {
        return isGamePaused;
    }

    public static void main(String[] args) {
        new MainMenu();
    }

}


class Board extends JPanel {
    private int NUM_ENEMIES;
    private int ENEMY_ROWS = SettingsFrame.getEnemyRowsAmount();
    private int ENEMY_COLS = 0;

    private Difficulty diff = SettingsFrame.getDifficulty();

    private Player player;
    private Enemy[] enemies;

    private int score = 0;

    public Board() {
        initBoard();
    }


    private void initBoard() {
        switch (diff) {
            case Difficulty.EASY:
                ENEMY_COLS = 5;
                break;
            case Difficulty.MEDIUM:
                ENEMY_COLS = 10;
                break;
            case Difficulty.HARD:
                ENEMY_COLS = 15;
                break;
        }
        NUM_ENEMIES = ENEMY_ROWS * ENEMY_COLS;




        player = new Player(200, 250);
        enemies = new Enemy[NUM_ENEMIES];
        for (int i = 0; i < ENEMY_ROWS; i++) {
            for (int j = 0; j < ENEMY_COLS; j++) {
                enemies[i * ENEMY_COLS + j] = new Enemy(50 + j * 30, 50 + i * 30);
            }
        }
    }

    public void resizeBoard(int width, int height) {

        player.setX(width / 2 - 20);
        player.setY(height - 80);



        int enemyWidth = width / ENEMY_COLS;
        int enemyHeight = height / (2 * ENEMY_ROWS);
        for (int i = 0; i < ENEMY_ROWS; i++) {
            for (int j = 0; j < ENEMY_COLS; j++) {
                Enemy enemy = enemies[i * ENEMY_COLS + j];
                if (enemy != null) {
                    enemy.setX(j * enemyWidth);
                    enemy.setY(i * enemyHeight);
                }
            }
        }


        repaint();
    }

    private List<Bullet> bullets = new ArrayList<>();

    public void handleKeyPress(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                player.setX(player.getX() - 10);
                break;
            case KeyEvent.VK_RIGHT:
                player.setX(player.getX() + 10);
                break;
            case KeyEvent.VK_ESCAPE:
                SpaceInvaders.setIsGamePaused(!SpaceInvaders.getIsGamePaused());
                break;
            case KeyEvent.VK_SPACE:
                bullets.add(new Bullet(player.getX(), player.getY()));
                break;
        }
    }

    public void moveEnemies() {
        for (int i = 0; i < ENEMY_ROWS; i++) {
            if (shouldReverseDirection(i)) {
                reverseDirectionAndMoveDown(i);
            }
        }
    }

    private boolean shouldReverseDirection(int row) {
        for (int j = 0; j < ENEMY_COLS; j++) {
            Enemy enemy = enemies[row * ENEMY_COLS + j];
            if (enemy != null) {
                enemy.move();
                if (enemy.getX() <= 0 || enemy.getX() >= getWidth() - 10) {
                    return true;
                }
            }
        }
        return false;
    }

    private void reverseDirectionAndMoveDown(int row) {
        for (int j = 0; j < ENEMY_COLS; j++) {
            Enemy enemy = enemies[row * ENEMY_COLS + j];
            if (enemy != null) {
                enemy.reverseDirection();
                enemy.moveDown();
            }
        }
    }

    public void update() {
        if (SpaceInvaders.getIsGamePaused()) {
            return;
        }

        for (Bullet bullet : new ArrayList<>(bullets)) {
            bullet.move();

            for (Enemy enemy : enemies) {
                if (enemy != null && bullet.getX() >= enemy.getX() && bullet.getX() <= enemy.getX() + 10
                        && bullet.getY() >= enemy.getY() && bullet.getY() <= enemy.getY() + 10) {

                    bullets.remove(bullet);
                    enemies[Arrays.asList(enemies).indexOf(enemy)] = null;
                    score++;
                    break;
                }
            }
        }


        if (Arrays.stream(enemies).allMatch(enemy -> enemy == null)) {
            gameOver(false);
        }

        for (Enemy enemy : enemies) {
            if (enemy != null && enemy.getY() + 10 >= player.getY()) {
                gameOver(true);
            }
        }

        bullets.removeIf(bullet -> bullet.getY() < 0);

        moveEnemies();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);


        int imageWidth = 40;
        int imageHeight = 40;
        g.drawImage(player.getImage(), player.getX() - imageWidth / 2, player.getY() - imageHeight / 2, imageWidth, imageHeight, null);

        for (Enemy enemy : enemies) {
            if (enemy != null) {
                g.setColor(enemy.getColor());
                g.fillRect(enemy.getX(), enemy.getY(), 10, 10);
            }
        }

        for (Bullet bullet : bullets) {
            g.setColor(bullet.getColor());
            g.fillRect(bullet.getX(), bullet.getY(), 5, 10);
        }


        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 10, 20);

        if (SpaceInvaders.getIsGamePaused()) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(0, 0, 0, 128));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 50));
            FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
            int x = (getWidth() - metrics.stringWidth("GAME PAUSED")) / 2;
            int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
            g2d.drawString("GAME PAUSED", x, y);
        }
    }


    private void gameOver(boolean wonGame) {
        String message = wonGame ? "Game Over! You lost!" : "Game Over! You won!";
        message += (score > ScoreManager.getBestScore()) ? "\nCongratulations! You've set a new best record!" : "";

        ScoreManager.saveScore(SpaceInvaders.getPlayerName(), score);

        JOptionPane.showMessageDialog(null, message);

        int playAgain = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Play Again", JOptionPane.YES_NO_OPTION);
        if (playAgain == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            System.exit(0);
        }
    }

    public void resetGame() {

        player.setX(getWidth() / 2);
        player.setY(getHeight() - 50);


        for (int i = 0; i < ENEMY_ROWS; i++) {
            for (int j = 0; j < ENEMY_COLS; j++) {
                enemies[i * ENEMY_COLS + j] = new Enemy(50 + j * 30, 50 + i * 30);
            }
        }


        bullets.clear();


        score = 0;


        SpaceInvaders.setIsGamePaused(false);
    }
}












