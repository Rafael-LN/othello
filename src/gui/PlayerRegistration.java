package gui;

import model.Player;
import utils.GuiUtils;
import utils.NumericDocumentFilter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PlayerRegistration extends JPanel {
    private JTextField nicknameField, nationalityField, ageField;
    private JPasswordField passwordField;
    private JButton photoButton, registerButton;
    private JLabel photoPreviewLabel;
    private byte[] photoData;

    public PlayerRegistration(MainWindow gui) {
        setLayout(new GridBagLayout());
        setBackground(new Color(255, 250, 240)); // Pastel background color

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Margins between components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;

        // Photo Preview Panel
        JPanel photoPanel = new JPanel();
        photoPanel.setLayout(new BoxLayout(photoPanel, BoxLayout.Y_AXIS)); // Set the layout to vertical
        photoPanel.setBackground(new Color(255, 250, 240)); // Matching background color
        photoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around the photo panel

        photoPreviewLabel = new JLabel();
        photoPreviewLabel.setPreferredSize(new Dimension(100, 100));
        photoPanel.add(GuiUtils.createLabel("Photo Preview:", SwingConstants.CENTER));
        photoPanel.add(photoPreviewLabel);

        gbc.gridheight = 6; // Span photo panel across multiple rows
        gbc.anchor = GridBagConstraints.NORTH;
        add(photoPanel, gbc);

        // Input Fields
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 1;
        add(GuiUtils.createLabel("Nickname: ", SwingConstants.RIGHT), gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        nicknameField = GuiUtils.createTextField(20);
        add(nicknameField, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        add(GuiUtils.createLabel("Password: ", SwingConstants.RIGHT), gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        passwordField = GuiUtils.createPasswordField(20);
        add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        add(GuiUtils.createLabel("Nationality: ", SwingConstants.RIGHT), gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        nationalityField = GuiUtils.createTextField(20);
        add(nationalityField, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        add(GuiUtils.createLabel("Age: ", SwingConstants.RIGHT), gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        ageField = GuiUtils.createTextField(20);
        ((AbstractDocument) ageField.getDocument()).setDocumentFilter(new NumericDocumentFilter());
        add(ageField, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        add(GuiUtils.createLabel("Photo: ", SwingConstants.RIGHT), gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        photoButton = GuiUtils.createButton("Choose Photo", new Color(100, 149, 237), this::choosePhoto);
        photoButton.setPreferredSize(new Dimension(150, 25)); // Adjust width to prevent ellipsis
        add(photoButton, gbc);

        // Register Button
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(20, 5, 5, 5); // Additional top margin for the register button
        gbc.anchor = GridBagConstraints.CENTER; // Center the register button horizontally
        registerButton = GuiUtils.createButton("Register", new Color(240, 128, 128), e -> registerPlayer(e, gui));
        add(registerButton, gbc);
    }

    private void choosePhoto(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
                "Image files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(imageFilter);
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                Path photoPath = fileChooser.getSelectedFile().toPath();
                photoData = Files.readAllBytes(photoPath);
                ImageIcon imageIcon = new ImageIcon(photoData);
                Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                photoPreviewLabel.setIcon(new ImageIcon(image));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Failed to load image.");
            }
        }
    }

    private void registerPlayer(ActionEvent e, MainWindow gui) {
        try {
            String nickname = nicknameField.getText();
            String password = new String(passwordField.getPassword());
            String nationality = nationalityField.getText();
            int age = Integer.parseInt(ageField.getText());

            Player player = new Player(nickname, password, nationality, age, photoData);
            gui.getPlayerService().registerPlayer(player);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid age format. Please enter a valid integer.");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
