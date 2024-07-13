package gui;

import gui.commands.Command;
import gui.commands.OpenLoginCommand;
import gui.commands.OpenRegisterCommand;

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
        loginButton.addActionListener(e -> new OpenLoginCommand(this, out).execute());
        buttonPanel.add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(60, 20));
        registerButton.addActionListener(e -> new OpenRegisterCommand(this, out).execute());
        buttonPanel.add(registerButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }

}

