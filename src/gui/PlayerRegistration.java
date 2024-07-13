package gui;

import model.Player;
import services.PlayerService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class PlayerRegistration extends JFrame {
    private JTextField nicknameField, passwordField, nationalityField, ageField;
    private JButton photoButton, registerButton;
    private JLabel photoPreviewLabel;
    private byte[] photoData;
    private PlayerService playerService;

    public PlayerRegistration(ObjectOutputStream out) {
        playerService = new PlayerService(out);
        setTitle("Player Registration");
        setSize(600, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel nicknameLabel = new JLabel("Nickname: ");
        nicknameField = new JTextField();
        inputPanel.add(nicknameLabel);
        inputPanel.add(nicknameField);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordField = new JPasswordField();
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);

        JLabel nationalityLabel = new JLabel("Nationality: ");
        nationalityField = new JTextField();
        inputPanel.add(nationalityLabel);
        inputPanel.add(nationalityField);

        JLabel ageLabel = new JLabel("Age: ");
        ageField = new JTextField();
        inputPanel.add(ageLabel);
        inputPanel.add(ageField);

        JLabel photoUrlLabel = new JLabel("Photo: ");
        photoButton = new JButton("Choose Photo");
        photoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Open file chooser dialog to select a photo
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        // Get the selected file path
                        Path photoPath = fileChooser.getSelectedFile().toPath();
                        photoData = Files.readAllBytes(photoPath);

                        // Load and display the selected image
                        ImageIcon imageIcon = new ImageIcon(photoData);
                        // Scale the image to fit the label
                        Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        photoPreviewLabel.setIcon(new ImageIcon(image));
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Failed to load image.");
                    }
                }
            }
        });
        inputPanel.add(photoUrlLabel);
        inputPanel.add(photoButton);

        photoPreviewLabel = new JLabel();
        photoPreviewLabel.setPreferredSize(new Dimension(200, 100));
        JPanel previewPanel = new JPanel();
        previewPanel.setLayout(new BoxLayout(previewPanel, BoxLayout.Y_AXIS)); // Set the layout to vertical

        previewPanel.add(new JLabel("Photo Preview:"));
        previewPanel.add(photoPreviewLabel);

        // Add padding between the label and the photo preview
        previewPanel.add(Box.createVerticalStrut(5));

        registerButton = new JButton("Register");
        registerButton.addActionListener(e -> {
            try {
                String nickname = nicknameField.getText();
                String password = new String(((JPasswordField) passwordField).getPassword());
                String nationality = nationalityField.getText();
                int age = Integer.parseInt(ageField.getText());

                Player player = new Player(nickname, password, nationality, age, photoData);
                playerService.registerPlayer(player);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid age format. Please enter a valid integer.");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(registerButton);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(previewPanel, BorderLayout.EAST);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    // Only for testing purposes
    /* public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PlayerRegistration(null));
    }*/
}
