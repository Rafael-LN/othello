package gui;

import services.PlayerService;
import utils.GuiUtils;

import javax.swing.*;
import java.awt.*;

public class Lobby extends JPanel {

    private JButton playButton;

    public Lobby(MainWindow gui) {
        setLayout(new GridBagLayout());
        setBackground(new Color(255, 250, 240)); // Pastel background color

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margins between components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 2;

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome player");
        welcomeLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel, gbc);

        // Player List
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JList<String> playerList = new JList<>();
        playerList.setModel(new AbstractListModel<String>() {
            private static final long serialVersionUID = 1L;
            String[] values = new String[] {"player1", "player2"};
            public int getSize() {
                return values.length;
            }
            public String getElementAt(int index) {
                return values[index];
            }
        });
        JScrollPane playerListScrollPane = new JScrollPane(playerList);
        add(playerListScrollPane, gbc);

        // Play Button
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;

        playButton = GuiUtils.createButton("Play", new Color(255, 125, 0), e -> {
            try {
                String selected = playerList.getSelectedValue();
                System.out.println("Playing with player " + selected);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        add(playButton, gbc);
    }
}
