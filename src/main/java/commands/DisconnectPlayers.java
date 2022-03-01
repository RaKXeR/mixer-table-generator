package commands;

import model.Player;

import java.util.HashMap;
import java.util.Scanner;

public class DisconnectPlayers {
    public static void run(Scanner s, HashMap<String, Player> joinedMap, HashMap<String, Player> leftMap) {
        System.out.println("Please write the name of one of your target players: ");
        String name = s.nextLine().trim();
        if (name.isBlank()) {
            System.out.println("No input received, returning to menu.");
            return;
        }

        // Get player entity from either joined or left list
        Player pivot = joinedMap.containsKey(name) ? joinedMap.get(name) : leftMap.get(name);
        if (pivot == null) {
            // Return since player isn't on our list
            System.out.printf("Player '%s' not found, returning to menu.\n", name);
            return;
        }

        // Get players to disconnect from pivot player
        System.out.println("Please write the names of the players you want to mark as played with: ");
        String resp = s.nextLine().trim();
        if (resp.isBlank()) {
            System.out.println("No input received, returning to menu.");
            return;
        }

        String[] newPlayers = resp.split(",");
        System.out.println("------------------------------------------------------------------------------------");
        for (String newName : newPlayers) {
            newName = newName.trim();
            // Get player instance from lists
            Player player = joinedMap.containsKey(newName) ? joinedMap.get(newName) : leftMap.get(newName);
            if (player == null) {
                System.out.printf("Player '%s' not found, no changes were made.\n", newName);
                continue;
            }

            // Mark pivot and current player as played with
            pivot.markAsPlayedWith(player);
            player.markAsPlayedWith(pivot);
            System.out.printf("Player '%s' has been marked as played with '%s'.\n", newName, name);
        }
    }
}
