package commands;

import model.Player;

import java.util.HashMap;
import java.util.Scanner;

public class AddNewPlayers {
    public static void run(Scanner s, HashMap<String, Player> joinedMap, HashMap<String, Player> leftMap) {
        System.out.println("Please write the new player names, separated by a comma: ");
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
            if (joinedMap.containsKey(name)) {
                System.out.printf("Player '%s' skipped (already in the list).\n", name);
                continue;
            }

            // Reuse old player instance if player was added before
            if (leftMap.containsKey(name)) {
                // Move unavailable player back to available list
                joinedMap.put(name, leftMap.remove(name));
            } else {
                // Create new player instance
                Player player = new Player(name);
                // Add player to joined list
                joinedMap.put(name, player);
                // Mark all joined players as not having played with our new player
                player.setHasntPlayedWith(joinedMap.values());
                for (Player joinedPlayer : joinedMap.values()) {
                    joinedPlayer.setHasntPlayedWith(player);
                }
            }
            System.out.printf("Player '%s' has been added.\n", name);
        }
    }
}
