package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.Game;
import game.Main;

public class Start {

    public static void main(String[] args) {
        // Create the JFrame
        JFrame frame = new JFrame("Start game.game.Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        // Create the JPanel
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        // Set the frame visible
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // Create the Start button
        JButton startButton = new JButton("Start");
        startButton.setBounds(100, 80, 100, 25);
        panel.add(startButton);

        // Add action listener to the Start button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Instantiate the game.game.Game class
                Game game = new Game(); // create an instance of the game

                // Create the graphical interface of the board
                SwingUtilities.invokeLater(() -> Main.createAndShowGUI(game));
            }
        });
    }
}
