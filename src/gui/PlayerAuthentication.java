package gui;

import javax.swing.*;
import java.awt.*;
import java.io.ObjectOutputStream;

public class PlayerAuthentication extends JFrame {
    private JButton loginButton, registerButton;

    public PlayerAuthentication(ObjectOutputStream out) {
        setTitle("Player Authentication");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Bem vindo ao Jogo Othello");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // Add space between title and buttons
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 50, 50, 50)); // Add padding
        buttonPanel.setLayout(new GridLayout(1, 2, 20, 10));

        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(60, 20));
        loginButton.addActionListener(e -> {
            // Add login functionality
            JOptionPane.showMessageDialog(null, "Login functionality will be implemented here.");
        });
        buttonPanel.add(loginButton);

        registerButton = new JButton("Register");
        loginButton.setPreferredSize(new Dimension(60, 20));
        registerButton.addActionListener(e -> {
            // Add register functionality
            new PlayerRegistration(out);

        });
        buttonPanel.add(registerButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }
}

