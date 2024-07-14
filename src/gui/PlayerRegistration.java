package gui;

import model.Player;
import services.PlayerService;
import utils.GuiUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A GUI for player registration.
 */
public class PlayerRegistration extends JFrame {
    private JTextField nicknameField, nationalityField, ageField;
    private JPasswordField passwordField;
    private JButton photoButton, registerButton;
    private JLabel photoPreviewLabel;
    private byte[] photoData;
    private PlayerService playerService;

    /**
     * Constructs a new PlayerRegistration window.
     *
     * @param out the ObjectOutputStream to be used for sending data to the server
     */
    public PlayerRegistration(ObjectOutputStream out) {
        playerService = new PlayerService(out);
        setTitle("Player Registration");
        setSize(600, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(255, 250, 240)); // Pastel background color

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 10, 10));
        inputPanel.setBackground(new Color(255, 250, 240)); // Pastel background color

        inputPanel.add(GuiUtils.createLabel("Nickname: ", SwingConstants.RIGHT));
        nicknameField = GuiUtils.createTextField(20);
        inputPanel.add(nicknameField);

        inputPanel.add(GuiUtils.createLabel("Password: ", SwingConstants.RIGHT));
        passwordField = GuiUtils.createPasswordField(20);
        inputPanel.add(passwordField);

        inputPanel.add(GuiUtils.createLabel("Nationality: ", SwingConstants.RIGHT));
        nationalityField = GuiUtils.createTextField(20);
        inputPanel.add(nationalityField);

        inputPanel.add(GuiUtils.createLabel("Age: ", SwingConstants.RIGHT));
        ageField = GuiUtils.createTextField(20);
        inputPanel.add(ageField);

        inputPanel.add(GuiUtils.createLabel("Photo: ", SwingConstants.RIGHT));
        photoButton = GuiUtils.createButton("Choose Photo", new Color(100, 149, 237), this::choosePhoto);
        inputPanel.add(photoButton);

        photoPreviewLabel = new JLabel();
        photoPreviewLabel.setPreferredSize(new Dimension(100, 100));
        JPanel previewPanel = new JPanel();
        previewPanel.setLayout(new BoxLayout(previewPanel, BoxLayout.Y_AXIS)); // Set the layout to vertical
        previewPanel.setBackground(new Color(255, 250, 240)); // Matching background color

        previewPanel.add(GuiUtils.createLabel("Photo Preview:", SwingConstants.CENTER));
        previewPanel.add(photoPreviewLabel);

        // Add padding between the label and the photo preview
        previewPanel.add(Box.createVerticalStrut(5));

        registerButton = GuiUtils.createButton("Register",  new Color(240, 128, 128), this::registerPlayer);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(255, 250, 240)); // Matching background color
        buttonPanel.add(registerButton);

        mainPanel.add(inputPanel, BorderLayout.EAST);
        mainPanel.add(previewPanel, BorderLayout.WEST);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void choosePhoto(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        // Create a FileNameExtensionFilter for image files
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
                "Image files", "jpg", "jpeg", "png", "gif");

        // Set the file filter for the JFileChooser
        fileChooser.setFileFilter(imageFilter);
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

    private void registerPlayer(ActionEvent e) {
        try {
            String nickname = nicknameField.getText();
            String password = new String(passwordField.getPassword());
            String nationality = nationalityField.getText();
            int age = Integer.parseInt(ageField.getText());

            Player player = new Player(nickname, password, nationality, age, photoData);
            playerService.registerPlayer(player);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid age format. Please enter a valid integer.");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    // Only for testing purposes
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PlayerRegistration(null).setVisible(true));
    }
}
