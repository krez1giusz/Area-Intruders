package frames;

import javax.swing.*;
import java.awt.*;

public class RulesFrame extends JFrame {
    public RulesFrame() {
        setTitle("Area Intruders - Rules");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextArea rulesText = new JTextArea();
        rulesText.setEditable(false);
        rulesText.setText("Rules:\n"
                + "1. Use the arrow keys to move your spaceship.\n"
                + "2. Press space to shoot bullets.\n"
                + "3. Destroy all enemies to win the game.\n"
                + "4. If an enemy reaches the bottom or hits your spaceship, you lose.\n");

        add(new JScrollPane(rulesText), BorderLayout.CENTER);

        setVisible(true);
    }
}