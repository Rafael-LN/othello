package gui.commands;

import java.io.ObjectOutputStream;

/**
 * Command interface for executing actions.
 */
public interface Command {
    /**
     * Executes the command action.
     */
    void execute();
}
