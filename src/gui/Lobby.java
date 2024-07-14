package gui;

import services.PlayerService;

import java.awt.*;
import utils.GuiUtils;
import javax.swing.*;
import java.io.ObjectOutputStream;

public class Lobby extends JFrame {

    private PlayerService playerService;
    private JFrame lobby;
    private JButton playButton;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Lobby window = new Lobby(null);
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Lobby(ObjectOutputStream out) {
        playerService = new PlayerService(out);

        setTitle("Lobby");
        setBounds(100, 100, 500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lobbyLabel = new JLabel("Lobby");
        lobbyLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lobbyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(lobbyLabel, BorderLayout.NORTH);

        JList<String> playerList = new JList<String>();
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
        getContentPane().add(playerList, BorderLayout.CENTER);

        playButton = GuiUtils.createButton("Play", new Color(255, 125, 0), e -> {
            try {
                String selected = playerList.getSelectedValue();
                System.out.println("Playing with player " + selected);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        getContentPane().add(playButton, BorderLayout.SOUTH);
    }

}
