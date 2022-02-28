package commands;

import model.Player;

import java.util.HashMap;
import java.util.Scanner;

public class ListCurrentPlayers {
    public static void run(Scanner s, HashMap<String, Player> players) {
        System.out.println("List of available players:");
        System.out.println("------------------------------------------------------------------------------------");
        for (Player player : players.values()) {
            System.out.println(player);
        }
    }
}
