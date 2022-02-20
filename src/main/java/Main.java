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


        List<Round> rounds = new LinkedList<>();
        List<Player> players;
        // Keep track of how many times we generated a round that had leftover players to prevent infinite loops
        int retryCounter = 0;
        do {
            // Copy player list and use it to generate unique tables
            players = new ArrayList<>(playerMap.values());

            // Generate a round of tables with players who haven't played with eachother
            Round round = RoundBuilder.createRound(players, 4);

            // Try again if not all players fit in
            if (players.size() > 0 || !round.hasAllPlayers(playerMap.values())) {
                // Break out of loop if finding a set of tables is taking too many attempts
                if (++retryCounter >= TableBuilder.TIMEOUT) {
                    break;
                }
                players.clear();
                continue;
            }

            if (round.size() > 0) {
                //TODO: Ask user if they want to store changes or not
                rounds.add(round);
            }

            // Mark table members as played with for each table now that we know this round is to be kept
            for (Table table : round) {
                for (Player player : table.getPlayers()) {
                    player.markAsPlayedWith(table.getPlayers());
                }
            }

        } while (players.size() == 0);

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
