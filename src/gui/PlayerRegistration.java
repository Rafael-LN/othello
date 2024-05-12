package gui;

import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

public class PlayerRegistration extends JFrame {
    private JTextField nicknameField, passwordField, nationalityField, ageField, photoUrlField;
    private JButton registerButton;

    public PlayerRegistration(ObjectOutputStream out) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        setTitle("Player Registration");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel nicknameLabel = new JLabel("Nickname: ");
        nicknameField = new JTextField();
        panel.add(nicknameLabel);
        panel.add(nicknameField);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordField = new JPasswordField();
        panel.add(passwordLabel);
        panel.add(passwordField);

        JLabel nationalityLabel = new JLabel("Nationality: ");
        nationalityField = new JTextField();
        panel.add(nationalityLabel);
        panel.add(nationalityField);

        JLabel ageLabel = new JLabel("Age: ");
        ageField = new JTextField();
        panel.add(ageLabel);
        panel.add(ageField);

        JLabel photoUrlLabel = new JLabel("URL of your photo: ");
        photoUrlField = new JTextField();
        panel.add(photoUrlLabel);
        panel.add(photoUrlField);

        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Collect player information from the text fields
                    String nickname = nicknameField.getText();
                    String password = passwordField.getText();
                    String nationality = nationalityField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    String photoUrl = photoUrlField.getText();

                    // Create and return a new Player object
                    Player player = new Player(nickname, password, nationality, age, photoUrl);

                    // Send the player object to the server
                    out.writeObject(player);
                    out.flush();

                    System.out.println("Player registration successful:\n" + player);
                } catch (NumberFormatException | IOException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid age format. Please enter a valid integer.");
                }
            }
        });
        panel.add(registerButton);

        // Create a separate panel for the button and align it to the center
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(registerButton);

        // Add the button panel to the main panel
        panel.add(buttonPanel);

        add(panel);
        setVisible(true);
    }
}
