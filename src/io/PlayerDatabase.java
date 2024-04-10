package src.io;

import src.model.Player;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PlayerDatabase {

    private final String FILE_PATH = "players.sar";

    public boolean registerPlayer(Player player, ObjectOutputStream outputStream) {
        // Load existing players from the file
        List<Player> existingPlayers = loadPlayers();

        // Check if the player already exists in the list
        if (existingPlayers.stream().anyMatch(p -> p.getNickname().equals(player.getNickname()))) {
            try {
                outputStream.writeUTF("Player with nickname " + player.getNickname() + " already exists.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return false;
        }

        // Add the new player to the list
        existingPlayers.add(player);

        // Save the updated player list back to the file
        savePlayers(existingPlayers);

        System.out.println("Player " + player.getNickname() + " registered successfully.");
        return true;
    }

    private List<Player> loadPlayers() {
        List<Player> players = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream(FILE_PATH);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            while (true) {
                try {
                    Player player = (Player) objectIn.readObject();
                    players.add(player);
                } catch (EOFException e) {
                    break; // Reached end of file
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            // If the file doesn't exist or is empty, return an empty list
        }
        return players;
    }

    private void savePlayers(List<Player> players) {
        try (FileOutputStream fileOut = new FileOutputStream(FILE_PATH);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            for (Player player : players) {
                objectOut.writeObject(player);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving players: " + e.getMessage(), e);
        }
    }
}
