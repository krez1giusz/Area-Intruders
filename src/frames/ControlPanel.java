package frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class ControlPanel extends JFrame {
    public ControlPanel(Board board) {
        JButton leftButton = new JButton("Left");
        JButton spaceButton = new JButton("Space");
        JButton rightButton = new JButton("Right");

        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.handleKeyPress(KeyEvent.VK_LEFT);
            }
        });

        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.handleKeyPress(KeyEvent.VK_RIGHT);
            }
        });

        spaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.handleKeyPress(KeyEvent.VK_SPACE);
            }
        });

        add(leftButton);
        add(spaceButton);
        add(rightButton);

        setLayout(new FlowLayout());
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}