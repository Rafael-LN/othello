package gui.commands;

import gui.PlayerRegistration;

import javax.swing.*;
import java.io.ObjectOutputStream;

/**
 * Command to open the PlayerRegistration window.
 */
public class OpenRegisterCommand implements Command {
    private JFrame parent;
    private ObjectOutputStream out;

    /**
     * Constructor for OpenRegisterCommand.
     *
     * @param parent the parent frame to hide
     * @param out    the ObjectOutputStream for communication
     */
    public OpenRegisterCommand(JFrame parent, ObjectOutputStream out) {
        this.parent = parent;
        this.out = out;
    }

    /**
     * Executes the command to open the PlayerRegistration window and hide the parent window.
     */
    @Override
    public void execute() {
        parent.setVisible(false); // Hide the parent window
        new PlayerRegistration(out).setVisible(true);
    }
}
