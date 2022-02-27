import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import model.*;
import parser.PlayerList;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Main {

    // JSON file with player list on disk
    private static final String PLAYER_LIST = "players.json";

    private static final HashMap<String, Player> playerMap = new HashMap<>();

    public static void main(String[] args) {
        // Get player list from JSON
        Gson gson = new Gson();
        JsonReader reader;
        PlayerList playerList;
        try {
            reader = new JsonReader(new FileReader(PLAYER_LIST));
            playerList = gson.fromJson(reader, PlayerList.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // Generate player objects based on player list
        for (String player : playerList.getPlayers()) {
            playerMap.put(player, new Player(player));
        }

        // Populate player nodes
        for (Player player : playerMap.values()) {
            player.setHasntPlayedWith(playerMap.values());
        }


        // Generate as many rounds as possible for the current player list
        List<Round> rounds = new LinkedList<>();
        Round round;
        while ((round = RoundBuilder.createRound(playerMap.values(), 4)) != null) {
            // Generate a round of tables with players who haven't played with each other
                rounds.add(round);
                //TODO: Ask user if they want to store changes or not

                // Mark table members as played with for each table now that we know this round is to be kept
                for (Table table : round) {
                    for (Player player : table.getPlayers()) {
                        player.markAsPlayedWith(table.getPlayers());
                    }
                }
        };

        // List generated rounds
        int i = 0;
        for (Collection<Table> tableCollection : rounds) {
            System.out.printf("Round %d (%d tables):\n", ++i, tableCollection.size());
            for (Table table : tableCollection) {
                System.out.println(table);
            }
            System.out.println();
        }
    }

}
