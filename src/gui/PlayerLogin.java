package gui;

import enums.PanelType;
import model.Player;
import services.PlayerService;
import utils.GuiUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class PlayerLogin extends JPanel {
    private JButton loginButton, registerButton;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public PlayerLogin(MainWindow gui) {
        setLayout(new GridBagLayout());
        setBackground(new Color(255, 250, 240)); // Pastel background color

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Margins between components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;

        JPanel spacerTop = new JPanel();
        spacerTop.setOpaque(false);
        add(spacerTop, gbc); // Spacer panel to push inputs to the center

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel usernameLabel = GuiUtils.createLabel("Username:", SwingConstants.RIGHT);
        add(usernameLabel, gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        usernameField = GuiUtils.createTextField(15);
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel passwordLabel = GuiUtils.createLabel("Password:", SwingConstants.RIGHT);
        add(passwordLabel, gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        passwordField = GuiUtils.createPasswordField(15);
        add(passwordField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTH;

        JPanel spacerBottom = new JPanel();
        spacerBottom.setOpaque(false);
        add(spacerBottom, gbc); // Spacer panel to push inputs to the center

        gbc.gridy++;
        gbc.weighty = 0;
        gbc.insets = new Insets(20, 5, 5, 5); // Increased top margin for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5)); // Adjusted gap between buttons
        buttonPanel.setBackground(new Color(255, 250, 240)); // Match the background color

        loginButton = GuiUtils.createButton("Login", new Color(173, 216, 230), e -> {
            if (usernameField.getText().isEmpty() || new String(passwordField.getPassword()).isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in both username and password", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());

                    Player player = new Player(username, password);
                    gui.getPlayerService().loginPlayer(player);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        registerButton = GuiUtils.createButton("Register", new Color(240, 128, 128), e -> gui.changePanel(PanelType.REGISTRATION));

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        add(buttonPanel, gbc);

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
    }

    private void updateLoginButtonState() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        loginButton.setEnabled(!username.isEmpty() && !password.isEmpty());
    }

}
