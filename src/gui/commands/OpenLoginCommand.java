package gui.commands;

import gui.PlayerLogin;

import javax.swing.*;
import java.io.ObjectOutputStream;

/**
 * Command to open the PlayerLogin window.
 */
public class OpenLoginCommand implements Command {
    private JFrame parent;
    private ObjectOutputStream out;

    /**
     * Constructor for OpenLoginCommand.
     *
     * @param parent the parent frame to hide
     * @param out    the ObjectOutputStream for communication
     */
    public OpenLoginCommand(JFrame parent, ObjectOutputStream out) {
        this.parent = parent;
        this.out = out;
    }

    /**
     * Executes the command to open the PlayerLogin window and hide the parent window.
     */
    @Override
    public void execute() {
        parent.setVisible(false); // Hide the parent window
        new PlayerLogin(out).setVisible(true);
    }
}
