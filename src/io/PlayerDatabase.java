package io;

import model.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDatabase {

    private final String FILE_PATH = "players.sar";

    public boolean registerPlayer(Player player, ObjectOutputStream outputStream) {
        List<Player> existingPlayers = loadPlayers();

        if (existingPlayers.stream().anyMatch(p -> p.getNickname().equals(player.getNickname()))) {
            try {
                outputStream.writeObject("Player with nickname " + player.getNickname() + " already exists.");
                System.out.println("Player with nickname " + player.getNickname() + " already exists.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return false;
        }

        existingPlayers.add(player);
        savePlayers(existingPlayers);

        System.out.println("Player " + player.getNickname() + " registered successfully.");
        return true;
    }

    public Player getPlayer(String nickname) {
        List<Player> players = loadPlayers();
        return players.stream().filter(p -> p.getNickname().equals(nickname)).findFirst().orElse(null);
    }

    public List<Player> loadPlayers() {
        List<Player> players = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream(FILE_PATH);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            while (true) {
                try {
                    Player player = (Player) objectIn.readObject();
                    players.add(player);
                } catch (EOFException e) {
                    break;
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

    public boolean validateLogin(Player player) {
        return getPlayer(player.getNickname()) != null;
    }
}
