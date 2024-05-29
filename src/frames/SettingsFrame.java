package frames;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;



public class SettingsFrame extends JFrame {
    private static int enemyRowsAmount = 5;
    private static int enemyDescentSpeed = 1;
    private static boolean reverseControls = false;
    private static Difficulty difficulty = Difficulty.MEDIUM;


    public SettingsFrame() {
        setTitle("Area Intruders - Settings");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());


        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel difficultyLabel = new JLabel("Difficulty:");
        JComboBox<Difficulty> difficultyComboBox = new JComboBox<>(Difficulty.values());
        difficultyComboBox.setSelectedItem(difficulty);
        difficultyComboBox.addActionListener(e -> difficulty = (Difficulty) difficultyComboBox.getSelectedItem());
        topPanel.add(difficultyLabel);
        topPanel.add(difficultyComboBox);
        add(topPanel, BorderLayout.NORTH);


        JPanel centerPanel = new JPanel(new GridLayout(4, 2));
        JLabel enemyRowsAmountLabel = new JLabel("Amount of enemies rows:");
        JSlider enemyRowsAmountSlider = new JSlider(1, 10, enemyRowsAmount);
        JLabel enemyRowsAmountValue = new JLabel(String.valueOf(enemyRowsAmount));
        enemyRowsAmountSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                enemyRowsAmount = enemyRowsAmountSlider.getValue();
                enemyRowsAmountValue.setText(String.valueOf(enemyRowsAmount));
            }
        });
        centerPanel.add(enemyRowsAmountLabel);
        centerPanel.add(enemyRowsAmountSlider);
        centerPanel.add(new JLabel());
        centerPanel.add(enemyRowsAmountValue);

        JLabel enemyDescentSpeedLabel = new JLabel("Enemy falling speed ( seconds ):");
        JSlider enemyDescentSpeedSlider = new JSlider(1, 10, enemyDescentSpeed);
        JLabel enemyDescentSpeedValue = new JLabel(String.valueOf(enemyDescentSpeed));
        enemyDescentSpeedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                enemyDescentSpeed = enemyDescentSpeedSlider.getValue();
                enemyDescentSpeedValue.setText(String.valueOf(enemyDescentSpeed));
            }
        });
        centerPanel.add(enemyDescentSpeedLabel);
        centerPanel.add(enemyDescentSpeedSlider);
        centerPanel.add(new JLabel());
        centerPanel.add(enemyDescentSpeedValue);
        add(centerPanel, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel(new FlowLayout());
        JLabel reverseControlsLabel = new JLabel("Reverse controls:");
        JCheckBox reverseControlsCheckBox = new JCheckBox();
        reverseControlsCheckBox.setSelected(reverseControls);
        reverseControlsCheckBox.addActionListener(e -> reverseControls = reverseControlsCheckBox.isSelected());
        bottomPanel.add(reverseControlsLabel);
        bottomPanel.add(reverseControlsCheckBox);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
    
    public static int getEnemyRowsAmount() {
        return enemyRowsAmount;
    }
    
    public static  int getEnemyDescentSpeed() {
        return enemyDescentSpeed;
    }
    
    public static boolean getReverseControls() {
        return reverseControls;
    }
    
    public static Difficulty getDifficulty() {
        return difficulty;
    }
}