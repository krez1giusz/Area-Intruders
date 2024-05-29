package frames;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScoreboardFrame extends JFrame {
    public ScoreboardFrame() {
        setTitle("Area Intruders - Scoreboard");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(10, 1));

        List<String> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("scores.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scores.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        scores.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int score1 = Integer.parseInt(o1.split(",")[1]);
                int score2 = Integer.parseInt(o2.split(",")[1]);
                return Integer.compare(score2, score1);
            }
        });

        for (int i = 0; i < 10 && i < scores.size(); i++) {
            add(new JLabel(scores.get(i)));
        }

        setVisible(true);
    }
}