package commands;

import model.Player;

import java.util.HashMap;
import java.util.Scanner;

public class ListCurrentPlayers {
    public static void run(Scanner s, HashMap<String, Player> players) {
        if (players.isEmpty()) {
            System.out.println("There are no available players.");
            return;
        }
        System.out.println("List of available players:");
        System.out.println("------------------------------------------------------------------------------------");
        for (Player player : players.values()) {
            System.out.println(player);
        }
    }
}
