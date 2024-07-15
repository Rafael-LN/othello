package gui;

import enums.PanelType;
import services.PlayerService;

import javax.swing.*;
import java.awt.*;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class MainWindow extends JFrame {
    private PlayerService playerService;
    private ObjectOutputStream out;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private Map<String, PanelConfig> panelConfigMap;

    public MainWindow(ObjectOutputStream out) {
        this.out = out;
        this.playerService = new PlayerService(out);

        initPanelConfig();
        initGui();
    }

    private void initGui() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Othello Game");
        setSize(400, 200); // Initial size for the authentication panel
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(new Color(255, 250, 240)); // Pastel background color

        // Add panels to the cardPanel
        cardPanel.add(new PlayerAuthentication(this), PanelType.AUTHENTICATION.getPanelType());
        cardPanel.add(new PlayerLogin(this), PanelType.LOGIN.getPanelType());
        cardPanel.add(new PlayerRegistration(this), PanelType.REGISTRATION.getPanelType());
        cardPanel.add(new Lobby(this), PanelType.LOBBY.getPanelType());

        setContentPane(cardPanel);
        changePanel(PanelType.AUTHENTICATION);
    }

    private void initPanelConfig() {
        panelConfigMap = new HashMap<>();
        panelConfigMap.put(PanelType.AUTHENTICATION.getPanelType(), new PanelConfig("Othello Game", 400, 200));
        panelConfigMap.put(PanelType.LOGIN.getPanelType(), new PanelConfig("Login", 400, 250));
        panelConfigMap.put(PanelType.REGISTRATION.getPanelType(), new PanelConfig("Player Registration", 600, 300));
        panelConfigMap.put(PanelType.LOBBY.getPanelType(), new PanelConfig("Lobby", 600, 300));
    }

    public void changePanel(PanelType panelType) {
        PanelConfig config = panelConfigMap.get(panelType.getPanelType());
        if (config != null) {
            setTitle(config.title);
            setSize(config.width, config.height);
            setLocationRelativeTo(null);
            cardLayout.show(cardPanel, panelType.getPanelType());
        } else {
            throw new IllegalArgumentException("Unknown panel name: " + panelType);
        }
    }

    public PlayerService getPlayerService() {
        return playerService;
    }

    public void showMessageDialog(String type, String message) {
        JOptionPane.showConfirmDialog(this, message, type, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }

    private static class PanelConfig {
        String title;
        int width, height;

        PanelConfig(String title, int width, int height) {
            this.title = title;
            this.width = width;
            this.height = height;
        }
    }
}
