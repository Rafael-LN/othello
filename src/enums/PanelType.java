package enums;

public enum PanelType {
    AUTHENTICATION("AUTHENTICATION"),
    LOGIN("LOGIN"),
    REGISTRATION("REGISTRATION"),
    LOBBY("LOBBY"),
    GAME("GAME");

    private final String panelType;

    PanelType(String panel) {
        this.panelType = panel;
    }

    public String getPanelType() {
        return panelType;
    }
}
