package gui;

import javax.swing.*;
import java.awt.*;

public class Foto extends JFrame {

    private JLabel photoPreviewLabel;

    public Foto(byte[] photo) {
        setTitle("Foto");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        ImageIcon imageIcon = new ImageIcon(photo);
        // Scale the image to fit the label
        Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);

        photoPreviewLabel = new JLabel();
        photoPreviewLabel.setIcon(new ImageIcon(image));
        photoPreviewLabel.setPreferredSize(new Dimension(200, 100));
        JPanel previewPanel = new JPanel();
        previewPanel.setLayout(new BoxLayout(previewPanel, BoxLayout.Y_AXIS)); // Set the layout to vertical

        previewPanel.add(new JLabel("Photo Preview:"));
        previewPanel.add(photoPreviewLabel);

        // Add padding between the label and the photo preview
        previewPanel.add(Box.createVerticalStrut(5));

        panel.add(previewPanel);
        add(panel);
        setVisible(true);
    }
}
