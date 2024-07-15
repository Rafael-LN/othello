package utils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

public class GuiUtils {

    public static JButton createButton(String text, Color backgroundColor, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 40));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE); // White text
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false); // Disable the focus painting
        button.addActionListener(actionListener);
        return button;
    }

    public static JLabel createLabel(String text, int alignment, Font font, Border border) {
        JLabel label = new JLabel(text, alignment);
        if (font != null) {
            label.setFont(font);
        }
        if (border != null) {
            label.setBorder(border);
        }
        return label;
    }

    // Overloaded method for cases where only text and alignment are provided
    public static JLabel createLabel(String text, int alignment) {
        return createLabel(text, alignment, null, null);
    }

    public static JTextField createTextField(int columns) {
        return new JTextField(columns);
    }

    public static JPasswordField createPasswordField(int columns) {
        return new JPasswordField(columns);
    }

    public static GridBagConstraints createGridBagConstraints(int gridx, int gridy, int anchor) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.anchor = anchor;
        gbc.insets = new Insets(5, 5, 5, 5); // Spacing between elements
        return gbc;
    }
}
