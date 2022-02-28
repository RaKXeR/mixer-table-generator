package commands;

import model.Player;
import model.Round;
import model.RoundBuilder;
import model.Table;

import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

public class GenerateNewRound {

    public static void run(Scanner s, HashMap<String, Player> players, Collection<Round> rounds) {
        // Create new round
        Round round = RoundBuilder.createRound(players.values(), 4);
        if (round == null) {
            System.out.println("No valid new round was found.");
            return;
        }

        // Present round to user
        System.out.printf("Resulting round (%d tables):\n", round.size());
        System.out.println("------------------------------------------------------------------------------------");
        for (Table table : round) {
            System.out.println(table);
        }
        System.out.println("------------------------------------------------------------------------------------");

        // Ask user if they want to keep the round or not
        boolean processed = false;
        while (!processed) {
            System.out.print("Would you like to keep this round? (y/n): ");
            String resp = s.nextLine().trim().toLowerCase();
            if (resp.isBlank()) resp = "y";
            switch (resp.charAt(0)) {
                case 'y':
                    // Mark table members as played with for each table now that we know this round is to be kept
                    for (Table table : round) {
                        for (Player player : table.getPlayers()) {
                            player.markAsPlayedWith(table.getPlayers());
                        }
                    }
                    rounds.add(round);
                    System.out.println("Round accepted.");
                    processed = true;
                    break;
                case 'n':
                    System.out.println("Round rejected.");
                    processed = true;
                    break;
                default:
                    System.out.println("Please select a valid option.");
            }
        }

    }
}
