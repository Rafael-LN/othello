package gui;

import model.Player;
import services.PlayerService;
import utils.GuiUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.ObjectOutputStream;

public class PlayerLogin extends JFrame {
    private JButton loginButton, registerButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private PlayerService playerService;

    public PlayerLogin(ObjectOutputStream out) {
        playerService = new PlayerService(out);
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // game.Main panel with BorderLayout and pastel background color
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(255, 250, 240)); // Pastel background color

        // Center panel for the input fields
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(255, 250, 240)); // Match background color with main panel

        JLabel usernameLabel = GuiUtils.createLabel("Username:", SwingConstants.RIGHT);
        usernameField = GuiUtils.createTextField(15);
        JLabel passwordLabel = GuiUtils.createLabel("Password:", SwingConstants.RIGHT);
        passwordField = GuiUtils.createPasswordField(15);

        centerPanel.add(usernameLabel, GuiUtils.createGridBagConstraints(0, 0, GridBagConstraints.EAST));
        centerPanel.add(usernameField, GuiUtils.createGridBagConstraints(1, 0, GridBagConstraints.WEST));
        centerPanel.add(passwordLabel, GuiUtils.createGridBagConstraints(0, 1, GridBagConstraints.EAST));
        centerPanel.add(passwordField, GuiUtils.createGridBagConstraints(1, 1, GridBagConstraints.WEST));

        // South panel for the buttons
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        southPanel.setBackground(new Color(255, 250, 240)); // Match background color with main panel

        loginButton = GuiUtils.createButton("Login", new Color(173, 216, 230), e -> {
            try {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                Player player = new Player(username, password);
                playerService.loginPlayer(player);
                JOptionPane.showMessageDialog(this, "Login button clicked with username: " + username);
                Lobby lobby = new Lobby(out);
                lobby.setVisible(true);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        loginButton.setEnabled(false);

        registerButton = GuiUtils.createButton("Register", new Color(240, 128, 128), e -> {
            PlayerRegistration registrationWindow = new PlayerRegistration(out);
            registrationWindow.setVisible(true);
        });

        southPanel.add(loginButton);
        southPanel.add(registerButton);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        add(mainPanel);

        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLoginButtonState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLoginButtonState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLoginButtonState();
            }
        };

        usernameField.getDocument().addDocumentListener(documentListener);
        passwordField.getDocument().addDocumentListener(documentListener);

        setVisible(true);
    }

    private void updateLoginButtonState() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        loginButton.setEnabled(!username.isEmpty() && !password.isEmpty());
    }

    // For testing purposes
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PlayerLogin(null));
    }
}
