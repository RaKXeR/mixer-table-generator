package commands;

import model.Player;

import java.util.HashMap;
import java.util.Scanner;

public class ListPlayerConnections {
    public static void run(Scanner s, HashMap<String, Player> joinedMap, HashMap<String, Player> leftMap) {
        System.out.println("Please write the player's name: ");
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

        // Return if player has no connections
        if (pivot.getHasntPlayedWith().size() == 0) {
            System.out.printf("'%s' has played with all previously connected players.\n", name);
            return;
        }

        // Display players that haven't been played with
        System.out.printf("Players that haven't played with '%s':\n", name);
        System.out.println("------------------------------------------------------------------------------------");
        for (Player player : pivot.getHasntPlayedWith()) {
            System.out.println(player);
        }
    }
}
