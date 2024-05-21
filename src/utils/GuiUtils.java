package utils;

import javax.swing.*;
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
}
