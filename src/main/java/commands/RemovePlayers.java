package commands;

import model.Player;

import java.util.HashMap;
import java.util.Scanner;

public class RemovePlayers {
    public static void run(Scanner s, HashMap<String, Player> joinedMap, HashMap<String, Player> leftMap) {
        System.out.println("Please write the names of the players to remove, separated by a comma: ");
        String resp = s.nextLine().trim();
        if (resp.isBlank()) {
            System.out.println("No input received, returning to menu.");
            return;
        }

        String[] newPlayers = resp.split(",");

        System.out.println("------------------------------------------------------------------------------------");
        for (String name : newPlayers) {
            name = name.trim();

            // Skip player if already on joined list
            if (leftMap.containsKey(name)) {
                System.out.printf("Player '%s' skipped (already removed).\n", name);
                continue;
            }

            // Attempt to remove player and store removed object
            Player player = joinedMap.remove(name);
            if (player == null) {
                System.out.printf("Player '%s' skipped (not on the list).\n", name);
                continue;
            }

            // Add player to list of removed players
            leftMap.put(name, player);
            System.out.printf("Player '%s' has been removed.\n", name);
        }
    }
}
