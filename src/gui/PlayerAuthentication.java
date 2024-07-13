package gui;

import gui.commands.Command;
import gui.commands.OpenLoginCommand;
import gui.commands.OpenRegisterCommand;
import utils.GuiUtils;

import javax.swing.*;
import java.awt.*;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * A GUI for player authentication, allowing users to choose between login and registration.
 */
public class PlayerAuthentication extends JFrame {
    private JButton loginButton, registerButton;
    private Map<JButton, Command> buttonCommandMap = new HashMap<>();

    /**
     * Constructs a new PlayerAuthentication window.
     *
     * @param out the ObjectOutputStream to be used for sending data to the server
     */
    public PlayerAuthentication(ObjectOutputStream out) {
        setTitle("Othello Game");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(255, 250, 240)); // Pastel background color

        JLabel titleLabel = new JLabel("Welcome to Othello Game");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add space between title and subtitle
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        JLabel subtitleLabel  = new JLabel("Please login or register to continue");
        subtitleLabel .setHorizontalAlignment(SwingConstants.CENTER);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0)); // Add space between subtitle and buttons
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(subtitleLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(255, 250, 240)); // Match the background color

        loginButton = GuiUtils.createButton("Login", new Color(173, 216, 230), e -> new OpenLoginCommand(this, out).execute());
        registerButton = GuiUtils.createButton("Register", new Color(240, 128, 128), e -> new OpenRegisterCommand(this, out).execute());

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }
}
