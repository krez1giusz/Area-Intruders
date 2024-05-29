package game;

import java.io.*;

public class ScoreManager {
    public static void saveScore(String playerName, int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("scores.txt", true))) {
            writer.write(playerName + "," + score);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static int getBestScore() {
        String bestPlayer = null;
        int bestScore = -1;

        try (BufferedReader reader = new BufferedReader(new FileReader("scores.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String playerName = parts[0];
                int score = Integer.parseInt(parts[1]);

                if (score > bestScore) {
                    bestScore = score;
                    bestPlayer = playerName;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bestScore;
    }

}